package com.ghorbari.BDLAND.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ghorbari.BDLAND.R;

import java.util.ArrayList;

public class RecyclerViewAdapterHotelDetailsPage extends RecyclerView.Adapter<RecyclerViewAdapterHotelDetailsPage.ViewHolder> {

    private ArrayList<String> mHDBImages = new ArrayList<>();
    private ArrayList<String> mHDBNames = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapterHotelDetailsPage(Context mContext, ArrayList<String> mHDBImages, ArrayList<String> mHDBNames) {
        this.mHDBImages = mHDBImages;
        this.mHDBNames = mHDBNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_iistitem_recyclerview_hotel_details_button, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mHDBImages.get(position))
                .into(holder.imageView_HDBImage);
        holder.textView_HDBName.setText(mHDBNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mHDBNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_HDBImage;
        private TextView textView_HDBName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_HDBImage = itemView.findViewById(R.id.card_circle_image_hotel_facilities);
            textView_HDBName = itemView.findViewById(R.id.card_circle_textView_hotel_facilities);
        }
    }
}
