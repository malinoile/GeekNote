package ru.malinoil.geeknote.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ru.malinoil.geeknote.NoteListAdapter;
import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;
import ru.malinoil.geeknote.models.NoteListImpl;
import ru.malinoil.geeknote.models.NotesListRepo;


public class NoteListFragment extends Fragment {

    private NotesListRepo noteRepo = new NoteListImpl();
    private RecyclerView recyclerListNotes;
    private NoteListAdapter listAdapter;
    private Button createBtn;

    private boolean isLand;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, null);
        recyclerListNotes = (RecyclerView) view.findViewById(R.id.recycler_list_notes);
        createBtn = (Button) view.findViewById(R.id.button_add_note);

        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter = new NoteListAdapter();
        listAdapter.setOnItemClickListener(new NoteListAdapter.OnMenuItemClickListener() {
            @Override
            public void onEditClick(NoteEntity note) {
                getController().openNote(note);
            }

            @Override
            public void onDeleteClick(NoteEntity note) {
                int position = getPositionFromListNotes(note);
                noteRepo.deleteNote(noteRepo.getNotes().get(position));
                listAdapter.notifyDataSetChanged();
            }
        });
        recyclerListNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerListNotes.setAdapter(listAdapter);
        listAdapter.setListNotes(noteRepo.getNotes());

        if (!isLand) {
            createBtn.setClickable(false);
            createBtn.setVisibility(View.GONE);
        }
        createBtn.setOnClickListener(v -> ((Controller) getActivity()).createNote());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity должен реализовывать NoteListFragment.Controller");
        }
    }

    public void addNoteOnList(NoteEntity note) {
        int position = getPositionFromListNotes(note);
        if (position != -1) {
            noteRepo.updateNote(position, note);
            if (!isLand) {
                return;
            }
        } else if (position == -1) {
            noteRepo.saveNote(note);
            position = noteRepo.getNotes().size() - 1;
        }
        listAdapter.notifyItemChanged(position);
    }

    private Controller getController() {
        return (Controller) getActivity();
    }

    public interface Controller {
        void openNote(NoteEntity noteEntity);

        void createNote();
    }

    private int getPositionFromListNotes(NoteEntity note) {
        for (int i = 0; i < noteRepo.getNotes().size(); i++) {
            if (noteRepo.getNotes().get(i).getId().equals(note.getId())) {
                return i;
            }
        }
        return -1;
    }
}
