package com.http;

import okhttp3.*;

import java.io.IOException;

public class Main {


    public static void main(String[] args){

        String url = "http://m.ximalaya.com/passport-sign-h5/v1/sign/combined/getVerifyCode";
        OkHttpClient okHttpClient = new OkHttpClient();

//        HashMap<String, String> params = new HashMap<>();
//        params.put( "code", "16c92d85ce252a5df231cdf95f9cc7f8" );
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("code", "16c92d85ce252a5df231cdf95f9cc7f8");
//        for ( Map.Entry<String, String> entry : params.entrySet() ) {
//            builder.add( entry.getKey(), entry.getValue() );
//        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
