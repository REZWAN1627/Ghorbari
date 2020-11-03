package com.ghorbari.BDLAND.Fragment_UI;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ghorbari.BDLAND.R;


public class Booking_page_farg extends Fragment {

    private onFragmentBtnSelected listener;

    private Button save_info_customer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_booking_page_farg, container, false);
        save_info_customer = view.findViewById(R.id.save_info_customer);
        save_info_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onbuttonSelected();
            }
        });
        return view;
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onFragmentBtnSelected) {
            listener = (onFragmentBtnSelected) context;
        } else {
            throw new ClassCastException(context.toString() + "must implement listener");

        }
    }

    public interface onFragmentBtnSelected {

        public void onbuttonSelected();

    }
}