package com.example.myapplication;

import com.example.myapplication.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("data")
    @Expose
    public UserData data;

    @SerializedName("ad")
    @Expose
    public UserData ad;
}
