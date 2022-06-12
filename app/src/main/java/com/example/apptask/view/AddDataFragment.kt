package com.example.apptask.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.apptask.databinding.FragmentAddDataBinding
import com.example.apptask.model.Note
import com.google.firebase.firestore.FirebaseFirestore

class AddDataFragment : Fragment() {

    private lateinit var binding: FragmentAddDataBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddDataBinding.inflate(layoutInflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
            val uang = binding.uangInput.text.toString()
            val tanggal = binding.dateInput.text.toString()
            val ket = binding.descInput.text.toString()
            addNote(uang, tanggal, ket)
        }

    }

    /*private fun saveData() {
        val uang = binding.uangInput.text.toString()
        val date = binding.dateInput.text.toString()
        val desc = binding.descInput.text.toString()

        val note = Note(id,uang.toInt(),date,desc)
        val noteId = ref.push().key.toString()

        ref.child(noteId).setValue(note).addOnCompleteListener {
            Toast.makeText(requireActivity(), "Successs",Toast.LENGTH_SHORT).show()
            binding.uangInput.setText("")
            binding.dateInput.setText("")
            binding.descInput.setText("")
        }
    }*/


    private fun addNote(uang: String, tanggal: String, ket: String) {

        val note = Note(uang, tanggal, ket)

        FirebaseFirestore.getInstance().collection("NOTES")
            .add(note)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(context, "Berhasil",Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Berhasil Tambah Data!, ID: " + documentReference.id)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Gagal",Toast.LENGTH_SHORT).show()
                Log.e(TAG, "Gagal Tambah Data", e)
            }
    }
}