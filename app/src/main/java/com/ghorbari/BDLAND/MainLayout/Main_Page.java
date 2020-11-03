package com.ghorbari.BDLAND.MainLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.ghorbari.BDLAND.Authentication.Login_by_user;
import com.ghorbari.BDLAND.FilterSearch.Filter_Search;
import com.ghorbari.BDLAND.Hotel_Room_Booking.Hotels_details;
import com.ghorbari.BDLAND.Fragment_UI.Ghorbari_Page_main_frag;

import com.ghorbari.BDLAND.MainLayout.UserDetails.User_profile_details;
import com.ghorbari.BDLAND.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.List;

public class Main_Page extends AppCompatActivity implements Ghorbari_Page_main_frag.onFragmentBtnSelected, NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button signout_btn;


    //to create dialog box of google account
    private List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.GoogleBuilder().build());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__page);


        drawerLayout = findViewById(R.id.drawer_layout_ghorbari_main_page);

        setSupportActionBar(toolbar);

        signout_btn = findViewById(R.id.logout_btn);
        navigationView = findViewById(R.id.navigationBar_ghorbari_main_page);

        navigationView.setNavigationItemSelectedListener(this);


        toolbar = findViewById(R.id.ghorbari_toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new Ghorbari_Page_main_frag());
        fragmentTransaction.commit();

        signout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });


    }

    private void SignOut() {

        //logout from firebase auth fully to see google account dialogBox again
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Main_Page.this, "User Signed Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main_Page.this, Login_by_user.class));
                        finish();
                    }
                });


    }


    @Override
    public void onButtonSelected() {

        Toast.makeText(this, "its working", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, Hotels_details.class));


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if (item.getItemId() == R.id.profile) {

            startActivity(new Intent(this, User_profile_details.class));//

        }else if (item.getItemId() == R.id.filter_search){

            startActivity(new Intent(this, Filter_Search.class));

        }


        return true;
    }
}