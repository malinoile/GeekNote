package ru.malinoil.geeknote

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setFragment(NoteListFragment(), NOTE_LIST_FRAGMENT_TAG)
        if (!isLand) {
            renderBottomNavigation()
        }
    }

    override fun openNote(noteEntity: NoteEntity?) {
        if(!isLand) {
            setFragment(NoteFragment.newInstance(noteEntity), null, isBackStack = true)
        } else {
            setFragment(
                NoteFragment.newInstance(noteEntity),
                null,
                binding.detailsContainer!!.id
            )
        }
    }

    override fun closeNote(note: NoteEntity?) {
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

    private fun renderBottomNavigation() {
        binding.bottomNavigation?.setOnNavigationItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                R.id.nav_list -> {
                    setFragment(
                        searchFragmentByTag(NOTE_LIST_FRAGMENT_TAG) ?: NoteListFragment(),
                        NOTE_LIST_FRAGMENT_TAG
                    )
                }
                R.id.nav_add_note -> {
                    setFragment(
                        NoteFragment.newInstance(null),
                        null,
                        isBackStack = true
                    )
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
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

    companion object {
        const val NOTE_LIST_FRAGMENT_TAG = "NOTE_TAG"
    }
}