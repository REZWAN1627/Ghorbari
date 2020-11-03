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

public class RecyclerViewAdapterHotelRoomsMale extends RecyclerView.Adapter<RecyclerViewAdapterHotelRoomsMale.ViewHolder> {

    private ArrayList<String> mHMImage = new ArrayList<>();
    private ArrayList<String> mHMRoomInfo = new ArrayList<>();
    private ArrayList<String> mHMMonth = new ArrayList<>();
    private ArrayList<String> mHMRent = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterHotelRoomsMale(Context mContext, ArrayList<String> mHMImage, ArrayList<String> mHMRoomInfo,
                                             ArrayList<String> mHMMonth, ArrayList<String> mHMRent) {
        this.mHMImage = mHMImage;
        this.mHMRoomInfo = mHMRoomInfo;
        this.mHMMonth = mHMMonth;
        this.mHMRent = mHMRent;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_recyclerview_hotel_room_male, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mHMImage.get(position))
                .into(holder.imageView_HMImage);

        holder.textView_HMRoomInfo.setText(mHMRoomInfo.get(position));
        holder.textView_HMMonth.setText(mHMMonth.get(position));
        holder.textView_HMRent.setText(mHMRent.get(position));

        holder.imageView_HMImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "CLicked", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mHMImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView_HMImage;
        private TextView textView_HMRoomInfo;
        private TextView textView_HMMonth;
        private TextView textView_HMRent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_HMImage = itemView.findViewById(R.id.hotel_room_image);
            textView_HMRoomInfo = itemView.findViewById(R.id.hotel_room_info);
            textView_HMMonth = itemView.findViewById(R.id.hotel_room_available_month);
            textView_HMRent = itemView.findViewById(R.id.hotel_rent_per_month);
        }
    }
}
