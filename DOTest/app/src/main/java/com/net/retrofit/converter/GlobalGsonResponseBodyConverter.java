package com.net.retrofit.converter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.net.retrofit.bean.HttpStatus;
import com.net.retrofit.exception.TokenInvalidException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.Constants.UTF_8;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class GlobalGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private Gson mGson;
    private TypeAdapter<T> mTypeAdapter;

    public GlobalGsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        mGson = gson;
        mTypeAdapter = typeAdapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String response = value.string();
            HttpStatus httpStatus = mGson.fromJson(response, HttpStatus.class);
            Log.e("hannibal",httpStatus.getMessage() + " : "+httpStatus.getCode());
            if (httpStatus.isCodeInvalid()) {
                throw new TokenInvalidException();
            }
            MediaType contentType = value.contentType();
            Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
            InputStream inputStream = new ByteArrayInputStream(response.getBytes());
            Reader reader = new InputStreamReader(inputStream, charset);
            JsonReader jsonReader = mGson.newJsonReader(reader);
            return mTypeAdapter.read(jsonReader);
        }finally {
            value.close();
        }
    }
}
