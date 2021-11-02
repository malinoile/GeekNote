package ru.malinoil.geeknote.ui.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.malinoil.geeknote.MyApplication
import ru.malinoil.geeknote.databinding.FragmentListNotesBinding
import ru.malinoil.geeknote.models.contracts.NoteListContract
import ru.malinoil.geeknote.models.controllers.NoteController
import ru.malinoil.geeknote.models.entities.NoteEntity
import ru.malinoil.geeknote.models.presenters.NoteListPresenter
import ru.malinoil.geeknote.ui.adapters.NoteListAdapter

class NoteListFragment : Fragment(), NoteListContract.View {
    private var _binding: FragmentListNotesBinding? = null
    private val binding: FragmentListNotesBinding get() = _binding!!
    private val app by lazy { activity?.application as MyApplication }
    private val presenter: NoteListContract.Presenter by lazy { NoteListPresenter(app.notesRepository) }
    private val adapter = NoteListAdapter { (activity as NoteController).openNote(it) }
    private val isLand by lazy {
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attach(this)
        if (activity !is NoteController) {
            throw RuntimeException("Activity must be implements NoteController")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerListNotes.layoutManager = LinearLayoutManager(context)
        binding.recyclerListNotes.adapter = adapter

        presenter.getNoteList()

        if (!isLand) {
            binding.buttonAddNote.apply {
                isClickable = false
                visibility = View.GONE
                setOnClickListener { (activity as NoteController).openNote(null) }
            }
        }

        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = (viewHolder as NoteListAdapter.NoteHolder).getNote()
                presenter.deleteNote(note)
                adapter.notifyItemChanged(adapter.deleteNote(note))
            }

        })
        touchHelper.attachToRecyclerView(binding.recyclerListNotes)
    }

    override fun renderList(list: List<NoteEntity>) {
        adapter.setListNotes(list)
        adapter.notifyDataSetChanged()
    }

    override fun renderToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    fun saveNote(note: NoteEntity) {
        adapter.saveNote(note)
    }

    override fun onDestroyView() {
        _binding = null
        presenter.detach()
        super.onDestroyView()
    }
}