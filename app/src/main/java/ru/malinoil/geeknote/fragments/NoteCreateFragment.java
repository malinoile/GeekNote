package ru.malinoil.geeknote.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;

public class NoteCreateFragment extends Fragment {

    private EditText editName;
    private EditText editDescription;
    private TextView textDeadline;
    private Button btnDeadline;
    private Button btnSave;

    private Date deadline;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (!(context instanceof NoteCreateFragment.Controller)) {
            throw new RuntimeException("Activity должен реализовывать интерфейс NoteCreateFragment.Controller");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_note, null);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);

        editName = (EditText) view.findViewById(R.id.edit_name);
        editDescription = (EditText) view.findViewById(R.id.edit_description);
        textDeadline = (TextView) view.findViewById(R.id.text_deadline);
        btnDeadline = (Button) view.findViewById(R.id.button_deadline);
        btnSave = (Button) view.findViewById(R.id.button_save);

        btnDeadline.setOnClickListener(v -> {
            deadline = new Date();
            textDeadline.setText(String.format("Deadline to %s", simpleDateFormat.format(deadline)));
        });

        btnSave.setOnClickListener(v -> {
            if (editName.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Поле с названием должно быть заполнено",
                        Toast.LENGTH_SHORT).show();
            } else {
                ((Controller) getActivity()).saveNewNote(getNote());
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private NoteEntity getNote() {
        NoteEntity note = new NoteEntity(editName.getText().toString(), editDescription.getText().toString());
        note.setDeadline(deadline);
        note.setCreateDate(new Date());
        return note;
    }

    public interface Controller {
        void saveNewNote(NoteEntity note);
    }
}
