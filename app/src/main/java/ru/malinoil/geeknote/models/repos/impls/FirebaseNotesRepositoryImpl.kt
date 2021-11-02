package ru.malinoil.geeknote.models.repos.impls

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ru.malinoil.geeknote.models.entities.NoteEntity
import ru.malinoil.geeknote.models.repos.NotesRepository

class FirebaseNotesRepositoryImpl : NotesRepository {
    private var notesList: MutableList<NoteEntity>? = null
    private val db = Firebase.firestore

    override fun getNotes(onSuccess: (List<NoteEntity>) -> Unit, onError: (Throwable) -> Unit) {
        notesList?.let { onSuccess(it) } ?: run {
            db.collection("notes")
                .get()
                .addOnSuccessListener {
                    val list: MutableList<NoteEntity> = emptyList<NoteEntity>().toMutableList()
                    for (document in it) {
                        list.add(document.toObject(NoteEntity::class.java))
                    }
                    notesList = list
                    onSuccess(list)
                }
                .addOnFailureListener {
                    onError(it)
                }
        }
    }

    override fun deleteNote(note: NoteEntity) {
        db.collection("notes").document(note.id).delete()
        notesList?.remove(note)
    }

    override fun addNote(note: NoteEntity) {
        db.collection("notes").document(note.id)
            .set(note);
        notesList?.add(note);
    }

    override fun updateNote(note: NoteEntity) {
        db.collection("notes").document(note.id).update(
            "name", note.name,
            "description", note.description,
            "deadline", note.deadline
        )
    }
}