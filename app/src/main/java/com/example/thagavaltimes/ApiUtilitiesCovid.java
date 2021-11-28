package com.example.thagavaltimes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtilitiesCovid {
    public static Retrofit retrofit = null;

    public static ApiInterfaceCovid getAPIInterface() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(ApiInterfaceCovid.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit.create(ApiInterfaceCovid.class);
    }
}
