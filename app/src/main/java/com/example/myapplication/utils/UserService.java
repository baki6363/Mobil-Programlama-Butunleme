package com.example.myapplication.utils;

import com.example.myapplication.UserCreateResponse;
import com.example.myapplication.UserListResponse;
import com.example.myapplication.UserLoginResponse;
import com.example.myapplication.UserRegisterResponse;
import com.example.myapplication.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @GET("api/users?page=2")
    Call<UserListResponse> getUsers();

    @GET("api/users")
    Call<UserListResponse> getUsers(@Query("page") int page);

    @GET("api/users")
    Call<User> getUser(@Query("id") int id);

    @FormUrlEncoded
    @POST("api/users")
    Call<UserCreateResponse> addUser(@Field("name") String name, @Field("job") String job);

    @FormUrlEncoded
    @POST("api/login")
    Call<UserLoginResponse> loginUser(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<UserRegisterResponse> registerUser(@Field("email") String email, @Field("password") String password);

}

