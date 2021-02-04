package com.isee.community.provider;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.isee.community.dto.AccesstokenDTO;
import com.isee.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccesstokenDTO accesstokenDTO){
        MediaType mediaType= MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            //System.out.println(responseBody); access_token=1584779e4d33f234c0b752e450aeeb0f3346e0c9&scope=user&token_type=bearer
            String token = responseBody.split("&")[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 根据accessToken获取github的用户信息
     * @param accessToken
     * @return
     */
    public GithubUser getUser(String accessToken)  {
        System.out.println(accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            System.out.println(githubUser);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
