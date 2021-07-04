package ru.malinoil.geeknote;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.malinoil.geeknote.models.NoteEntity;

public class NoteListAdapter extends RecyclerView.Adapter<NoteHolder> {
    private List<NoteEntity> listNotes = new ArrayList<>();
    private OnMenuItemClickListener onMenuItemClickListener;

    public void setListNotes(List<NoteEntity> list) {
        listNotes = list;
    }

    public void setOnItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(parent, onMenuItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        holder.bind(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public interface OnMenuItemClickListener {
        void onEditClick(NoteEntity note);

        void onDeleteClick(NoteEntity note);
    }
}
