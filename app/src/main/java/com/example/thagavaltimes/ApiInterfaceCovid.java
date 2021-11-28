package com.example.thagavaltimes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceCovid {
    String BASE_URL = "https://corona.lmao.ninja/v2/";

    @GET("countries")
    Call<List<ModelClassCovid>> getcountrydata();
}
