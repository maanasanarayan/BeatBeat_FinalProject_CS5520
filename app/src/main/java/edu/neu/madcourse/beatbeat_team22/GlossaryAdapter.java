package edu.neu.madcourse.beatbeat_team22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.LessonViewHolder> {
    private List<LessonItem> lessonItems;
    private OnItemClickerListener mListener;

    public interface OnItemClickerListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(GlossaryAdapter.OnItemClickerListener listener) {
        mListener = listener;
    }

    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public ImageView mImageView;
        public TextView mTextView2;

        public LessonViewHolder(@NonNull View itemView, final GlossaryAdapter.OnItemClickerListener listener) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.titleTextView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView2 = itemView.findViewById(R.id.subtitleTextView);

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

    public GlossaryAdapter(List<LessonItem> levelSelectorItemList) {
        lessonItems = levelSelectorItemList;
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_cardview, parent, false);
        LessonViewHolder lessonViewHolder = new GlossaryAdapter.LessonViewHolder(v, mListener);
        return lessonViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GlossaryAdapter.LessonViewHolder holder, int position) {
        LessonItem currentItem = lessonItems.get(position);
        holder.mImageView.setImageResource(currentItem.getmImage());
        holder.mTextView1.setText(currentItem.getmTitle());
        holder.mTextView2.setText(currentItem.getmBody());
    }

    @Override
    public int getItemCount() {
        return lessonItems.size();
    }
}
