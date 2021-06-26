package ru.malinoil.geeknote.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
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
import java.util.GregorianCalendar;

import ru.malinoil.geeknote.R;
import ru.malinoil.geeknote.models.NoteEntity;

public class NotebookFragment extends Fragment {
    private static final String NOTEBOOK_KEY = "notebook";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private NoteEntity noteEntity;

    private EditText editName;
    private EditText editDescription;
    private TextView textDeadline;
    private TextView textCreate;
    private Button btnSave;
    private Button btnDeadline;

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

        editName = (EditText) view.findViewById(R.id.edit_name);
        editDescription = (EditText) view.findViewById(R.id.edit_description);
        textCreate = (TextView) view.findViewById(R.id.text_date_create);
        textDeadline = (TextView) view.findViewById(R.id.text_deadline);
        btnSave = (Button) view.findViewById(R.id.button_save);
        if (noteEntity == null) {
            btnSave.setText(getContext().getString(R.string.create_text));
        } else {
            btnSave.setText(getContext().getString(R.string.save_text));
        }
        btnDeadline = (Button) view.findViewById(R.id.button_deadline);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFields(noteEntity);
        if (noteEntity == null) noteEntity = new NoteEntity("", "");

        btnSave.setOnClickListener(v -> {
            fillNote();
            getController().updateNote(noteEntity);
        });
        btnDeadline.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                DatePickerDialog dialog = new DatePickerDialog(getContext());
                dialog.setOnDateSetListener((view1, year, month, dayOfMonth) -> {
                    GregorianCalendar calendar = new GregorianCalendar(year, month, dayOfMonth);
                    noteEntity.setDeadline(calendar.getTime());
                    textDeadline.setText(getDeadlineString(noteEntity.getDeadline()));
                });
                dialog.show();
            } else {
                //todo Доделать после урока по диалогам
            }
        });

    }

    private void fillNote() {
        noteEntity.setName(editName.getText().toString());
        noteEntity.setDescription(editDescription.getText().toString());
        noteEntity.setCreateDate(new Date());
    }

    private void initializeFields(NoteEntity note) {
        if (note == null) return;

        editName.setText(note.getName());
        editDescription.setText(note.getDescription());
        textCreate.setText(String.format("%s %s",
                getResources().getString(R.string.tag_created),
                simpleDateFormat.format(note.getCreateDate())));
        textDeadline.setText(
                (note.getDeadline() != null)
                        ? getDeadlineString(note.getDeadline())
                        : getResources().getString(R.string.deadline_undefined)
        );
    }

    private String getDeadlineString(Date date) {
        return String.format("%s %s",
                getResources().getString(R.string.tag_deadline),
                simpleDateFormat.format(date));
    }

    public interface Controller {
        void updateNote(NoteEntity noteEntity);
    }

    private Controller getController() {
        return (Controller) getActivity();
    }

}

