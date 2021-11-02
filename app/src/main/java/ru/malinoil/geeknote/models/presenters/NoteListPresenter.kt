package ru.malinoil.geeknote.models.presenters

import android.util.Log
import ru.malinoil.geeknote.models.contracts.NoteListContract
import ru.malinoil.geeknote.models.entities.NoteEntity
import ru.malinoil.geeknote.models.repos.NotesRepository

class NoteListPresenter(private val repository: NotesRepository) : NoteListContract.Presenter {
    private var view: NoteListContract.View? = null

    override fun attach(view: NoteListContract.View) {
        this.view = view
    }

    override fun getNoteList() {
        repository.getNotes({
            view?.renderList(it)
        }, {
            view?.renderToast(it.message.toString())
        })
    }

    override fun deleteNote(note: NoteEntity) {
        Log.d("@@@", view.toString())
        repository.deleteNote(note)
        view?.renderToast("Delete note \"${note.name}\"")
    }

    override fun detach() {
        view = null
    }
}