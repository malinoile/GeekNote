package ru.malinoil.geeknote.models.controllers

import androidx.fragment.app.Fragment
import ru.malinoil.geeknote.R

interface FragmentController {
    fun setFragment(
        fragment: Fragment,
        tag: String?,
        containerId: Int = R.id.single_fragment_container,
        isBackStack: Boolean = false)
    fun searchFragmentByTag(tag: String?): Fragment?
}