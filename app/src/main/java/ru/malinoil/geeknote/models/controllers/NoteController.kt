package ru.malinoil.geeknote.models.controllers

import ru.malinoil.geeknote.models.entities.NoteEntity

interface NoteController {
    fun openNote(note: NoteEntity?)
    fun closeNote(note: NoteEntity?)
}