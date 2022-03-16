package me.bxhuynh.vocabnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudyingListViewAdapter extends RecyclerView.Adapter<StudyingListViewAdapter.ViewHolder> {
    private ArrayList<WordModal> wordModalArrayList;
    private Context context;
    private DBHandler dbHandler;

    public StudyingListViewAdapter(ArrayList<WordModal> wordModalArrayList, Context context) {
        this.wordModalArrayList = wordModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.studying_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        WordModal modal = wordModalArrayList.get(position);
        holder.tvWord.setText(modal.getWord());
        holder.tvSoundLike.setText(modal.getSoundlike());
        holder.tvMeaning.setText(modal.getMeaning());
    }

    @Override
    public int getItemCount() {
        // returning the size of our array list
        return wordModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView tvWord, tvSoundLike, tvMeaning;
        private Button btnCheck;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            tvWord = itemView.findViewById(R.id.word);
            tvSoundLike = itemView.findViewById(R.id.soundLike);
            tvMeaning = itemView.findViewById(R.id.meaning);
            dbHandler = new DBHandler(context.getApplicationContext());
            itemView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.updateWord(tvWord.getText().toString(), tvWord.getText().toString(), tvSoundLike.getText().toString(), tvMeaning.getText().toString(), 0);
                Toast.makeText(context.getApplicationContext(), "Remove from studying", Toast.LENGTH_SHORT).show();
                wordModalArrayList = dbHandler.readWords(1);
                notifyDataSetChanged();
            }
        });
        }
    }
}
