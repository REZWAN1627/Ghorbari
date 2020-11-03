package com.ghorbari.BDLAND.MainLayout.UserDetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_profile_details extends AppCompatActivity {

    private CircleImageView circleImageView;
    private Button exit_btn;
    private TextView user_firstName_tv;
    private TextView user_phoneNumber_tv;
    private TextView user_emailAddress_tv;
    private TextView user_Location_tv;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_details);

        user_firstName_tv = findViewById(R.id.user_firstName_tv);
        user_phoneNumber_tv = findViewById(R.id.user_phoneNumber_tv);
        user_emailAddress_tv = findViewById(R.id.user_emailAddress_tv);
        user_Location_tv = findViewById(R.id.user_Location_tv);
        exit_btn = findViewById(R.id.exit_button_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();


        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                user_firstName_tv.setText(value.getString("First Name") + " " + value.getString("Last Name"));
                user_emailAddress_tv.setText("   " + value.getString("Email"));
                user_phoneNumber_tv.setText("   " + value.getString("Phone Number"));

                if (!value.exists()) {
                    Toast.makeText(User_profile_details.this, "ERROR!" + error, Toast.LENGTH_SHORT).show();
                }

            }
        });

        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User_profile_details.this, Main_Page.class));
                finish();
            }
        });
    }
}