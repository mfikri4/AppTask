package com.example.apptask.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.apptask.R
import com.example.apptask.databinding.FragmentEditBinding
import com.example.apptask.model.Note
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class EditFragment : Fragment() {

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!
    private val args: EditFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pserial = args.edNote

        setNote(args.edNote)
        binding.btnDelete.setOnClickListener {
            FirebaseFirestore.getInstance().collection("NOTES").document(it.id.toString()).delete()
                .addOnSuccessListener {
                Toast.makeText(requireContext(), "Berhasil Hapus", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {Toast.makeText(requireContext(), "Gagal Hapus", Toast.LENGTH_SHORT).show()  }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                Navigation.findNavController(view).navigate(R.id.action_editFragment_to_listDataFragment)
            }
        }

        binding.btnEdit.setOnClickListener {
            val uang = binding.uangInput.text.toString()
            val tanggal = binding.dateInput.text.toString()
            val ket = binding.descInput.text.toString()
            editNote(uang, tanggal, ket)
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                Navigation.findNavController(view).navigate(R.id.action_editFragment_to_listDataFragment)
            }
        }

        binding.ivBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_editFragment_to_listDataFragment)
        }

    }

    private fun setNote(edNote: Note?) {
        edNote?.let {
            with(binding) {
                binding.uangInput.setText("${edNote.uang}")
                binding.dateInput.setText("${edNote.tanggal}")
                binding.descInput.setText("${edNote.ket}")
            }
        }
    }
    private fun deleteNote() {

    }

    private fun editNote(uang: String, tanggal: String, ket: String) {

        val note = Note(uang, tanggal, ket)

        FirebaseFirestore.getInstance().collection("NOTES")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Berhasil Tambah Data!, ID: " + documentReference.id)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Gagal", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "Gagal Tambah Data", e)
            }
    }
}