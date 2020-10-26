package com.example.notepad.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.example.notepad.MainActivityContract
import com.example.notepad.R
import com.example.notepad.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.layout_blank_note_fragment.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class BlankNoteFragment : Fragment() {

    private val presenter: MainActivityContract.Presenter = MainActivityPresenter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_blank_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            presenter.launchNotesListFragment(parentFragmentManager)
        }

        save_note_button.setOnClickListener {
            val title = title_text.text.toString()
            val description = description_text.text.toString()

            if (!title.isEmpty() || !description.isEmpty()) {
                presenter.saveNote(
                    requireContext(),
                    title_text.text.toString(),
                    description_text.text.toString()
                )
                presenter.launchNotesListFragment(parentFragmentManager)
            }
        }
    }
}