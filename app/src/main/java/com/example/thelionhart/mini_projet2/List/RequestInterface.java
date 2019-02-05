package com.example.thelionhart.mini_projet2.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("android/jsonandroid")
    Call<JSONResponse> getJSON();
}
