package edu.neu.madcourse.beatbeat_team22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LevelSeletorAdaptor extends RecyclerView.Adapter<LevelSeletorAdaptor.LevelSelectorViewHolder> {

    private ArrayList<LevelSelectorItem> mLevelSelectorItemList;
    private OnItemClickerListener mListener;

    public interface OnItemClickerListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickerListener listener) {
        mListener = listener;
    }

    public static class LevelSelectorViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public LevelSelectorViewHolder(@NonNull View itemView,final OnItemClickerListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener  != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public LevelSeletorAdaptor  (ArrayList<LevelSelectorItem> levelSelectorItemList) {
        mLevelSelectorItemList = levelSelectorItemList;
    }

    @NonNull
    @Override
    public LevelSelectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_selector_cardview, parent, false);
        LevelSelectorViewHolder levelSelectorViewHolder = new LevelSelectorViewHolder(v, mListener);
        return levelSelectorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LevelSelectorViewHolder holder, int position) {
        LevelSelectorItem currentItem = mLevelSelectorItemList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mLevelSelectorItemList.size();
    }
}
