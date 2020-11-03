package com.ghorbari.BDLAND.Hotel_Room_Booking;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ghorbari.BDLAND.R;
import com.ghorbari.BDLAND.Fragment_UI.Booking_page_farg;
import com.ghorbari.BDLAND.Fragment_UI.Hotel_Details_frag;
import com.google.android.material.navigation.NavigationView;

public class Hotels_details extends AppCompatActivity implements Hotel_Details_frag.onFragmentBtnSelected, Booking_page_farg.onFragmentBtnSelected {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_details);

        drawerLayout = findViewById(R.id.drawer_layout_ghorbari_hotel_details_page);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.navigationBar_ghorbari_hotel_details_page);
        toolbar = findViewById(R.id.ghorbari_hotel_details_page_toolbar);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_hotel_details_page, new Hotel_Details_frag());
        fragmentTransaction.commit();
    }

    @Override
    public void onButtonSelected() {
        Toast.makeText(this, "wow", Toast.LENGTH_SHORT).show();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_hotel_details_page, new Booking_page_farg());
        fragmentTransaction.commit();
    }

    @Override
    public void onbuttonSelected() {
        startActivity(new Intent(Hotels_details.this, Confirmation_page.class));

    }
}