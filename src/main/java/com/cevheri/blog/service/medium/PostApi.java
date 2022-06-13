package com.cevheri.blog.service.medium;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;


import java.io.IOException;

public class PostApi {

    private String listPublicationsResourceUrl = "/users/{{userId}}/publications";

    public static PostResponse createPost(PostResponse input) throws IOException {


        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(input.getData());
        System.out.println("createPost content:" + content);

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, content);
        PostResponse result = new PostResponse();
        String createPostResourceUrl = result.getApiUrl().replace("{{authorId}}", UserApi.AUTHOR_ID);
        //String createPostResourceUrl = result.getApiUrl().replace("{{authorId}}", "12974e025f39ae31b1e4c4a419f4f8210f1b399d20e39f24d5eeb3f1715d041e4");

        Request request = new Request.Builder()
            .url(EndPoint.BASE_URL + createPostResourceUrl)
            .method("POST", body)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", Client.ACCESS_KEY)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        String responseBody = response.body().string();
        result = objectMapper.readValue(responseBody, PostResponse.class);
        response.body().close();

        System.out.println("createPost result:" + result);
//        if (result.getData() != null) {
//            PostModel model = (PostModel) result.getData();
//            System.out.println("createPost model:" + model);
//        }
        return result;
    }

}
