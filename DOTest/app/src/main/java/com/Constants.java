package com;

import java.nio.charset.Charset;

import okhttp3.MediaType;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public interface Constants {

    //project
    String PACKAGENAME = "com.julihuxin.shanlvyy";

    //net
    MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    Charset UTF_8 = Charset.forName("UTF-8");

    //file
    String CACHE = "NetCache";
    String RESOURCE = "Resource";
    String CACHERESOURCE = "CacheResource";
}
