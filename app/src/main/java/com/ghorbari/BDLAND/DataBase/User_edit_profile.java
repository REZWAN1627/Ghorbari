package com.ghorbari.BDLAND.DataBase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;

public class User_edit_profile extends AppCompatActivity {

    private Button exit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_profile);

        exit_btn = findViewById(R.id.exit_button_edit_profile);

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_edit_profile.this, Main_Page.class));
                finish();
            }
        });
    }
}