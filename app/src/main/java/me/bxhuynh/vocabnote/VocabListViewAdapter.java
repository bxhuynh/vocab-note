package me.bxhuynh.vocabnote;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VocabListViewAdapter extends RecyclerView.Adapter<VocabListViewAdapter.ViewHolder> {
    private ArrayList<WordModal> wordModalArrayList;
    private Context context;
    private  int position;
    private DBHandler dbHandler;

    public int getPosition( ) {
            return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public VocabListViewAdapter(ArrayList<WordModal> wordModalArrayList, Context context) {
        this.wordModalArrayList = wordModalArrayList;
        this.context = context;
    }

    public void deleteItem() {
        dbHandler =  new DBHandler(context);
        dbHandler.deleteWord(wordModalArrayList.get(position).getWord());
        wordModalArrayList = dbHandler.readWords(0);
        notifyItemRemoved(position);
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
    }

    public void addToStudy() {
        dbHandler = new DBHandler(context);
        WordModal word = wordModalArrayList.get(position);
        if (word.getIsStudying() == 1) {
            Toast.makeText(context, "This word is already in study list.", Toast.LENGTH_SHORT).show();
            return;
        }
        dbHandler.updateWord(word.getWord(), word.getWord(), word.getSoundlike(), word.getMeaning(), 1);
        wordModalArrayList.set(position, new WordModal(word.getWord(), word.getSoundlike(), word.getMeaning(), 1));
        notifyItemChanged(position);
        Toast.makeText(context, word.getWord() + " is added to study", Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public VocabListViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocab_item, parent, false);

        return new VocabListViewAdapter.ViewHolder(view);
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
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
            holder.itemView.setBackgroundColor(context.getColor(R.color.white));
        } else {
            holder.imgStudying.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundColor(context.getColor(R.color.tertiary));
        }

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setPosition(holder.getLayoutPosition());
                holder.itemView.setBackgroundColor(context.getColor(R.color.tertiary_dark));
                return false;
            }

        });
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

            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.add(Menu.NONE, R.id.ctx_add,
                            Menu.NONE, R.string.ctx_Add);
                    contextMenu.add(Menu.NONE, R.id.ctx_edit, Menu.NONE, R.string.ctx_Edit);
                    contextMenu.add(Menu.NONE, R.id.ctx_delete,
                            Menu.NONE, R.string.ctx_Delete);

                }
            });
        }

    }
}
