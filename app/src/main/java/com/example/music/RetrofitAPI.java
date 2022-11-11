package com.example.music;

import android.telecom.Call;

public interface RetrofitAPI {

        @POST("Music")
        Call<Mask> createPost(@Body Mask dataModal);

        @PUT("Music/")
        Call<Mask> createPut(@Body Mask dataModal, @Query("ID") int id);

        @DELETE("Music/")
        Call<Mask> createDelete(@Query("id") int id);
    }

