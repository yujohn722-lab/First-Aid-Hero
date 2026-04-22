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
        CarouselItem item = items.get(position % items.size());
        holder.card.setCardBackgroundColor(Color.parseColor(item.getBackgroundColor()));
        holder.image.setImageResource(item.getImageResource());
        holder.title.setText(item.getTitle());
        holder.text.setText(item.getText());
    }

    @Override
    public int getItemCount() {
        // Return a large number to enable pseudo-infinite swiping if desired, 
        // or just items.size() for simple looping logic in MainActivity.
        // The user asked to go back to the first if swiping next from last.
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