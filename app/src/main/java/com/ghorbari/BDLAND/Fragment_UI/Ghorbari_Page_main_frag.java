package com.ghorbari.BDLAND.Fragment_UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghorbari.BDLAND.R;
import com.ghorbari.BDLAND.RecyclerView.RecyclerViewAdapter;
import com.ghorbari.BDLAND.RecyclerView.RecyclerViewAdapterHotelRooms;
import com.ghorbari.BDLAND.RecyclerView.RecyclerViewAdapterHotelRoomsMale;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;


public class Ghorbari_Page_main_frag extends Fragment {
    private onFragmentBtnSelected listener;

    //For Near-Location RecyclerView Items

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    // For Hotel RecyclerView Items

    private ArrayList<String> mHImages = new ArrayList<>();
    private ArrayList<String> mHNames = new ArrayList<>();
    private ArrayList<String> mHAddress = new ArrayList<>();
    private ArrayList<String> mHRent = new ArrayList<>();

    //For Hotel-Rooms Male recyclerView Items

    private ArrayList<String> mHMImage = new ArrayList<>();
    private ArrayList<String> mHMRoomInfo = new ArrayList<>();
    private ArrayList<String> mHMMonth = new ArrayList<>();
    private ArrayList<String> mHMRent = new ArrayList<>();

    /////////////////////////////////////////////////////////

    private RecyclerView recyclerView;
    private RecyclerView recyclerView_second;
    private RecyclerView recyclerView_third;

    //Recycler View class object

    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerViewAdapterHotelRooms recyclerViewAdapterHotelRooms;
    private RecyclerViewAdapterHotelRoomsMale recyclerViewAdapterHotelRoomsMale;

    private Button notification_btn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ghorbari__page_main_frag, container, false);

        getImages();

        notification_btn = view.findViewById(R.id.notification_btn);

        recyclerView = view.findViewById(R.id.recyclerView_ghorbari_main_page1);
        recyclerView_second = view.findViewById(R.id.recyclerView_ghorbari_main_page2);
        recyclerView_third = view.findViewById(R.id.recyclerView_ghorbari_main_page3);

        recyclerView.setHasFixedSize(true);
        recyclerView_second.setHasFixedSize(true);
        recyclerView_third.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_second.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView_third.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), mNames, mImageUrls);
        recyclerViewAdapterHotelRooms = new RecyclerViewAdapterHotelRooms(this.getContext(), mHImages, mHNames, mHAddress, mHRent);
        recyclerViewAdapterHotelRoomsMale = new RecyclerViewAdapterHotelRoomsMale(this.getContext(), mHMImage, mHMRoomInfo, mHMMonth, mHMRent);

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView_second.setAdapter(recyclerViewAdapterHotelRooms);
        recyclerView_third.setAdapter(recyclerViewAdapterHotelRoomsMale);

        notification_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onButtonSelected();
            }
        });

        return view;
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        //Near Location Items Data set 1
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Havasu");

        //Hotel Info data 1
        mHImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHNames.add("Havasu Falls");
        mHAddress.add("Nazipur Bus-stand");
        mHRent.add("TK 7,000/ Night");

        //Hotel Room Info Data 1
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Single Room");
        mHMMonth.add("From August");
        mHMRent.add("TK 7,000/ Month");

        //Near Location Items Data set 2
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Trondheim");

        //Hotel Info data set 2
        mHImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHNames.add("Trondheim");
        mHAddress.add("Nazipur Kacha-Bazar");
        mHRent.add("TK 9,000/ Night");

        //Hotel Room Info Data set 2
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Single Bed");
        mHMMonth.add("From March");
        mHMRent.add("TK 9,000/ Month");

        //Near Location Items Data set 3
        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        //Hotel Info data set 3
        mHImages.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mHNames.add("High School");
        mHAddress.add("Portugal");
        mHRent.add("TK 5,000/ Night");

        //Hotel Room Info Data set 3
        mHMImage.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mHMRoomInfo.add("Sublet Room");
        mHMMonth.add("From January");
        mHMRent.add("TK 9,000/ Month");

        //Near Location Items Data set 4
        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky");

        //Hotel Info data set 4
        mHImages.add("https://i.redd.it/j6myfqglup501.jpg");
        mHNames.add("PaharPur");
        mHAddress.add("Bangladesh");
        mHRent.add("TK 10,000/ Night");

        //Hotel Room Info Data set 4
        mHMImage.add("https://i.redd.it/j6myfqglup501.jpg");
        mHMRoomInfo.add("Three bed");
        mHMMonth.add("From February");
        mHMRent.add("TK 8,000/ Month");

        //Near Location Items Data set 5
        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        //Hotel Info data set 5
        mHImages.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHNames.add("Mahahual");
        mHAddress.add("Bangladesh");
        mHRent.add("TK 1,000/ Night");

        //Hotel Room Info Data set 5
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Two bed");
        mHMMonth.add("From March");
        mHMRent.add("TK 5,000/ Month");

        //Near Location Items Data
        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen");

        //Hotel Info data
        mHImages.add("https://i.redd.it/k98uzl68eh501.jpg");
        mHNames.add("Frozen Lake");
        mHAddress.add("Nazipur Attri-Lake");
        mHRent.add("TK 15,000/ Night");

        //Hotel Room Info Data set 5
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Two bed");
        mHMMonth.add("From March");
        mHMRent.add("TK 5,000/ Month");

        //Near Location Items Data
        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White");

        //Hotel Info data
        mHImages.add("https://i.redd.it/glin0nwndo501.jpg");
        mHNames.add("White Sands Desert");
        mHAddress.add("Nazipur Attrai-Beach");
        mHRent.add("TK 9,000/ Night");

        //Hotel Room Info Data set 5
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Two bed");
        mHMMonth.add("From March");
        mHMRent.add("TK 5,000/ Month");

        //Near Location Items Data
        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        //Hotel Info data
        mHImages.add("https://i.redd.it/obx4zydshg601.jpg");
        mHNames.add("Austrailia");
        mHAddress.add("Nazipur Bulbuler-dokan");
        mHRent.add("TK 19,000/ Night");

        //Hotel Room Info Data set 5
        mHMImage.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mHMRoomInfo.add("Two bed");
        mHMMonth.add("From March");
        mHMRent.add("TK 5,000/ Month");

        //Near Location Items Data
        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

        //Hotel Info data
        mHImages.add("https://i.imgur.com/ZcLLrkY.jpg");
        mHNames.add("Washington");
        mHAddress.add("Dhaka");
        mHRent.add("TK 90,000/ Night");

        //Hotel Room Info Data set 6
        mHMImage.add("https://i.imgur.com/ZcLLrkY.jpg");
        mHMRoomInfo.add("Whole Flat");
        mHMMonth.add("From December");
        mHMRent.add("TK 90,000/ Month");

        //initRecyclerView();

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