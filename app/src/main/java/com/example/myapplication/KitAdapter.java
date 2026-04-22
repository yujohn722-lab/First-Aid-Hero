package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KitAdapter extends RecyclerView.Adapter<KitAdapter.ViewHolder> {

    private List<KitItem> list;

    public KitAdapter(List<KitItem> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description;
        ImageView arrow;
        LinearLayout header;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            arrow = itemView.findViewById(R.id.arrow);
            header = itemView.findViewById(R.id.header);
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

        boolean isExpanded = item.isExpanded();
        holder.description.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.arrow.setRotation(isExpanded ? 180f : 0f);

        holder.header.setOnClickListener(v -> {
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