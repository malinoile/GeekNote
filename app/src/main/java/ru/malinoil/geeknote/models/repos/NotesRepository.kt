package ru.malinoil.geeknote.models.repos

import ru.malinoil.geeknote.models.entities.NoteEntity

interface NotesRepository {
    fun getNotes(
        onSuccess: (List<NoteEntity>) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun deleteNote(note: NoteEntity)
    fun addNote(note: NoteEntity)
    fun updateNote(note: NoteEntity)
}