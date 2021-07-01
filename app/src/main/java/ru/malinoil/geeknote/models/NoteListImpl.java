package ru.malinoil.geeknote.models;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NoteListImpl implements NotesListRepo {
    private static final String NOTES_LIST_TABLE = "notes";
    FirebaseFirestore store;
    private List<NoteEntity> notes = new ArrayList<>();

    public NoteListImpl() {
        store = FirebaseFirestore.getInstance();
        store.collection(NOTES_LIST_TABLE).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                notes.add(documentSnapshot.toObject(NoteEntity.class));
            }
        });
    }

    @Override
    public void saveNote(NoteEntity note) {
        store.collection(NOTES_LIST_TABLE).document(note.getId())
                .set(note);
        notes.add(note);
    }

    @Override
    public void updateNote(int position, NoteEntity note) {
        DocumentReference noteRef = store.collection(NOTES_LIST_TABLE)
                .document(note.getId());

        notes.get(position).setName(note.getName());
        notes.get(position).setDescription(note.getDescription());
        notes.get(position).setDeadline(note.getDeadline());

        noteRef.update(
                "name", note.getName(),
                "description", note.getDescription(),
                "deadline", note.getDeadline()
        );
    }

    @Override
    public void deleteNote(NoteEntity note) {
        notes.remove(note);
        DocumentReference noteRef = store.collection(NOTES_LIST_TABLE)
                .document(note.getId());
        noteRef.delete();
    }

    @Override
    public List<NoteEntity> getNotes() {
        Collections.sort(notes, (o1, o2) -> Long.compare(o1.getCreateDate(), o2.getCreateDate()));
        return notes;
    }

}
