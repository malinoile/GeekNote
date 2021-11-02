package ru.malinoil.geeknote.models.contracts

import ru.malinoil.geeknote.models.entities.NoteEntity

interface NoteContract {
    interface View {
        fun renderError(msg: String)
        fun saveNote(note: NoteEntity)
    }
    interface Presenter {
        fun attach(view: View)
        fun saveNote(note: NoteEntity)
        fun detach()
    }
}