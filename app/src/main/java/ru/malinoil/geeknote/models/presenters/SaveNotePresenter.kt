package ru.malinoil.geeknote.models.presenters

import ru.malinoil.geeknote.models.contracts.NoteContract
import ru.malinoil.geeknote.models.entities.NoteEntity
import ru.malinoil.geeknote.models.repos.NotesRepository

class SaveNotePresenter(private val notesRepo: NotesRepository) : NoteContract.Presenter {
    private var view: NoteContract.View? = null

    override fun attach(view: NoteContract.View) {
        this.view = view
    }

    override fun saveNote(note: NoteEntity) {
        notesRepo.getNotes({
            val noteIndex = it.indexOf(note)
            if (noteIndex >= 0) {
                notesRepo.updateNote(note)
            } else {
                notesRepo.addNote(note)
            }
            view?.saveNote(note)
        }, {
            view?.renderError("Error")
        })
    }

    override fun detach() {
        view = null
    }
}