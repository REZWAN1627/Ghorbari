package com.ghorbari.BDLAND.DataBase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PhoneNumber_user_database extends AppCompatActivity {

    private EditText phone_user_first_name_txt;
    private EditText phone_user_second_name_txt;
    private TextView phone_user_phone_number1_txt;
    private TextView phone_user_phone_number2_txt;
    private Button phone_user_SignUp_btn;
    private ProgressBar progressBar;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private String UserId;
    private DocumentReference documentReference;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_user_database);

        Intent data = getIntent();
        phone = data.getStringExtra("phone");

        phone_user_phone_number1_txt = findViewById(R.id.user2);//input extra
        phone_user_first_name_txt = findViewById(R.id.phone_user_Fname);
        phone_user_second_name_txt = findViewById(R.id.phone_user_Sname);
        phone_user_phone_number2_txt = findViewById(R.id.phone_user_phone);
        phone_user_SignUp_btn = findViewById(R.id.phone_user_SignUp_btn);
        progressBar = findViewById(R.id.phone_user_progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        phone_user_phone_number1_txt.setText(phone);
        phone_user_phone_number2_txt.setText(phone);

        phone_user_SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PFName = phone_user_first_name_txt.getText().toString().trim();
                String PSName = phone_user_second_name_txt.getText().toString().trim();
                if (TextUtils.isEmpty(PFName)) {
                    phone_user_first_name_txt.setError("Name is Required");
                    return;

                }
                if (TextUtils.isEmpty(PSName)) {
                    phone_user_second_name_txt.setError("Name is Required");
                    return;
                }


                progressBar.setVisibility(view.VISIBLE);


                UserId = firebaseAuth.getCurrentUser().getUid();
                documentReference = firebaseFirestore.collection("users").document(UserId);
                Map<String, Object> Client = new HashMap<>();

                Client.put("First Name", PFName);
                Client.put("Second Name", PSName);
                Client.put("Email", null);
                Client.put("Phone Number", phone);
                documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(PhoneNumber_user_database.this, "Information Stored Successfully", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        startActivity(new Intent(PhoneNumber_user_database.this, Main_Page.class));
                        finish();

                    }
                });

            }
        });
    }
}