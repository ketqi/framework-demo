package com.ketqi.common.utils;

import com.ketqi.common.PlatformConstant;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.IDN;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 辅助工具
 * User: ketqi
 * Date: 2017-06-08 11:18
 */
public abstract class PlatformUtils {
    /** 分隔符 */
    public static final String DELIMITERS = ",; \t\n";
    private static final List<String> AGENT_LIST = parse2StrList("android,iphone,ipod,ipad,windows phone,mqqbrowser");
    //微信过滤表情
    private static final Pattern EMOJI_PATTERN = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

    /**
     * 将字符串按照分隔符分开
     *
     * @param source    带分割的字符串
     * @param separator 分隔符
     */
    public static List<String> parse2StrList(String source, String separator) {
        return parse2StrList(source, separator, false);
    }

    /**
     * 将字符串按照分隔符分开
     *
     * @param source    带分割的字符串
     * @param separator 分隔符
     */
    public static List<String> parse2StrList(String source, String separator, boolean space) {
        if (!StringUtils.hasText(source)) {
            return new ArrayList<>();
        }
        List<String> itemList = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(source, separator);
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (StringUtils.hasText(token)) {
                itemList.add(token.trim());
            } else if (space) {
                itemList.add("");
            }
        }
        return itemList;
    }

    /**
     * @param source 字符串
     * @return 将以[,; \t\n]分隔的字符串转换为List
     */
    public static List<String> parse2StrList(String source) {
        return parse2StrList(source, DELIMITERS);
    }

    /**
     * @param source 数字字符串
     * @return 将以[,; \t\n]分隔的数字字符串转换为List
     */
    public static List<Long> parse2LongList(String source) {
        List<String> list = parse2StrList(source);
        List<Long> itemList = new ArrayList<>();
        for (String str : list) {
            itemList.add(Long.parseLong(str));
        }
        return itemList;
    }

    /**
     * @param source 数字字符串
     * @return 将以[,; \t\n]分隔的数字字符串转换为List
     */
    public static List<Integer> parse2IntegerList(String source) {
        List<String> list = parse2StrList(source);
        List<Integer> itemList = new ArrayList<>();
        for (String str : list) {
            itemList.add(Integer.parseInt(str));
        }
        return itemList;
    }

    /**
     * @param list 对象列表
     * @return 将对象列表转换为以逗号分隔的字符串
     */
    public static <T> String parse2String(Collection<T> list) {
        return parse2String(list, ",");
    }

    /**
     * @param list      对象列表
     * @param separator 分隔符
     * @return 将对象列表转换为以指定分隔的字符串
     */
    public static <T> String parse2String(Collection<T> list, String separator) {
        if (isEmpty(list)) {
            return "";
        }

        StringBuilder builder = new StringBuilder(PlatformConstant.LENGTH_64);
        for (T t : list) {
            builder.append(t).append(separator);
        }

        int length = builder.length();
        if (length > 0) {
            builder.delete(length - separator.length(), length);
        }
        return builder.toString();
    }

    /**
     * 字符串转换数组
     *
     * @param str 字符串，格式（换行）：
     *            baidu.com
     *            qq.com
     *            51web.com
     * @return 数组
     */
    public static String[] parse2Array(String str) {
        if (!StringUtils.hasText(str)) {
            return new String[0];
        }

        String strs = str.replace("\r\n", ";");
        strs = strs.replace("\n", ";");
        return strs.split(";");
    }

    /**
     * @param sourceList 待分离的集合
     * @param targetList 参考集合
     * @param <T>        比较的对象
     * @return 分离sourceList中不包含在targetList中的数据
     */
    public static <T extends Comparable<T>> List<T> exclude(List<T> sourceList, List<T> targetList) {
        List<T> list = new ArrayList<>();
        for (T t : sourceList) {
            if (!targetList.contains(t)) {
                list.add(t);
            }
        }
        return list;
    }

    /**
     * 空值转化为""字符
     *
     * @param str 源字符串
     * @return 字符串
     */
    public static String nullToEmpty(String str) {
        if (str == null || "null".equalsIgnoreCase(str)) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 保证原字符串长度在250范围内
     *
     * @param str 源字符串
     * @return str
     */
    public static String assureStringLengthLt250(String str) {
        String temp = str;
        if (StringUtils.hasText(temp)) {
            temp = temp.trim();
            if (temp.length() > PlatformConstant.LENGTH_250) {
                return temp.substring(0, PlatformConstant.LENGTH_250);
            }
        }
        return temp;
    }

    /**
     * @param str1 源字符串1
     * @param str2 源字符串2
     * @return boolean
     * 比较两个字符串是否相等
     */
    public static boolean equals(String str1, String str2) {
        if (StringUtils.hasText(str1)) {
            return str1.equals(str2);
        } else if (StringUtils.hasText(str2)) {
            return str2.equals(str1);
        }
        return true;
    }

    /**
     * 集合是否为空
     *
     * @param coll 集合对象
     * @return 是否为空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    /**
     * Map是否为空
     *
     * @param map map对象
     * @return 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 集合是否不为空
     *
     * @param coll 集合对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return coll != null && !coll.isEmpty();
    }

    /**
     * Map是否不为空
     *
     * @param map map对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    /**
     * 比较字符串的大小
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 比较结果
     */
    public static int compareTo(String str1, String str2) {
        boolean b1 = StringUtils.hasText(str1);
        boolean b2 = StringUtils.hasText(str2);

        if (b1 && !b2) {
            return 1;
        }

        if (!b1 && b2) {
            return -1;
        }

        if (b1) {
            return str1.compareTo(str2);
        }
        return 0;
    }

    /**
     * 将"bool:true;message:xxxxxx"的字符串转化为map;
     *
     * @param source 字符串
     */
    public static Map<String, String> parse2Map(String source) {
        if (!StringUtils.hasText(source)) {
            return Collections.emptyMap();
        }

        List<String> strs = parse2StrList(source);
        if (strs.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<>();
        for (String item : strs) {
            if (!StringUtils.hasText(item)) {
                continue;
            }
            int indexof = item.indexOf(':');
            if (indexof == -1) {
                continue;
            }
            map.put(item.substring(0, indexof), item.substring(indexof + 1));
        }

        return map;
    }

    /**
     * @param t 堆栈异常
     * @return 堆栈信息字符串化
     * 将堆栈信息字符串化
     */
    public static String getStackTrace(Throwable t) {
        if (t == null) {
            return "";
        }

        try (StringWriter stringWriter = new StringWriter(); PrintWriter pw = new PrintWriter(stringWriter, true)) {
            t.printStackTrace(pw);
            return stringWriter.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c 待判断的字符
     * @return true or false
     */
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    /**
     * 完整的判断中文汉字和符号
     *
     * @param strName 待判断的字符串
     * @return true or false
     */
    public static boolean isChinese(String strName) {
        if (!StringUtils.hasText(strName)) {
            return true;
        }

        char[] ch = strName.toCharArray();
        for (char c : ch) {
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 在进行多线程运行时,切分任务
     *
     * @param list 源列表
     * @param num  切分个数
     * @param <T>  资源类型
     * @return 子资源列表
     */
    public static <T> List<List<T>> subList(List<T> list, int num) {
        List<List<T>> resultList = new ArrayList<>();
        if (num <= 1) {
            resultList.add(list);
            return resultList;
        }

        int size = num;
        int total = list.size();
        int page = total / size;
        if (page * (size + 0.5) < total) {
            size = num + 1;
        }

        for (int i = 1; i <= size; i++) {
            if (i == size) {
                resultList.add(list.subList((i - 1) * page, total));
            } else {
                resultList.add(list.subList((i - 1) * page, i * page));
            }
        }
        return resultList;
    }

    /**
     * 在进行多线程运行时,切分任务
     *
     * @param list     源列表
     * @param pageSize 每页条数
     * @param <T>      资源类型
     * @return 子资源列表
     */
    public static <T> List<List<T>> subListByPageSize(List<T> list, int pageSize) {
        List<List<T>> resultList = new ArrayList<>();
        int total = list.size();
        if (pageSize <= 0 || total < pageSize) {
            resultList.add(list);
            return resultList;
        }

        int page = total / pageSize;
        if (page * pageSize < total) {
            page++;
        }

        for (int i = 1; i <= page; i++) {
            if (i == page) {
                resultList.add(list.subList((i - 1) * pageSize, total));
            } else {
                resultList.add(list.subList((i - 1) * pageSize, i * pageSize));
            }
        }
        return resultList;
    }

    /**
     * 根据域名获取ip地址
     *
     * @param domainName 域名名称
     */
    public static String getIp(String domainName) {
        try {
            return InetAddress.getByName(IDN.toASCII(domainName)).getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }

    /**
     * 将str转换为long
     *
     * @param longStr str
     * @return long
     */
    public static long parse2Long(String longStr) {
        long num = 0L;
        try {
            num = Long.parseLong(longStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 将str转换为int
     *
     * @param intStr str
     * @return long
     */
    public static int parse2int(String intStr) {
        int num = 0;
        try {
            num = Integer.parseInt(intStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 判断请求是否来自手机
     *
     * @param userAgent 请求头中的User-Agent
     */
    public static boolean isMobileAgent(String userAgent) {
        if (!StringUtils.hasText(userAgent)) {
            return false;
        }
        userAgent = userAgent.toLowerCase();
        if (!userAgent.contains("windows nt") || (userAgent.contains("windows nt") && userAgent.contains("compatible; msie 9.0;"))) {
            // 排除 苹果桌面系统
            if (!userAgent.contains("windows nt") && !userAgent.contains("macintosh")) {
                for (String item : AGENT_LIST) {
                    if (userAgent.contains(item)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** 微信过滤表情 */
    public static String filterEmoji(String source) {
        if (source == null) {
            return "";
        }

        Matcher emojiMatcher = EMOJI_PATTERN.matcher(source);
        if (emojiMatcher.find()) {
            source = emojiMatcher.replaceAll("");
            return source;
        }
        return source;
    }

    public static int getCurrentProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return parse2int(runtimeMXBean.getName().split("@")[0]);
    }

    /** 判断当前操作系统是否是linux */
    public static boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().startsWith("linux");
    }
}
