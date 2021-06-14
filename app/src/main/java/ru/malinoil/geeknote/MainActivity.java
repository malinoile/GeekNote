package ru.malinoil.geeknote;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.malinoil.geeknote.fragments.NoteListFragment;
import ru.malinoil.geeknote.fragments.NotebookFragment;
import ru.malinoil.geeknote.models.NoteEntity;

public class MainActivity extends AppCompatActivity implements NoteListFragment.Controller,
        NotebookFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteEntity note = new NoteEntity("Первая заметка",
                "В первой заметке будет\n" +
                        "небольшой текст с переносами строк");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.single_fragment_container, new NoteListFragment())
                .commit();
    }


    @Override
    public void openNotebook(NoteEntity noteEntity) {
        boolean isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .add((isLand) ? R.id.details_container : R.id.single_fragment_container, NotebookFragment.newInstance(noteEntity))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void saveNote(NoteEntity noteEntity) {
        onBackPressed();
    }
}