package ru.malinoil.geeknote;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.malinoil.geeknote.models.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteHolder> {
    private List<NoteEntity> listNotes = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setListNotes(List<NoteEntity> list) {
        listNotes = list;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(parent, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public interface OnItemClickListener {
        void onItemClick(NoteEntity note);
    }
}
