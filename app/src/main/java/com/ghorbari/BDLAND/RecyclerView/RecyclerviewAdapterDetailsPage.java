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

public class RecyclerviewAdapterDetailsPage extends RecyclerView.Adapter<RecyclerviewAdapterDetailsPage.ViewHolder> {

    private ArrayList<String> mDBImages = new ArrayList<>();
    private ArrayList<String> mDBNames = new ArrayList<>();
    private Context mContext;

    public RecyclerviewAdapterDetailsPage(Context mContext, ArrayList<String> mDBImages, ArrayList<String> mDBNames) {
        this.mContext = mContext;
        this.mDBImages = mDBImages;
        this.mDBNames = mDBNames;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem_recyclerview_details_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mDBImages.get(position))
                .into(holder.imageView_DBImage);
        holder.textView_DBName.setText(mDBNames.get(position));

    }

    @Override
    public int getItemCount() {
        return mDBNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_DBImage;
        private TextView textView_DBName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView_DBImage = itemView.findViewById(R.id.card_circle_image_facilities);
            textView_DBName = itemView.findViewById(R.id.card_circle_textView_facilities);
        }
    }
}
