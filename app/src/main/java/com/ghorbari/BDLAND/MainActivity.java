package com.ghorbari.BDLAND;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ghorbari.BDLAND.Authentication.Login_page;

public class MainActivity extends AppCompatActivity {
    private Button stat_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stat_btn = findViewById(R.id.khela_suru);

        stat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(MainActivity.this, Login_page.class));
            }
        });

    }
}