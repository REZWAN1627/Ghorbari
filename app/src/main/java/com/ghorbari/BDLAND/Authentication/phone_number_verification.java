package com.ghorbari.BDLAND.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ghorbari.BDLAND.DataBase.PhoneNumber_user_database;
import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class phone_number_verification extends AppCompatActivity {


    public static final String TAG = "TAG";
    private FirebaseAuth firebaseAuth;
    private EditText User_Phone_number_txt;
    private Button next_btn;
    private ProgressBar progressBar;
    private TextView resend_txt;
    private Button resend_OTP_btn;
    private EditText otpNumberOne, getOtpNumberTwo, getOtpNumberThree, getOtpNumberFour, getOtpNumberFive, otpNumberSix;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private Boolean otpValid = true;

    private CountryCodePicker countryCodePicker;

    private String verificationId;

    private PhoneAuthProvider.ForceResendingToken token;
    private Boolean VerificationOnProgress = false;
    private FirebaseFirestore firebaseFirestore;

    private String CountryCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number_verification);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        User_Phone_number_txt = findViewById(R.id.user_phone_otp);
        next_btn = findViewById(R.id.phone_auth_btn);
        progressBar = findViewById(R.id.progressBar_phone_auth);
        resend_txt = findViewById(R.id.resendOtpBtn);
        countryCodePicker = findViewById(R.id.ccp);
        resend_OTP_btn = findViewById(R.id.phone_auth_resend_btn);

        otpNumberOne = findViewById(R.id.otpNumberOne);
        getOtpNumberTwo = findViewById(R.id.optNumberTwo);
        getOtpNumberThree = findViewById(R.id.otpNumberThree);
        getOtpNumberFour = findViewById(R.id.otpNumberFour);
        getOtpNumberFive = findViewById(R.id.otpNumberFive);
        otpNumberSix = findViewById(R.id.optNumberSix);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!VerificationOnProgress) {
                    if (!User_Phone_number_txt.getText().toString().isEmpty() && User_Phone_number_txt.getText().toString().length() == 10) {

                        CountryCode = "+" + countryCodePicker.getSelectedCountryCode() + User_Phone_number_txt.getText().toString();
                        Log.d(TAG, "OnClick: Phone number --> " + CountryCode);
                        progressBar.setVisibility(View.VISIBLE);
                        resend_txt.setText("Sending OTP..");
                        resend_txt.setVisibility(View.VISIBLE);
                        sendOTP(CountryCode);


                    } else {
                        User_Phone_number_txt.setError("Phone Number is not Valid!");
                    }

                } else {
                    validateField(otpNumberOne);
                    validateField(getOtpNumberTwo);
                    validateField(getOtpNumberThree);
                    validateField(getOtpNumberFour);
                    validateField(getOtpNumberFive);
                    validateField(otpNumberSix);
                    if (otpValid) {
                        // send otp to the user
                        String otp = otpNumberOne.getText().toString() + getOtpNumberTwo.getText().toString() + getOtpNumberThree.getText().toString() + getOtpNumberFour.getText().toString() +
                                getOtpNumberFive.getText().toString() + otpNumberSix.getText().toString();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);

                        verifyAuthentication(credential);

                    }

                }


            }
        });
        resend_OTP_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                resendOTP(CountryCode);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                progressBar.setVisibility(View.GONE);
                VerificationOnProgress = true;
                next_btn.setText("Verify");
                resend_txt.setVisibility(View.GONE);
                resend_OTP_btn.setVisibility(View.GONE);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resend_OTP_btn.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(phone_number_verification.this, "OTP Verification Failed" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        };


    }

    private void verifyAuthentication(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    final DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Toast.makeText(phone_number_verification.this, "signed IN", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(phone_number_verification.this, Main_Page.class));
                                finish();
                            } else {
                                Toast.makeText(phone_number_verification.this, "REGISTRATION", Toast.LENGTH_SHORT).show();//registration activity
                                startActivity(new Intent(phone_number_verification.this, PhoneNumber_user_database.class).putExtra("phone", CountryCode));
                                finish();

                            }


                        }
                    });
                } else {
                    Toast.makeText(phone_number_verification.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void validateField(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Required");
            otpValid = false;
        } else {
            otpValid = true;
        }
    }


    public void sendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);
    }

    public void resendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, token);
    }

}