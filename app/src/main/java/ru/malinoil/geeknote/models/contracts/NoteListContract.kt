package ru.malinoil.geeknote.models.contracts

import ru.malinoil.geeknote.models.entities.NoteEntity

interface NoteListContract {
    interface View {
        fun renderList(list: List<NoteEntity>)
        fun renderToast(msg: String)
    }
    interface Presenter {
        fun attach(view: View)
        fun getNoteList()
        fun deleteNote(note: NoteEntity)
        fun detach()
    }
}