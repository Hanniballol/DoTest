package com.net.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class GlobalGsonConverterFactory extends Converter.Factory {
    private Gson mGson;

    private GlobalGsonConverterFactory(Gson gson) {
        if (null == gson) throw new NullPointerException("gson can't be null");
        this.mGson = gson;
    }

    public static GlobalGsonConverterFactory create() {
        return create(new Gson());
    }

    public static GlobalGsonConverterFactory create(Gson gson) {
        return new GlobalGsonConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new GlobalGsonResponseBodyConverter<>(mGson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new GlobalGsonRequestBodyConverter<>(mGson,adapter);
    }
}
