package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class KitAdapter extends RecyclerView.Adapter<KitAdapter.ViewHolder> {

    private List<KitItem> list;

    public KitAdapter(List<KitItem> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView arrow;
        MaterialCardView kitCard;
        View iconPlaceholder;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            arrow = itemView.findViewById(R.id.arrow);
            kitCard = itemView.findViewById(R.id.kitCard);
            iconPlaceholder = itemView.findViewById(R.id.kitIconPlaceholder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_kit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        KitItem item = list.get(position);

        holder.title.setText(item.getTitle());
        holder.description.setText(item.getDescription());

        // Apply dynamic background color
        if (item.getBackgroundColor() != null) {
            holder.kitCard.setCardBackgroundColor(Color.parseColor(item.getBackgroundColor()));
        }

        boolean isExpanded = item.isExpanded();
        holder.description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrow.setRotation(isExpanded ? 180f : 0f);

        holder.kitCard.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                KitItem current = list.get(pos);
                current.setExpanded(!current.isExpanded());
                notifyItemChanged(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }
}