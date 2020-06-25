package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("token")
    @Expose
    public String token;
}
