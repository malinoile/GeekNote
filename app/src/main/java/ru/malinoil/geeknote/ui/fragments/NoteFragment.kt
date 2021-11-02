package ru.malinoil.geeknote.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.malinoil.geeknote.MyApplication
import ru.malinoil.geeknote.R
import ru.malinoil.geeknote.databinding.FragmentNoteBinding
import ru.malinoil.geeknote.models.contracts.NoteContract
import ru.malinoil.geeknote.models.controllers.NoteController
import ru.malinoil.geeknote.models.entities.NoteEntity
import ru.malinoil.geeknote.models.presenters.SaveNotePresenter

class NoteFragment : Fragment(), NoteContract.View {
    private var _binding: FragmentNoteBinding? = null
    private val binding: FragmentNoteBinding get() = _binding!!
    private var noteEntity: NoteEntity? = null
    private val app: MyApplication by lazy { requireActivity().application as MyApplication }
    private val presenter: NoteContract.Presenter by lazy { SaveNotePresenter(app.notesRepository) }

    companion object {
        private const val NOTEBOOK_KEY = "notebook"
        fun newInstance(noteEntity: NoteEntity?): NoteFragment {
            val fragment = NoteFragment()
            val args = Bundle()
            args.putParcelable(NOTEBOOK_KEY, noteEntity)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (activity !is NoteController) {
            throw RuntimeException("Activity must be implements NoteController")
        }
        arguments?.let {
            noteEntity = it.getParcelable(NOTEBOOK_KEY)
        }
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeFields()

        binding.buttonSave.setOnClickListener {
            updateOrGenerateNote()
            noteEntity?.let {
                presenter.saveNote(it)
            }
        }
    }

    private fun updateOrGenerateNote() {
        noteEntity?.let {
            it.name = binding.editName.text.toString()
            it.description = binding.editDescription.text.toString()
        } ?: apply {
            noteEntity = NoteEntity(
                NoteEntity.generateId(),
                binding.editName.text.toString(),
                binding.editDescription.text.toString(),
                System.currentTimeMillis()
            )
        }
    }

    private fun initializeFields() {
        noteEntity?.let {
            fillNoteFields(it)
        }
    }

    private fun fillNoteFields(note: NoteEntity) {
        with(binding) {
            editName.setText(note.name)
            editDescription.setText(note.description)
            textDateCreate.text = note.createDate.toString()
            buttonSave.text = requireContext().getString(
                noteEntity?.let { R.string.save_text } ?: R.string.create_text
            )
        }
    }

    override fun onDestroyView() {
        presenter.detach()
        _binding = null
        super.onDestroyView()
    }

    override fun renderError(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun saveNote(note: NoteEntity) {
        getController().closeNote(note)
    }

    private fun getController(): NoteController {
        return activity as NoteController
    }
}