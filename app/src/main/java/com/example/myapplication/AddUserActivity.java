package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.utils.ApiUrl;
import com.example.myapplication.utils.UserService;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddUserActivity extends AppCompatActivity {

    private EditText edtFirstName;
    private EditText edtLastName;
    private Button btnOlustur;
    private Button btnIptal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        edtFirstName = findViewById(R.id.addUserFirstName);
        edtLastName = findViewById(R.id.addUserLastName);
        btnOlustur = findViewById(R.id.addUserBtnOlustur);
        btnIptal = findViewById(R.id.addUserBtnIptal);

        btnOlustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(v);
            }
        });

    }

    private void addUser(View v) {
        String name = edtFirstName.getText().toString();
        String lastName = edtLastName.getText().toString();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserService users = retrofit.create(UserService.class);

        Call<UserCreateResponse> call = users.addUser(name,lastName);
        call.enqueue(new Callback<UserCreateResponse>() {
            @Override
            public void onResponse(Call<UserCreateResponse> call, Response<UserCreateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddUserActivity.this,"Kullanıcı başarıyla eklendi.",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    String resp = null;
                    String errorMessage = "";
                    try {
                        resp = response.errorBody().string();
                        JSONObject jsonObject = new JSONObject(resp);
                        errorMessage = jsonObject.getString("error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AddUserActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserCreateResponse> call, Throwable t) {
                Toast.makeText(AddUserActivity.this,"Error..",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
