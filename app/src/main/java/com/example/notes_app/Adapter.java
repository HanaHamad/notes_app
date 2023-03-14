package com.example.notes_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Note> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener2 itemClickListener2;

    public Adapter(Context context, List<Note> data ,ItemClickListener onClick, ItemClickListener2 onClick2) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.card_note, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder,int position) {
        holder.title.setText(mData.get(position).getTitle());
        holder.content.setText(mData.get(position).getContent());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(holder.getAdapterPosition(), mData.get(position).id);

            }
        });
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener2.onItemClick2(holder.getAdapterPosition(), mData.get(position).id);

            }
        });
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        TextView content;
        public ImageView delete;
        public ImageView update;
        public CardView card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            this.delete = itemView.findViewById(R.id.delete);
            this.update = itemView.findViewById(R.id.update);
            this.card = itemView.findViewById(R.id.card2);
            itemView.setOnClickListener(this);

        }
        Note getItem(int id) {
            return mData.get(id);
        }

        @Override
        public void onClick(View v) {

        }
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position, String id);
    }

    public interface ItemClickListener2{
        void onItemClick2(int position, String id);
    }
}