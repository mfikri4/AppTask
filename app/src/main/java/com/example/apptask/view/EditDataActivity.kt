package com.example.apptask.view

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.apptask.adapter.ListAdapter
import com.example.apptask.databinding.ActivityEditDataBinding
import com.example.apptask.databinding.CustomMenuHomeBinding
import com.example.apptask.model.Note
import com.example.apptask.utils.DataCallbackNote
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore

class EditDataActivity : AppCompatActivity(){

    private lateinit var binding: ActivityEditDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        val collection = db.collection("NOTES")
        val pserial = intent.getSerializableExtra("notes")

        val pengguna = if(pserial != null){
            binding.btnDelete.visibility = View.VISIBLE
            pserial as HashMap<*,*>
        }else{
            null
        }

        if(pengguna != null){
            binding.uangInput.setText(pengguna["uang"].toString())
            binding.dateInput.setText(pengguna["tanggal"].toString())
            binding.descInput.setText(pengguna["desc"].toString())
        }

        binding.btnDelete.setOnClickListener {
            collection.document(pengguna!!["id"].toString()).delete().addOnSuccessListener {
                Toast.makeText(this, "Success delete data with id ${pengguna["id"]}", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
        binding.btnEdit.setOnClickListener {
            val data = hashMapOf(
                "uang" to binding.uangInput.text.toString(),
                "tanggal" to binding.dateInput.text.toString(),
                "ket" to binding.descInput.text.toString()
            )
            if(pengguna == null){
                collection.add(data).addOnSuccessListener {documentReference ->
                    Toast.makeText(this, "Success add new data with id ${documentReference.id}", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }else{
                @Suppress("UNCHECKED_CAST")
                collection.document(pengguna["id"].toString()).update(data as Map<String, Any>).addOnSuccessListener {
                    Toast.makeText(this, "Success update data with id ${pengguna["id"]}", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                }
            }
        }
    }

}