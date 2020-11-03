package com.ghorbari.BDLAND.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ghorbari.BDLAND.DataBase.Email_user_database;
import com.ghorbari.BDLAND.DataBase.PhoneNumber_user_database;
import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login_by_user extends AppCompatActivity {

    private static final int RC_SIGN_IN = 0;
    private Button continue_btn;
    private Button google_signIn_btn;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private TextView CreateAccount_txt;
    private EditText user_email_registered_txt;
    private EditText user_password_registered_txt;

    private TextView forgotten_password_txt;
    private Button Log_in_with_phone_btn;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified()) {
            ValidationCheck();
        }

    } // to check wether user is still log in or not


    private void ValidationCheck() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                    Toast.makeText(Login_by_user.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_by_user.this, Main_Page.class));
                } else {
                    Toast.makeText(Login_by_user.this, "REGISTRATION PLEASE!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login_by_user.this, Email_user_database.class));
                    finish();

                }


            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_user);

        mAuth = FirebaseAuth.getInstance();
        CreateRequest();//creating request to firebase


        continue_btn = findViewById(R.id.Log_in_continue_btn);
        google_signIn_btn = findViewById(R.id.sign_in_with_google);
        CreateAccount_txt = findViewById(R.id.create_account_text_page);

        user_email_registered_txt = findViewById(R.id.user_email_registered);
        user_password_registered_txt = findViewById(R.id.user_password_registered);
        forgotten_password_txt = findViewById(R.id.forgotten_password);
        Log_in_with_phone_btn = findViewById(R.id.sign_in_with_phone);


        google_signIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });


        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email_registered_txt.getText().toString().trim();
                String password = user_password_registered_txt.getText().toString().trim();

                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_by_user.this, "Email or password Field is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            final String user2 = user.getEmail();
                            if (user.isEmailVerified() && user != null) {
                                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                                        if (documentSnapshot.exists()) {
                                            Toast.makeText(Login_by_user.this, "Welcome", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login_by_user.this, Main_Page.class));
                                            finish();
                                        } else {
                                            Toast.makeText(Login_by_user.this, "REGISTRATION PLEASE!", Toast.LENGTH_SHORT).show();
                                            Intent CurrentUser = new Intent(Login_by_user.this, Email_user_database.class);
                                            CurrentUser.putExtra("CurrentUserEmail", user2);
                                            startActivity(CurrentUser);
                                            finish();


                                        }

                                    }
                                });
                            } else {
                                Toast.makeText(Login_by_user.this, "Please verify your email Address", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login_by_user.this, "Error! The Email or password are Incorrect", Toast.LENGTH_SHORT).show();
                            return;
                        }

                    }
                });

            }
        });

        CreateAccount_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login_by_user.this, SignUp_New_Members_BDLand.class));
            }
        });

        forgotten_password_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset password ?");
                passwordResetDialog.setMessage("Enter your Email to Reset the password Link");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login_by_user.this, "Reset link Sent to your Email", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login_by_user.this, "Error! Reset link not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passwordResetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //close dialog

                    }
                });

                passwordResetDialog.create().show();
            }
        });

        Log_in_with_phone_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user2 = mAuth.getCurrentUser();
                FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();

                if (user2 != null) {
                    final String phone = user3.getPhoneNumber();
                    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Toast.makeText(Login_by_user.this, "Welcome Back", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_by_user.this, Main_Page.class));
                                finish();
                            } else {
                                Toast.makeText(Login_by_user.this, "REGISTRATION PLEASE!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login_by_user.this, PhoneNumber_user_database.class).putExtra("phone", phone));
                                finish();
                            }

                        }
                    });

                } else {
                    startActivity(new Intent(Login_by_user.this, phone_number_verification.class));
                    finish();
                }
            }
        });

    }

    private void CreateRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    firebaseAuthWithGoogle(account.getIdToken());
                }


            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "failed to create", Toast.LENGTH_SHORT).show();
                // ...
            }
        }

    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String UserEmail = mAuth.getCurrentUser().getEmail();
                            startActivity(new Intent(Login_by_user.this, Email_user_database.class).putExtra("CurrentUserEmail", UserEmail));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Login_by_user.this, "Failed to login", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }


}