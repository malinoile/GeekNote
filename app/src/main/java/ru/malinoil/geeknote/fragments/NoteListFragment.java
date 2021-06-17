package ru.malinoil.geeknote.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;


public class NoteListFragment extends Fragment {

    private LinearLayout listNotes;
    private Button createBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listNotes = view.findViewById(R.id.list_notes);
        createBtn = (Button) view.findViewById(R.id.add_note_button);
        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            createBtn.setClickable(false);
            createBtn.setVisibility(View.GONE);
        }

        createBtn.setOnClickListener(v -> {
            ((Controller) getActivity()).openCreateNote();
        });

        addNoteOnViewport(new NoteEntity("Заметка с продуктами", "1. Квас\n2. Молоко\n3. Ирис"));
        addNoteOnViewport(new NoteEntity("Рабочая", "Посмотреть проблемы на 194.53.53.125"));
        addNoteOnViewport(new NoteEntity("Пустой список дел", ""));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity должен реализовывать интерфейс NoteListFragment.Controller");
        }
    }

    private void addNoteOnViewport(NoteEntity noteEntity) {
        Button button = new Button(getContext());
        button.setText(noteEntity.getName());
        button.setOnClickListener(v -> {
            ((Controller) getActivity()).openNotebook(noteEntity);
        });
        listNotes.addView(button);
    }

    public interface Controller {
        void openNotebook(NoteEntity noteEntity);

        void openCreateNote();
    }
}
