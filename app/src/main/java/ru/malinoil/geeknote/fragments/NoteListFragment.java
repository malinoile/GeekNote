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

import java.util.ArrayList;
import java.util.List;

import ru.malinoil.geeknote.NoteListAdapter;
import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;


public class NoteListFragment extends Fragment {

    private List<NoteEntity> listNotes = new ArrayList<>();
    private RecyclerView recyclerListNotes;
    private NoteListAdapter listAdapter;
    private Button createBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, null);
        recyclerListNotes = (RecyclerView) view.findViewById(R.id.recycler_list_notes);
        createBtn = (Button) view.findViewById(R.id.button_add_note);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listAdapter = new NoteListAdapter();
        listAdapter.setOnItemClickListener(getController()::openNote);
        recyclerListNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerListNotes.setAdapter(listAdapter);
        listAdapter.setListNotes(listNotes);

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
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
        for (NoteEntity listNote : listNotes) {
            if (listNote.getId().equals(note.getId())) {
                return;
            }
        }
        listNotes.add(note);
        listAdapter.setListNotes(listNotes);
        listAdapter.notifyDataSetChanged();
    }

    private Controller getController() {
        return (Controller) getActivity();
    }

    public interface Controller {
        void openNote(NoteEntity noteEntity);

        void createNote();
    }
}
