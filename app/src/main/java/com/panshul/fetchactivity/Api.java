package com.panshul.fetchactivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("/hiring.json")
    Call<List<ListIdObject>> getAllListIds();
}
