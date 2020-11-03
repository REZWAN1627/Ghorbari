package com.ghorbari.BDLAND.DataBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Image_upload_database extends AppCompatActivity {

    public static final int CAMERA_PERM_CODE = 102;
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final String TAG = "TAG";
    public static final int GALLERY_REQUEST_CODE = 105;
    private ImageView imageView;
    private ImageView section_pic_1, section_pic_2, section_pic_3;

    private Button next_btn;
    private EditText Number_of_rooms;
    private EditText flat_location;
    private EditText flat_number;
    private EditText flat_rent;
    private EditText flat_holding_number;
    private EditText flat_description;

    private Button camera_cap_btn;
    private Button gallery_cap_btn;

    private StorageReference mStorageRef;

    private String currentPhotoPath;

    private boolean flag = true;

    private int count = 0;

    private String House_Area = "NAZIPUR", House_Number = "E-0066";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_database);

        imageView = findViewById(R.id.imageView);
        camera_cap_btn = findViewById(R.id.take_photo);
        gallery_cap_btn = findViewById(R.id.from_galary);

        Number_of_rooms = findViewById(R.id.Number_of_rooms);
        flat_number = findViewById(R.id.flat_number);
        flat_rent = findViewById(R.id.flat_rent);
        flat_holding_number = findViewById(R.id.House_Number);
        flat_location = findViewById(R.id.location_extract);
        flat_description = findViewById(R.id.flat_description);

        next_btn = findViewById(R.id.upload_to_database);

        section_pic_1 = findViewById(R.id.pic1);
        section_pic_2 = findViewById(R.id.pic12);
        section_pic_3 = findViewById(R.id.pic13);

        mStorageRef = FirebaseStorage.getInstance().getReference();





    }
    

}