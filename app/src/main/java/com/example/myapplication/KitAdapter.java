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

public class KitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<KitItem> list;

    public KitAdapter(List<KitItem> list) {
        this.list = list;
    }

    public void updateItems(List<KitItem> filteredList) {
        this.list = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_kit_header, parent, false);
            return new HeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_kit, parent, false);
            return new ItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            // Subtract 1 from position because of the header at index 0
            KitItem item = list.get(position - 1);
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.title.setText(item.getTitle());
            itemHolder.description.setText(item.getDescription());

            // Apply dynamic background color
            if (item.getBackgroundColor() != null) {
                itemHolder.kitCard.setCardBackgroundColor(Color.parseColor(item.getBackgroundColor()));
            }

            boolean isExpanded = item.isExpanded();
            itemHolder.description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            itemHolder.arrow.setRotation(isExpanded ? 180f : 0f);

            itemHolder.kitCard.setOnClickListener(v -> {
                int currentPos = itemHolder.getAdapterPosition();
                if (currentPos != RecyclerView.NO_POSITION) {
                    KitItem current = list.get(currentPos - 1);
                    current.setExpanded(!current.isExpanded());
                    notifyItemChanged(currentPos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        // Add 1 for the header
        return (list != null ? list.size() : 0) + 1;
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView arrow;
        MaterialCardView kitCard;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            arrow = itemView.findViewById(R.id.arrow);
            kitCard = itemView.findViewById(R.id.kitCard);
        }
    }
}
