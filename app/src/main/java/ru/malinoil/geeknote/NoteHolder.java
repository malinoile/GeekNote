package ru.malinoil.geeknote;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

import ru.malinoil.geeknote.models.NoteEntity;

public class NoteHolder extends RecyclerView.ViewHolder {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private NoteEntity note;

    private CardView cardView;
    private TextView titleText;
    private TextView descText;
    private TextView createText;

    public NoteHolder(@NonNull ViewGroup parent, NoteListAdapter.OnMenuItemClickListener onMenuItemClickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_on_list, parent, false));
        cardView = (CardView) itemView;
        titleText = (TextView) itemView.findViewById(R.id.list_title_note);
        descText = (TextView) itemView.findViewById(R.id.list_desc_note);
        createText = (TextView) itemView.findViewById(R.id.list_create_note);

        cardView.setOnLongClickListener(v -> {
            PopupMenu popup = new PopupMenu(itemView.getContext(), itemView);
            popup.inflate(R.menu.menu_note_item);
            popup.setOnMenuItemClickListener(item -> {
                setListener(item, onMenuItemClickListener);
                return true;
            });
            popup.show();
            return true;
        });
    }

    public void bind(NoteEntity note) {
        this.note = note;
        titleText.setText(note.getName());
        descText.setText(note.getDescription());
        createText.setText(dateFormat.format(note.getCreateDate()));
    }

    private void setListener(MenuItem item, NoteListAdapter.OnMenuItemClickListener clickListener) {
        if (clickListener == null) {
            return;
        }
        switch (item.getItemId()) {
            case R.id.edit_note:
                clickListener.onEditClick(note);
                break;
            case R.id.delete_note:
                clickListener.onDeleteClick(note);
                break;
        }
    }
}
