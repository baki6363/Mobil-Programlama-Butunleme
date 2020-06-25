package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserListResponse {

    @SerializedName("page")
    @Expose
    public Integer page;

    @SerializedName("per_page")
    @Expose
    public Integer perPage;

    @SerializedName("total")
    @Expose
    public Integer total;

    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

    @SerializedName("data")
    @Expose
    public List<UserData> data = null;

    public List<UserData> getData() {
        return data;
    }

}
