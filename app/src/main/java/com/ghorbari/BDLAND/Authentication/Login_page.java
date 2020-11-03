package com.ghorbari.BDLAND.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ghorbari.BDLAND.R;

public class Login_page extends AppCompatActivity {
    private Button log_in_btn;
    private Button sign_in_btn;
    private Button Guest_log_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log_in_btn = findViewById(R.id.log_btn);
        sign_in_btn = findViewById(R.id.sign_up_btn_page);

        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Login_page.this, Login_by_user.class));
            }
        });

        sign_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_page.this, SignUp_New_Members_BDLand.class));
            }
        });
    }
}