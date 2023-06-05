package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondActivity extends AppCompatActivity {
    private ThemesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ListView listView = findViewById(R.id.listTests);


        adapter = new ThemesAdapter(this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                ThemeInfo c = adapter.getItem(position);

                Intent intent = new Intent(SecondActivity.this, TestingActivity.class);
                intent.putExtra(TestingActivity.TEST_NAME_ARG, c.file);
                startActivity(intent);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/kirieshka1934/rus-test-info/main/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TestService service = retrofit.create(TestService.class);
        Call<AllThemesInfo> call = service.fetchAllTests();
        call.enqueue(new Callback<AllThemesInfo>() {
            @Override
            public void onResponse(Call<AllThemesInfo> call, Response<AllThemesInfo> response) {
                AllThemesInfo b = response.body();
                if (b == null){
                    Toast.makeText(SecondActivity.this, "Не получилось загрузить темы", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                adapter.clear();
                adapter.addAll(b.themes);
            }

            @Override
            public void onFailure(Call<AllThemesInfo> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(SecondActivity.this, "Не получилось загрузить темы", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
