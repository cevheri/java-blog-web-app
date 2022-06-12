package com.cevheri.blog.service.medium;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.okhttp3.*;

import java.io.IOException;
import java.util.Arrays;

public class PostApi {

    private final String createPostResourceUrl = "/users/{{authorId}}/posts";
    private final String listPublicationsResourceUrl = "/users/{{userId}}/publications";


    public PostModel createPost(PostModel input) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(input);
        System.out.println("createPost content:" + content);

        OkHttpClient client = new OkHttpClient().newBuilder()
            .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,
            "{" + content + "}"
        );

        String resourceUrl = createPostResourceUrl.replace("{{authorId}}", UserApi.AUTHOR_ID);

        Request request = new Request.Builder()
            .url(EndPoint.BASE_URL + resourceUrl)
            .method("POST", body)
            .addHeader("accept", "application/json")
            .addHeader("Authorization", Client.ACCESS_KEY)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        assert response.body() != null;
        PostModel result = objectMapper.readValue(response.body().string(), PostModel.class);
        System.out.println("createPost result:" + result);
        return result;
    }

}
