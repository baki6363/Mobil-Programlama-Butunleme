package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserRegisterResponse {

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("token")
    @Expose
    public String token;
}
