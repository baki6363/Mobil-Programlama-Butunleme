package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.RecyclerView.RecyclerAdapter;
import com.example.myapplication.utils.ApiUrl;
import com.example.myapplication.utils.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersListActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ArrayList<String> imageNames = new ArrayList<>();
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<Integer> Ids = new ArrayList<>();
    private int page;
    private int total_page;
    private List<UserData> data;
    private Button btnEkle;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        page = 1;
        recyclerView = findViewById(R.id.userListReyclerView);
        recyclerView.addOnScrollListener(new UserScrollListener());

        btnEkle = findViewById(R.id.userListAddUser);
        btnEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersListActivity.this,AddUserActivity.class);
                startActivity(intent);
            }
        });
        initUserList(page);

    }

    public void toUserDetail() {
        Intent intent = new Intent(UsersListActivity.this,UserDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("userid",1);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class UserScrollListener extends RecyclerView.OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                if (page <= total_page) {
                    initUserList(page);
                }
            }
        }
    }

    private void initUserList(int page) {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        UserService users = retrofit.create(UserService.class);
        Call<UserListResponse> call = users.getUsers(page);
        call.enqueue(new Callback<UserListResponse>() {
            @Override
            public void onResponse(Call<UserListResponse> call, Response<UserListResponse> response) {
                total_page = response.body().totalPages;
                data = response.body().data;

                for (int i = 0; i < data.size(); i++) {
                    imageNames.add(data.get(i).firstName);
                    imageUrls.add(data.get(i).avatar);
                    Ids.add(data.get(i).id);
                }
                initializeRecycler();
            }

            @Override
            public void onFailure(Call<UserListResponse> call, Throwable t) {
                Toast.makeText(UsersListActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeRecycler() {
        RecyclerView recyclerView = findViewById(R.id.userListReyclerView);
        RecyclerAdapter adapter = new RecyclerAdapter(this,imageNames,imageUrls,Ids);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        page++;
    }
}
