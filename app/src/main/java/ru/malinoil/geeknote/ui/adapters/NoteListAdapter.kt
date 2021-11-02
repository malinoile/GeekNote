package ru.malinoil.geeknote.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.geeknote.R
import ru.malinoil.geeknote.databinding.ItemOnListBinding
import ru.malinoil.geeknote.models.entities.NoteEntity

class NoteListAdapter(private val onClickNote: (NoteEntity) -> Unit) :
    RecyclerView.Adapter<NoteListAdapter.NoteHolder?>() {
    private var listNotes: MutableList<NoteEntity> = emptyList<NoteEntity>().toMutableList()

    fun setListNotes(list: List<NoteEntity>) {
        listNotes = list.toMutableList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(parent, onClickNote)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    fun deleteNote(note: NoteEntity): Int {
        val index = listNotes.indexOf(note)
        listNotes.remove(note)
        return index
    }

    fun saveNote(note: NoteEntity) {
        val indexNote = listNotes.indexOf(note)
        if(indexNote >= 0) {
            listNotes[indexNote] = note
            notifyItemChanged(indexNote)
        } else {
            listNotes.add(note)
            notifyItemChanged(listNotes.size-1)
        }
    }

    inner class NoteHolder(
        parent: ViewGroup,
        onClickNote: (NoteEntity) -> Unit
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_on_list, parent, false)
    ) {
        private val binding: ItemOnListBinding = ItemOnListBinding.bind(itemView)
        private var note: NoteEntity? = null

        fun bind(note: NoteEntity) {
            this.note = note
            with(binding) {
                listTitleNote.text = note.name
                listDescNote.text = note.description
                listCreateNote.text = note.createDate.toString()
            }
        }

        fun getNote(): NoteEntity {
            return note!!
        }

        init {
            binding.root.setOnClickListener {
                note?.let { onClickNote(it) }
            }
        }
    }
}