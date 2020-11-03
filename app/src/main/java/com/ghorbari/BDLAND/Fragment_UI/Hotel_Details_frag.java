package com.ghorbari.BDLAND.Fragment_UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghorbari.BDLAND.R;
import com.ghorbari.BDLAND.RecyclerView.RecyclerViewAdapterHotelDetailsPage;

import java.util.ArrayList;


public class Hotel_Details_frag extends Fragment {
    private onFragmentBtnSelected listener;
    private Button Hotel_BookIn_btn;
    private ArrayList<String> mHDBImages = new ArrayList<>();
    private ArrayList<String> mHDBNames = new ArrayList<>();

    private RecyclerView recyclerView;

    private RecyclerViewAdapterHotelDetailsPage recyclerViewAdapterHotelDetailsPage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel__details_frag, container, false);
        Hotel_BookIn_btn = view.findViewById(R.id.hotel_bookIn_btn);
        getsImages();
        recyclerView = view.findViewById(R.id.recyclerView_ghorbari_hotel_details_page1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewAdapterHotelDetailsPage = new RecyclerViewAdapterHotelDetailsPage(this.getContext(), mHDBImages, mHDBNames);
        recyclerView.setAdapter(recyclerViewAdapterHotelDetailsPage);
        Hotel_BookIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected();
            }
        });
        return view;
    }

    public void getsImages() {

        //Near Location Items Data set 1
        mHDBImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHDBNames.add("24/7 Water");

        //Near Location Items Data set 2
        mHDBImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHDBNames.add("Free Wifi");

        //Near Location Items Data set 3
        mHDBImages.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mHDBNames.add("free Parking");

        //Near Location Items Data set 4
        mHDBImages.add("https://i.redd.it/j6myfqglup501.jpg");
        mHDBNames.add("24/7 gas");

        //Near Location Items Data set 5
        mHDBImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHDBNames.add("Meal");

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");

        }
    }

    public interface onFragmentBtnSelected {
        public void onButtonSelected();
    }
}