package com.ghorbari.BDLAND.DataBase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Email_user_database extends AppCompatActivity {

    public static final String TAG = "TAG";

    private String UserEmail;
    private TextView userInfo_tv;
    private EditText First_name_txt;
    private EditText Last_name_txt;
    private EditText phoneNumber_txt;
    private Button Email_user_addInfo_btn;

    private Button send_phoneNumber_Otp;
    private Button Resend_phoneNumber_Otp;
    private ProgressBar PopUpProgressBar;
    private TextView pop_process_phone_txt;

    private CountryCodePicker countryCodePicker;

    private AlertDialog.Builder DialogBuilder; // for pop up dialog
    private AlertDialog dialog;//pop up dialog

    private EditText otpNumberOne, getOtpNumberTwo, getOtpNumberThree, getOtpNumberFour, getOtpNumberFive, otpNumberSix;
    private TextView user_phoneNumber_txt;
    private Boolean otpValid = true;
    private Boolean VerificationOnProgress = false;//to use send button as verified button
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken token;
    private String verificationId;
    private Boolean flag = false;//to hide resend button

    private String Fname, Sname, UserPhoneNumber;

    private String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_user_database);
        userInfo_tv = findViewById(R.id.user_info);
        First_name_txt = findViewById(R.id.Email_user_Fname);
        Last_name_txt = findViewById(R.id.Email_user_Sname);
        phoneNumber_txt = findViewById(R.id.Email_user_phone);
        Email_user_addInfo_btn = findViewById(R.id.Email_user_addInfo_btn);
        countryCodePicker = findViewById(R.id.ccp_email);


        Intent data = getIntent();
        UserEmail = data.getStringExtra("CurrentUserEmail");

        userInfo_tv.setText(UserEmail);

        Email_user_addInfo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Fname = First_name_txt.getText().toString().trim();
                Sname = Last_name_txt.getText().toString().trim();


                if (Fname.isEmpty()) {
                    First_name_txt.setError("Field is Empty!");
                }
                if (Sname.isEmpty()) {
                    Last_name_txt.setError("Field is Empty!");
                }
                if (!phoneNumber_txt.getText().toString().isEmpty() && phoneNumber_txt.getText().toString().length() == 10) {
                    UserPhoneNumber = "+" + countryCodePicker.getSelectedCountryCode() + phoneNumber_txt.getText().toString();
                    CreateNewVerifiedDialog();
                } else {
                    phoneNumber_txt.setError("Invalid Number");
                    Toast.makeText(Email_user_database.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void CreateNewVerifiedDialog() {

        DialogBuilder = new AlertDialog.Builder(this);

        final View verificationPopUp = getLayoutInflater().inflate(R.layout.popup_phoneverification, null);//layout
        otpNumberOne = verificationPopUp.findViewById(R.id.otpNumberOne);
        getOtpNumberTwo = verificationPopUp.findViewById(R.id.optNumberTwo);
        getOtpNumberThree = verificationPopUp.findViewById(R.id.otpNumberThree);
        getOtpNumberFour = verificationPopUp.findViewById(R.id.otpNumberFour);
        getOtpNumberFive = verificationPopUp.findViewById(R.id.otpNumberFive);
        otpNumberSix = verificationPopUp.findViewById(R.id.optNumberSix);

        user_phoneNumber_txt = verificationPopUp.findViewById(R.id.user_phoneNumber);
        send_phoneNumber_Otp = verificationPopUp.findViewById(R.id.verify_otp_phone_btn);
        Resend_phoneNumber_Otp = verificationPopUp.findViewById(R.id.re_send_otp_phone_btn);

        PopUpProgressBar = verificationPopUp.findViewById(R.id.progressBar_for_otp);
        pop_process_phone_txt = verificationPopUp.findViewById(R.id.SendingOtp);


        DialogBuilder.setView(verificationPopUp);
        dialog = DialogBuilder.create();
        dialog.show();

        user_phoneNumber_txt.setText(UserPhoneNumber);

        send_phoneNumber_Otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!VerificationOnProgress) {

                    Log.d(TAG, "OnClick: Phone number --> " + UserPhoneNumber);
                    PopUpProgressBar.setVisibility(View.VISIBLE);
                    pop_process_phone_txt.setText("Sending OTP..");
                    pop_process_phone_txt.setVisibility(View.VISIBLE);
                    sendOTP(UserPhoneNumber);

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

                        verifyCode(otp);


                    }
                }
            }
        });

        Resend_phoneNumber_Otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpProgressBar.setVisibility(View.VISIBLE);
                resendOTP(UserPhoneNumber);
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                verificationId = s;
                token = forceResendingToken;
                PopUpProgressBar.setVisibility(View.GONE);
                VerificationOnProgress = true;
                send_phoneNumber_Otp.setText("Verify");
                pop_process_phone_txt.setVisibility(View.GONE);
                Resend_phoneNumber_Otp.setVisibility(View.GONE);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                if (flag) {
                    Resend_phoneNumber_Otp.setVisibility(View.GONE);

                } else {
                    Resend_phoneNumber_Otp.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();
                ArrayList<String> tmep_code = new ArrayList<>();
                if (code != null) {
                    for (int i = 0; i < code.length(); i++) {
                        tmep_code.add(i, String.valueOf(code.charAt(i)));
                    }
                    otpNumberOne.setText(tmep_code.get(0));
                    getOtpNumberTwo.setText(tmep_code.get(1));
                    getOtpNumberThree.setText(tmep_code.get(2));
                    getOtpNumberFour.setText(tmep_code.get(3));
                    getOtpNumberFive.setText(tmep_code.get(4));
                    otpNumberSix.setText(tmep_code.get(5));

                    PopUpProgressBar.setVisibility(View.GONE);

                    verifyCode(code);
                }


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Email_user_database.this, "OTP Verification Failed" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
                return;

            }
        };


    }

    private void verifyCode(String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);//Link UP with Email if not exits
        /** Ei part ta baki ache
         * link up kora
         * check kora jodi mobile number exits thake
         * na thakle link up kore dawa oi email er sate
         *
         * **/

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        UserId = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(UserId);
        Intent data = getIntent();
        String UserEmail = data.getStringExtra("CurrentUserEmail");
        Map<String, Object> Client = new HashMap<>();

        Client.put("First Name", Fname);
        Client.put("Last Name", Sname);
        Client.put("Email", UserEmail);
        Client.put("Phone Number", UserPhoneNumber);
        documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Toast.makeText(Email_user_database.this, "Verified User Information", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Email_user_database.this, Main_Page.class));
                dialog.dismiss();
                finish();

            }
        });


    }

    private void resendOTP(String userPhoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, token);
    }

    private void sendOTP(String userPhoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(userPhoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);
    }

    public void validateField(EditText field) {
        if (field.getText().toString().isEmpty()) {
            field.setError("Required");
            otpValid = false;
        } else {
            otpValid = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();

    }
}