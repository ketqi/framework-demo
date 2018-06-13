package com.ketqi.core.datebase;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 数据源切片
 * User: ketqi
 * Date: 2018-06-13 19:06
 */
@Aspect
@Component
public class DateSourceAspect {
    private static ConcurrentMap<String, Boolean> methodIsReadCache = new ConcurrentHashMap<>();

    @Pointcut("execution(public * com.*.*.service.*.*(..))")
    public void service() {
    }

    @Around("service()")
    public Object arround(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Object target = pjp.getTarget();
        String cacheKey = String.format("%s.%s", target.getClass().getName(), method.getName());
        Boolean isReadCacheValue = methodIsReadCache.get(cacheKey);
        if (isReadCacheValue == null) {
            // 重新获取方法，否则传递的是接口的方法信息
            Method realMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());
            isReadCacheValue = isChoiceReadDB(realMethod);
            methodIsReadCache.put(cacheKey, isReadCacheValue);
        }
        if (isReadCacheValue) {
            DynamicDataSourceHolder.markRead();
        } else {
            DynamicDataSourceHolder.markWrite();
        }
        try {
            return pjp.proceed();
        } finally {
            DynamicDataSourceHolder.reset();
        }
    }

    /**
     * 判断是否只读方法
     *
     * @param method 执行方法
     * @return 当前方法是否只读
     */
    private boolean isChoiceReadDB(Method method) {
        Transactional transactionalAnno = AnnotationUtils.findAnnotation(method, Transactional.class);
        if (transactionalAnno == null) {
            return true;
        }

        // 如果之前选择了写库，则现在还选择写库
        if (DynamicDataSourceHolder.isChoiceWrite()) {
            return false;
        }

        return transactionalAnno.readOnly();
    }
}
