package com.example.myapplication;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TestService {
        @GET("{test_file}")
        Call<OneTest> fetchOneTest(@Path("test_file") String test_file);
        @GET("index.json")
        Call<AllThemesInfo> fetchAllTests();

}
