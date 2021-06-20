package ru.malinoil.geeknote.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;

public class NotebookFragment extends Fragment {
    private static final String NOTEBOOK_KEY = "notebook";

    private NoteEntity noteEntity;
    private SimpleDateFormat simpleDateFormat;

    private TextView editName;
    private EditText editDescription;
    private TextView textDeadline;
    private TextView textCreate;
    private Button btnSave;

    public static NotebookFragment newInstance(NoteEntity noteEntity) {
        NotebookFragment fragment = new NotebookFragment();
        Bundle args = new Bundle();

        args.putParcelable(NOTEBOOK_KEY, noteEntity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity должен реализовывать интерфейс NotebookFragment.Controller");
        }
        if (getArguments() != null) {
            noteEntity = getArguments().getParcelable(NOTEBOOK_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, null);

        editName = (TextView) view.findViewById(R.id.text_name);
        editDescription = (EditText) view.findViewById(R.id.edit_description);
        textCreate = (TextView) view.findViewById(R.id.text_date_create);
        textDeadline = (TextView) view.findViewById(R.id.text_deadline);
        btnSave = (Button) view.findViewById(R.id.button_save);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        editName.setText(noteEntity.getName());
        editDescription.setText(noteEntity.getDescription());
        textCreate.setText(String.format("Created: %s",
                simpleDateFormat.format(noteEntity.getCreateDate())));
        textDeadline.setText(
                (noteEntity.getDeadline() != null)
                        ? getDeadlineString(noteEntity.getDeadline())
                        : "Дедлайн не указан"
        );

        btnSave.setOnClickListener(v -> {
            noteEntity.setName(editName.getText().toString());
            noteEntity.setDescription(editDescription.getText().toString());
            ((Controller) getActivity()).updateNote(noteEntity);
        });

    }

    private String getDeadlineString(Date date) {
        return String.format("Deadline to %s", simpleDateFormat.format(date));
    }

    public interface Controller {
        void updateNote(NoteEntity noteEntity);
    }

}

