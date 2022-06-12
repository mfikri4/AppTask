package com.example.apptask.utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.apptask.model.Note
import com.google.firebase.firestore.FirebaseFirestore

class RemoteDataSource {
    companion object {

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource().apply { instance = this }
            }
    }
    private val db = FirebaseFirestore.getInstance()

    fun getNoteFew () : MutableLiveData<List<Note>> {

        val listNote : MutableLiveData<List<Note>> = MutableLiveData()
        val docRef = db.collection("NOTES").orderBy("uang").limit(3)

        val list = ArrayList<Note>()
        docRef.get().addOnCompleteListener { document->
            list.clear()
            for (i in document.result!!) {
                val reseller = Note(
                    i.getString("uang"),
                    i.getString("tanggal"),
                    i.getString("ket")

                )
                list.add(reseller)
            }
            listNote.postValue(list)
        }.addOnFailureListener { e->
            Log.d("Remote data source","${e.message}")
        }
        return listNote
    }
    fun getNote () : MutableLiveData<List<Note>> {

        val listNote : MutableLiveData<List<Note>> = MutableLiveData()
        val docRef = db.collection("NOTES")

        val list = ArrayList<Note>()
        docRef.get().addOnCompleteListener { document->
            list.clear()
            for (i in document.result!!) {
                val reseller = Note(
                    i.getString("uang"),
                    i.getString("tanggal"),
                    i.getString("ket")

                )
                list.add(reseller)
            }
            listNote.postValue(list)
        }.addOnFailureListener { e->
            Log.d("Remote data source","${e.message}")
        }
        return listNote
    }


    fun getDeleteNote () : MutableLiveData<List<Note>> {

        val listNote : MutableLiveData<List<Note>> = MutableLiveData()
        val docRef = db.collection("NOTES")

        val list = ArrayList<Note>()
        docRef.document().delete().addOnCompleteListener { document->
            Log.d("Remote data source - Berhasil","${document}")
        }.addOnFailureListener { e->
            Log.d("Remote data source","${e.message}")
        }
        return listNote
    }
}