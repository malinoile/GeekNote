package ru.malinoil.geeknote

import android.app.Application
import ru.malinoil.geeknote.models.repos.NotesRepository
import ru.malinoil.geeknote.models.repos.impls.FirebaseNotesRepositoryImpl

class MyApplication: Application() {

    val notesRepository: NotesRepository by lazy {
        FirebaseNotesRepositoryImpl()
    }
}