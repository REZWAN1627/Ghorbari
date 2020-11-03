package com.ghorbari.BDLAND.Hotel_Room_Booking;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.ghorbari.BDLAND.R;
import com.ghorbari.BDLAND.Fragment_UI.Room_Details_frag;
import com.google.android.material.navigation.NavigationView;

public class Room_Details extends AppCompatActivity implements Room_Details_frag.onFragmentBtnSelected {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room__details);

        drawerLayout = findViewById(R.id.drawer_layout_ghorbari_details_page);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.navigationBar_ghorbari_details_page);
        toolbar = findViewById(R.id.ghorbari_details_page_toolbar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container_details_page, new Room_Details_frag());
        fragmentTransaction.commit();
    }


    @Override
    public void onButtonSelected() {

        startActivity(new Intent(Room_Details.this, Hotels_details.class));

    }
}