package ru.malinoil.geeknote;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import ru.malinoil.geeknote.models.NoteEntity;

public class NoteHolder extends RecyclerView.ViewHolder {
    private NoteEntity note;

    private CardView cardView;
    private TextView titleText;
    private TextView descText;
    private TextView createText;

    public NoteHolder(@NonNull ViewGroup parent, NoteListAdapter.OnItemClickListener onItemClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_list, parent, false));
        cardView = (CardView) itemView;
        titleText = (TextView) itemView.findViewById(R.id.list_title_note);
        descText = (TextView) itemView.findViewById(R.id.list_desc_note);
        createText = (TextView) itemView.findViewById(R.id.list_create_note);

        cardView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(note);
            }
        });
    }

    public void bind(NoteEntity note) {
        this.note = note;
        titleText.setText(note.getName());
        descText.setText(note.getDescription());
    }
}
