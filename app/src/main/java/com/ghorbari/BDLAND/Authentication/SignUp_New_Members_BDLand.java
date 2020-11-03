package com.ghorbari.BDLAND.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ghorbari.BDLAND.MainLayout.Main_Page;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SignUp_New_Members_BDLand extends AppCompatActivity {

    public static final String TAG = "TAG";

    private Button SignUp_btn;
    private EditText UserFirstName_txt;
    private EditText UserSecondName_txt;
    private EditText UserEmail_txt;
    private EditText UserPass_txt;
    private EditText UserRePass_txt;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference documentReference;
    private String UserId;

    private String fName;
    private String sName;
    private String email;
    private String password;
    private String Re_pass;

    private AlertDialog.Builder DialogBuilder; // for pop up dialog
    private AlertDialog dialog;//pop up dialog

    private EditText pop_phoneNumber_txt;
    private TextView pop_emailAddress_tv;
    private ProgressBar PopUpProgressBar;
    private ProgressBar PopUpProgressBar_second;
    private TextView pop_process_phone_txt;
    private TextView pop_process_email_txt;
    private Button pop_phone_verify_btn;
    private Button pop_email_verify_btn;
    private Boolean VerificationOnProgress = false;//to use send button as verified button
    private Boolean flag = false;//to hide resend button
    private Button pop_resend_btn;

    private CountryCodePicker countryCodePicker;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken token;


    private EditText otpNumberOne, getOtpNumberTwo, getOtpNumberThree, getOtpNumberFour, getOtpNumberFive, otpNumberSix;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private Boolean otpValid = true;
    private String countryCode;

    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_to_ghorbari);
        SignUp_btn = findViewById(R.id.user_SignUp_btn);
        UserFirstName_txt = findViewById(R.id.user_Fname);
        UserSecondName_txt = findViewById(R.id.user_Sname);
        UserEmail_txt = findViewById(R.id.user_email);
        UserPass_txt = findViewById(R.id.user_pass);
        UserRePass_txt = findViewById(R.id.user_pass_again);
        progressBar = findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        SignUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fName = UserFirstName_txt.getText().toString().trim();
                sName = UserSecondName_txt.getText().toString().trim();
                email = UserEmail_txt.getText().toString().trim();
                password = UserPass_txt.getText().toString().trim();
                Re_pass = UserRePass_txt.getText().toString().trim();

                if (TextUtils.isEmpty(fName)) {
                    UserFirstName_txt.setError("Name required");
                    return;
                }

                if (TextUtils.isEmpty(sName)) {
                    UserSecondName_txt.setError("Name Field is Empty");
                    return;
                }


                if (TextUtils.isEmpty(email)) {
                    UserEmail_txt.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(Re_pass)) {
                    UserPass_txt.setError("Password is Required or Re-Type Password is Required");
                    UserPass_txt.setText(" ");
                    return;
                }

                if (!password.equals(Re_pass)) {
                    UserPass_txt.setError("Password not Match");
                    UserPass_txt.setText(" ");
                    return;

                }

                if (password.length() < 6) {
                    UserPass_txt.setError("Password must be more than 6 characters");
                    return;
                }
                progressBar.setVisibility(view.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        CreateNewVerifiedDialog();


                                    } else {
                                        Toast.makeText(SignUp_New_Members_BDLand.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.INVISIBLE);
                                        return;
                                    }

                                }
                            });


                        } else {
                            Toast.makeText(SignUp_New_Members_BDLand.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            return;
                        }

                    }
                });

            }
        });

    }

    public void CreateNewVerifiedDialog() {

        DialogBuilder = new AlertDialog.Builder(this);

        final View verificationPopUp = getLayoutInflater().inflate(R.layout.verification_popup, null);//layout

        pop_phoneNumber_txt = verificationPopUp.findViewById(R.id.user_phone_otp_pop);
        pop_emailAddress_tv = verificationPopUp.findViewById(R.id.textView2_emailAddress);
        PopUpProgressBar = verificationPopUp.findViewById(R.id.progressBar_for_otp);
        PopUpProgressBar_second = verificationPopUp.findViewById(R.id.progressBar_for_email);
        pop_process_phone_txt = verificationPopUp.findViewById(R.id.SendingOtp);
        pop_process_email_txt = verificationPopUp.findViewById(R.id.SendingEmailVerification);
        pop_phone_verify_btn = verificationPopUp.findViewById(R.id.phone_auth_btn_pop);
        pop_email_verify_btn = verificationPopUp.findViewById(R.id.phone_auth_btn_pop2);
        pop_resend_btn = verificationPopUp.findViewById(R.id.POP_phone_auth_resend_btn);
        linearLayout = verificationPopUp.findViewById(R.id.OTP_liner_layout);

        otpNumberOne = verificationPopUp.findViewById(R.id.otpNumberOne);
        getOtpNumberTwo = verificationPopUp.findViewById(R.id.optNumberTwo);
        getOtpNumberThree = verificationPopUp.findViewById(R.id.otpNumberThree);
        getOtpNumberFour = verificationPopUp.findViewById(R.id.otpNumberFour);
        getOtpNumberFive = verificationPopUp.findViewById(R.id.otpNumberFive);
        otpNumberSix = verificationPopUp.findViewById(R.id.optNumberSix);


        countryCodePicker = verificationPopUp.findViewById(R.id.ccp_pop);

        DialogBuilder.setView(verificationPopUp);
        dialog = DialogBuilder.create();
        dialog.show();

        pop_emailAddress_tv.setText(email);
        linearLayout.setVisibility(View.GONE);

        pop_phone_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!VerificationOnProgress) {
                    if (!pop_phoneNumber_txt.getText().toString().isEmpty() && pop_phoneNumber_txt.getText().toString().length() == 10) {
                        countryCode = "+" + countryCodePicker.getSelectedCountryCode() + pop_phoneNumber_txt.getText().toString();
                        Log.d(TAG, "OnClick: Phone number --> " + countryCode);
                        PopUpProgressBar.setVisibility(View.VISIBLE);
                        pop_process_phone_txt.setText("Sending OTP..");
                        pop_process_phone_txt.setVisibility(View.VISIBLE);
                        sendOTP(countryCode);
                    } else {
                        pop_phoneNumber_txt.setError("Phone Number is not Valid!");
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

        pop_resend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopUpProgressBar.setVisibility(View.VISIBLE);
                resendOTP(countryCode);
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
                pop_phone_verify_btn.setText("Verify");
                pop_process_phone_txt.setVisibility(View.GONE);
                pop_resend_btn.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);


            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                if (flag) {
                    pop_resend_btn.setVisibility(View.GONE);

                } else {
                    pop_resend_btn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuthentication(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(SignUp_New_Members_BDLand.this, "OTP Verification Failed" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        };


        pop_email_verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified() && user != null) {

                                pop_process_email_txt.setText("Verified");
                                UserId = firebaseAuth.getCurrentUser().getUid();
                                documentReference = firebaseFirestore.collection("users").document(UserId);
                                Map<String, Object> Client = new HashMap<>();

                                Client.put("First Name", fName);
                                Client.put("Last Name", sName);
                                Client.put("Email", email);
                                Client.put("Phone Number", countryCode);
                                documentReference.set(Client).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(SignUp_New_Members_BDLand.this, "Information Stored Successfully", Toast.LENGTH_LONG).show();
                                        PopUpProgressBar.setVisibility(View.GONE);
                                        Toast.makeText(SignUp_New_Members_BDLand.this, "Sign In", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignUp_New_Members_BDLand.this, Main_Page.class));
                                        dialog.dismiss();
                                        finish();

                                    }
                                });

                            } else {
                                pop_process_email_txt.setText("Email isn't verified check your Email!");
                            }

                        } else {
                            pop_process_email_txt.setText("Error!");
                            return;
                        }

                    }
                });


            }
        });

    }

    private void verifyAuthentication(PhoneAuthCredential credential) {//link up

        firebaseAuth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DocumentReference documentReference = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());
                    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {

                            if (documentSnapshot.exists()) {
                                Toast.makeText(SignUp_New_Members_BDLand.this, "signed IN", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp_New_Members_BDLand.this, Main_Page.class));


                            } else {
                                Toast.makeText(SignUp_New_Members_BDLand.this, "REGISTRATION", Toast.LENGTH_SHORT).show();//registration activity
                                countryCodePicker.setVisibility(View.GONE);
                                pop_phoneNumber_txt.setVisibility(View.GONE);
                                PopUpProgressBar.setVisibility(View.GONE);
                                pop_phone_verify_btn.setVisibility(View.GONE);
                                pop_process_phone_txt.setVisibility(View.GONE);
                                pop_resend_btn.setVisibility(View.GONE);
                                linearLayout.setVisibility(View.GONE);
                                flag = true;
                                pop_process_email_txt.setVisibility(View.VISIBLE);
                                pop_process_email_txt.setText("Verification code send");
                                PopUpProgressBar_second.setVisibility(View.VISIBLE);


                                return;

                            }


                        }
                    });
                } else {
                    Toast.makeText(SignUp_New_Members_BDLand.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void sendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks);

    }

    public void resendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this, mCallbacks, token);
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