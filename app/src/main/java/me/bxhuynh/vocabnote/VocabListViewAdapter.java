package me.bxhuynh.vocabnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VocabListViewAdapter extends RecyclerView.Adapter<VocabListViewAdapter.ViewHolder> {
    private ArrayList<WordModal> wordModalArrayList;
    private Context context;

    public VocabListViewAdapter(ArrayList<WordModal> wordModalArrayList, Context context) {
        this.wordModalArrayList = wordModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public VocabListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_item, parent, false);

        return new VocabListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VocabListViewAdapter.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        WordModal modal = wordModalArrayList.get(position);
        holder.tvWord.setText(modal.getWord());
        holder.tvSoundLike.setText(modal.getSoundlike());
        holder.tvMeaning.setText(modal.getMeaning());
        if (modal.getIsStudying() == 0) {
            holder.imgStudying.setVisibility(View.INVISIBLE);
        } else {
            holder.imgStudying.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return wordModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tvWord, tvSoundLike, tvMeaning;
        private ImageView imgStudying;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tvWord = itemView.findViewById(R.id.word);
            tvSoundLike = itemView.findViewById(R.id.soundLike);
            tvMeaning = itemView.findViewById(R.id.meaning);
            imgStudying = itemView.findViewById(R.id.imageStudying);
        }
    }
}
