package ru.malinoil.geeknote;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.malinoil.geeknote.fragments.NoteCreateFragment;
import ru.malinoil.geeknote.fragments.NoteListFragment;
import ru.malinoil.geeknote.fragments.NotebookFragment;
import ru.malinoil.geeknote.models.NoteEntity;

public class MainActivity extends AppCompatActivity implements NoteListFragment.Controller,
        NotebookFragment.Controller, NoteCreateFragment.Controller {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteEntity note = new NoteEntity("Первая заметка",
                "В первой заметке будет\n" +
                        "небольшой текст с переносами строк");

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.single_fragment_container, new NoteListFragment())
                .commit();

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

            BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
            navigationView.setOnNavigationItemSelectedListener(item -> {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.nav_list:
                        fragmentTransaction.replace(R.id.single_fragment_container,
                                new NoteListFragment());
                        break;
                    case R.id.nav_add_note:
                        fragmentTransaction.replace(R.id.single_fragment_container,
                                new NoteCreateFragment());
                        break;
                }
                fragmentTransaction.commit();
                return true;
            });

        }
    }


    @Override
    public void openNotebook(NoteEntity noteEntity) {
        boolean isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        getSupportFragmentManager()
                .beginTransaction()
                .replace((isLand) ? R.id.details_container : R.id.single_fragment_container, NotebookFragment.newInstance(noteEntity))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void openCreateNote() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details_container, new NoteCreateFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void updateNote(NoteEntity noteEntity) {
        boolean isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isLand) {
            transaction.remove(getSupportFragmentManager().findFragmentById(R.id.details_container));
        } else {
            transaction.replace(R.id.single_fragment_container, new NoteListFragment());
        }
        transaction.commit();
    }

    @Override
    public void saveNewNote(NoteEntity note) {

    }
}