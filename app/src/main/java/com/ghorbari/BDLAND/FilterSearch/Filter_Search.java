package com.ghorbari.BDLAND.FilterSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ghorbari.BDLAND.Fragment_UI.Filter_Search_UI.Filter_Search_Main_frag;
import com.ghorbari.BDLAND.Fragment_UI.Ghorbari_Page_main_frag;
import com.ghorbari.BDLAND.R;

public class Filter_Search extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter__search);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.filterSearch_frag, new Filter_Search_Main_frag());
        fragmentTransaction.commit();
    }
}