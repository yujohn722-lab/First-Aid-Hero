package com.example.myapplication;

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

        // Ensure everything is visible
        holder.title.setVisibility(View.VISIBLE);
        holder.text.setVisibility(View.VISIBLE);

        // CLICK ACTION REMOVED:
        // The cards are no longer clickable and will not link to anything.
        holder.card.setOnClickListener(null);
        holder.card.setClickable(false);
        holder.card.setFocusable(false);
    }

    @Override
    public int getItemCount() {
        // Keeps the infinite scroll working
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