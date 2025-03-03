package com.example.rtd_android_xml_lab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rtd_android_xml_lab.data.entity.Note
import com.example.rtd_android_xml_lab.viewmodel.NoteViewModel

class AddEditNoteFragment : Fragment() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var dropdownPriority: AutoCompleteTextView

    private val args by navArgs<AddEditNoteFragmentArgs>()
    private var editMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_edit_note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextTitle = view.findViewById(R.id.edit_text_title)
        editTextDescription = view.findViewById(R.id.edit_text_description)
        dropdownPriority = view.findViewById(R.id.dropdown_priority)

        val priorities = (1..10).map { it.toString() }
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, priorities)

        dropdownPriority.setAdapter(adapter)

        dropdownPriority.setText("1", false)

        setHasOptionsMenu(true)

        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        if (args.noteId != -1) {

            editMode = true
            editTextTitle.setText(args.noteTitle)

            noteViewModel.getNoteById(args.noteId).observe(viewLifecycleOwner) { note ->
                note?.let {
                    editTextDescription.setText(it.description)
                    dropdownPriority.setText(it.priority.toString(), false)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.save_note -> {
                saveNote()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveNote() {

        val title = editTextTitle.text.toString()
        val description = editTextDescription.text.toString()
        val priorityText = dropdownPriority.text.toString()

        if (priorityText.isEmpty() || priorityText.toIntOrNull() == null) {
            Toast.makeText(
                requireContext(),
                "Please select a valid priority",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val priority = priorityText.toInt()

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please insert a title and description",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (editMode) {
            val updatedNote = Note(title, description, priority)
            updatedNote.id = args.noteId
            noteViewModel.update(updatedNote)
            Toast.makeText(requireContext(), "Note updated", Toast.LENGTH_SHORT).show()
        } else {
            val note = Note(title, description, priority)
            noteViewModel.insert(note)
            Toast.makeText(requireContext(), "Note saved", Toast.LENGTH_SHORT).show()
        }

        findNavController().navigateUp()
    }
}