package com.ghorbari.BDLAND.RecyclerView;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ghorbari.BDLAND.R;


import java.util.ArrayList;

public class RecyclerViewAdapterHotelRooms extends RecyclerView.Adapter<RecyclerViewAdapterHotelRooms.ViewHolder> {

    private ArrayList<String> mHImages = new ArrayList<>();
    private ArrayList<String> mHNames = new ArrayList<>();
    private ArrayList<String> mHAddress = new ArrayList<>();
    private ArrayList<String> mHRent = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterHotelRooms(Context mContext, ArrayList<String> mHImages, ArrayList<String> mHNames, ArrayList<String> mHAddress,
                                         ArrayList<String> mHRent) {
        this.mHImages = mHImages;
        this.mHNames = mHNames;
        this.mHAddress = mHAddress;
        this.mHRent = mHRent;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_recyclerview_hotel_rooms, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mHImages.get(position))
                .into(holder.imageView_HImage);

        holder.textView_HName.setText(mHNames.get(position));
        holder.textView_HAddress.setText(mHAddress.get(position));
        holder.textView_HRent.setText(mHRent.get(position));

        holder.imageView_HImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "CLicked", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_HImage;
        private TextView textView_HName;
        private TextView textView_HAddress;
        private TextView textView_HRent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_HImage = itemView.findViewById(R.id.hotel_image);
            textView_HName = itemView.findViewById(R.id.hotel_name);
            textView_HAddress = itemView.findViewById(R.id.hotel_address);
            textView_HRent = itemView.findViewById(R.id.hotel_rent);
        }
    }
}
