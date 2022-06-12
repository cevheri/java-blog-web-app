package com.cevheri.blog.service.medium;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.okhttp3.*;

import java.io.IOException;
import java.util.Arrays;

public class UserApi {
    public static String AUTHOR_ID= "12974e025f39ae31b1e4c4a419f4f8210f1b399d20e39f24d5eeb3f1715d041e4";
    private final String resourceUrl = "/me";
    private final String url = EndPoint.BASE_URL + resourceUrl;

    public UserApi() {
        if(AUTHOR_ID==null){
            try{
                aboutMe();
            }catch (IOException ie){
                System.out.println(Arrays.toString(ie.getStackTrace()));
            }
        }
    }

    private void aboutMe() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
            .url(url)
            .method("GET", body)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", Client.ACCESS_KEY)
            .build();
        Response response = client.newCall(request).execute();

        ObjectMapper objectMapper = new ObjectMapper();
        assert response.body() != null;
        UserModel userModel = objectMapper.readValue(response.body().string(), UserModel.class);
        AUTHOR_ID=userModel.getId();
    }

}
