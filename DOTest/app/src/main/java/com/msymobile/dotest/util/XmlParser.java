package com.msymobile.dotest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;

/**
 * autour: hannibal
 * date: 2018/4/7
 * e-mail:404769122@qq.com
 * description:
 */
public class XmlParser {
    private static final String URL_MATCHER = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
    private static final String HEAD_MATCHER = "<head.*?>([\\s\\S]*?)</head>";
    private static final String APPLE_MATCHER = "<link ([\\S]*)rel=\"apple-touch-icon[\\S]*?\"[\\s\\S]*?>";
    private static final String HREF_MATCHER = "href=\"([^\"]*)\"";

    public static String xmlParser(ResponseBody xml) {
        long l = System.currentTimeMillis();
        try {
            Pattern headP = Pattern.compile(HEAD_MATCHER);
            Pattern subP = Pattern.compile(APPLE_MATCHER);
            Pattern urlP = Pattern.compile(HREF_MATCHER);
            //match head
            Matcher headMather = headP.matcher(xml.string());
            if (!headMather.find()) return null;
            //match <link
            Matcher subMatcher = subP.matcher(headMather.group());
            if (!subMatcher.find()) return null;
            //match href
            Matcher urlMatcher = urlP.matcher(subMatcher.group());
            if (!urlMatcher.find()) return null;
            //get url
            String group = urlMatcher.group();
            group = group.replaceAll("href=", "");
            group = group.replaceAll("\"", "");
            //add protocol
            if (!group.contains("http:") && !group.contains("https")) {
                group = "http:" + group;
            }
            LogUtil.e("hannibal", " time : " + (System.currentTimeMillis() - l));
            return group;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUrl(String url) {
        Pattern compile = Pattern.compile(URL_MATCHER);
        Matcher matcher = compile.matcher(url);
        return matcher.find();
    }
}
