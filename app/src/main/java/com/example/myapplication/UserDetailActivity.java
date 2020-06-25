package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.utils.ApiUrl;
import com.example.myapplication.utils.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserDetailActivity extends AppCompatActivity {

    private int userId;
    private User userData;

    private EditText edtName;
    private EditText edtLastName;
    private ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        edtName = findViewById(R.id.user_detail_name);
        edtLastName = findViewById(R.id.user_detail_lastName);
        profilePic = findViewById(R.id.user_detail_image);

        Bundle bundle = getIntent().getExtras();
        userId = -1;
        if (bundle != null) {
            userId = bundle.getInt("userid");
            initializeUser();
        }

    }

    private void initializeUser() {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        UserService users = retrofit.create(UserService.class);
        Call<User> call = users.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                UserData user = response.body().data;
                edtName.setText(user.firstName);
                edtLastName.setText(user.lastName);
                Picasso.get().load(user.avatar).into(profilePic);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
