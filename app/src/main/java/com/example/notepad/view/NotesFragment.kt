package com.example.notepad.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notepad.MainActivityContract
import com.example.notepad.R
import com.example.notepad.model.NoteModel
import com.example.notepad.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.layout_note.*
import kotlinx.android.synthetic.main.layout_notes_fragment.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NotesFragment : Fragment(), MainActivityContract.View {

    private val presenter: MainActivityContract.Presenter = MainActivityPresenter()

    private var noteAdapter: NoteAdapter? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_notes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        presenter.setView(this)
        presenter.getNotesList(requireContext())

        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        add_note_button.setOnClickListener {
            presenter.launchBlankNoteFragment(requireActivity().supportFragmentManager)
        }

        delete_note_button.setOnClickListener { presenter.deleteNote() }
    }

    override fun updateNoteList(noteModel: NoteModel?) {
    }

    override fun updateNoteAdapter(notes: List<NoteModel>) {
        noteAdapter = NoteAdapter(requireContext(), notes)
        recycler_view?.adapter = noteAdapter
    }

    private fun initRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext().applicationContext, 2,
            LinearLayoutManager.VERTICAL, false)
        recycler_view?.layoutManager = gridLayoutManager
        recycler_view?.setHasFixedSize(true)
    }
}