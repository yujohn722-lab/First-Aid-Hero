package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {

    private List<CarouselItem> items;

    public CarouselAdapter(List<CarouselItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hero_carousel_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int realPosition = position % items.size();
        CarouselItem item = items.get(realPosition);
        
        holder.card.setCardBackgroundColor(Color.parseColor(item.getBackgroundColor()));
        holder.image.setImageResource(item.getImageResource());
        holder.title.setText(item.getTitle());
        holder.text.setText(item.getText());

        // Ensure everything is visible for all slides
        holder.title.setVisibility(View.VISIBLE);
        holder.text.setVisibility(View.VISIBLE);

        // Handle specific navigation based on the item
        holder.card.setOnClickListener(v -> {
            Intent intent;
            if (realPosition == 1) {
                // 2nd Item -> Guide
                intent = new Intent(v.getContext(), GuideActivity.class);
            } else if (realPosition == 2) {
                // 3rd Item -> Kit
                intent = new Intent(v.getContext(), KitActivity.class);
            } else {
                // 1st Item (or others) -> Original Detail behavior
                intent = new Intent(v.getContext(), GuideDetailActivity.class);
                intent.putExtra(GuideDetailActivity.EXTRA_TITLE, item.getTitle());
                intent.putExtra(GuideDetailActivity.EXTRA_TEXT, item.getText());
                intent.putExtra(GuideDetailActivity.EXTRA_IMAGE, item.getImageResource());
            }
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public int getRealCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView card;
        ImageView image;
        TextView title, text;

        ViewHolder(View view) {
            super(view);
            card = view.findViewById(R.id.carouselCard);
            image = view.findViewById(R.id.carouselImage);
            title = view.findViewById(R.id.carouselTitle);
            text = view.findViewById(R.id.carouselText);
        }
    }
}