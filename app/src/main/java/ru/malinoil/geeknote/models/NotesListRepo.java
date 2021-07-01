package ru.malinoil.geeknote.models;

import java.util.List;

public interface NotesListRepo {
    void saveNote(NoteEntity note);

    void updateNote(int position, NoteEntity note);

    void deleteNote(NoteEntity note);

    List<NoteEntity> getNotes();
}
