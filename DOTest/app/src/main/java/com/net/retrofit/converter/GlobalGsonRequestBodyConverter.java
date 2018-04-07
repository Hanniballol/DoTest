package com.net.retrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;

import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

import static com.Constants.MEDIA_TYPE;
import static com.Constants.UTF_8;

/**
 * autour: hannibal
 * date: 2017/12/25
 * e-mail:404769122@qq.com
 * description:
 */
public class GlobalGsonRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private Gson mGson;
    private TypeAdapter<T> mTypeAdapter;

    public GlobalGsonRequestBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        mGson = gson;
        mTypeAdapter = typeAdapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JsonWriter jsonWriter = mGson.newJsonWriter(outputStreamWriter);
        mTypeAdapter.write(jsonWriter, value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
