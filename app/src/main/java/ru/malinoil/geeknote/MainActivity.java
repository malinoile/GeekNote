package ru.malinoil.geeknote;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.malinoil.geeknote.fragments.NoteListFragment;
import ru.malinoil.geeknote.fragments.NotebookFragment;
import ru.malinoil.geeknote.models.NoteEntity;

public class MainActivity extends AppCompatActivity implements NoteListFragment.Controller,
        NotebookFragment.Controller {

    public static final String NOTE_LIST_FRAGMENT_TAG = "NOTE_TAG";

    private BottomNavigationView navigationView;
    private boolean isLand = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.single_fragment_container, new NoteListFragment(), NOTE_LIST_FRAGMENT_TAG)
                .commit();

        isLand = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        if (!isLand) {
            renderBottomNavigation();
        }

    }

    @Override
    public void updateNote(NoteEntity noteEntity) {
        if (!isLand) {
            getSupportFragmentManager().popBackStack();
        } else {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.details_container);
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .remove(fragment).commit();
            }
        }
        NoteListFragment noteListFragment = (NoteListFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTE_LIST_FRAGMENT_TAG);
        if (noteListFragment != null) {
            noteListFragment.addNoteOnList(noteEntity);
        }
    }

    @Override
    public void openNote(NoteEntity noteEntity) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isLand) {
            transaction.addToBackStack(null)
                    .replace(R.id.single_fragment_container, NotebookFragment.newInstance(noteEntity));
        } else {
            transaction.add(R.id.details_container, NotebookFragment.newInstance(noteEntity));
        }
        transaction.commit();
    }

    @Override
    public void createNote() {
        openNote(null);
    }

    private void renderBottomNavigation() {
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.nav_list:
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag(NOTE_LIST_FRAGMENT_TAG);
                    fragmentTransaction.replace(R.id.single_fragment_container,
                            (fragment == null) ? new NoteListFragment() : fragment);
                    break;
                case R.id.nav_add_note:
                    fragmentTransaction.addToBackStack(null)
                            .replace(R.id.single_fragment_container,
                                    NotebookFragment.newInstance(null));
                    break;
            }
            fragmentTransaction.commit();
            return true;
        });
    }
}