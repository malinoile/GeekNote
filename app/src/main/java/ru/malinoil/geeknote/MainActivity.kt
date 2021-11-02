package ru.malinoil.geeknote

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.malinoil.geeknote.databinding.ActivityMainBinding
import ru.malinoil.geeknote.models.controllers.FragmentController
import ru.malinoil.geeknote.models.controllers.NoteController
import ru.malinoil.geeknote.ui.fragments.NoteListFragment
import ru.malinoil.geeknote.ui.fragments.NoteFragment
import ru.malinoil.geeknote.models.entities.NoteEntity

class MainActivity : AppCompatActivity(), NoteController, FragmentController {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val isLand: Boolean by lazy {
        resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    companion object {
        const val NOTE_LIST_FRAGMENT_TAG = "NOTE_LIST_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment(NoteListFragment(), NOTE_LIST_FRAGMENT_TAG)
        binding.addFab.setOnClickListener {
            openNote(null)
        }
    }

    override fun openNote(note: NoteEntity?) {
        binding.addFab.visibility = View.INVISIBLE
        if(!isLand) {
            setFragment(NoteFragment.newInstance(note), null, isBackStack = true)
        } else {
            setFragment(
                NoteFragment.newInstance(note),
                null, binding.detailsContainer!!.id
            )
        }
    }

    override fun closeNote(note: NoteEntity?) {
        binding.addFab.visibility = View.VISIBLE
        with(supportFragmentManager) {
            if(!isLand) { popBackStack() }
            else {
                findFragmentById(binding.detailsContainer!!.id)?.let { fragment ->
                    beginTransaction().remove(fragment).commit()
                }
            }
        }
        val noteListFragment = (searchFragmentByTag(NOTE_LIST_FRAGMENT_TAG) as NoteListFragment?)
        note?.let {
            noteListFragment?.saveNote(it)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        binding.addFab.visibility = View.VISIBLE
    }

    override fun setFragment(
        fragment: Fragment,
        tag: String?,
        containerId: Int,
        isBackStack: Boolean)
    {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(containerId, fragment, tag)
        if(isBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun searchFragmentByTag(tag: String?): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }
}