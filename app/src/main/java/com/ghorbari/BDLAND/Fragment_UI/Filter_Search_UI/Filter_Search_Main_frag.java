package com.ghorbari.BDLAND.Fragment_UI.Filter_Search_UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghorbari.BDLAND.R;


public class Filter_Search_Main_frag extends Fragment {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private Button residential_btn;
    private Button commercial_btn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_filter__search_frag, container, false);

        residential_btn = view.findViewById(R.id.filter_Search_residential_btn);
        commercial_btn = view.findViewById(R.id.filter_Search_commercial_btn);

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout_property_type, new residential_filterSearch_frag());
        fragmentTransaction.commit();

        residential_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout_property_type, new residential_filterSearch_frag());
                fragmentTransaction.commit();
            }
        });

        commercial_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.framelayout_property_type, new commercial_filterSearch_frag());
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}