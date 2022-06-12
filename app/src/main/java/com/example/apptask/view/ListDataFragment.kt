package com.example.apptask.view


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptask.R
import com.example.apptask.adapter.ListAdapter
import com.example.apptask.databinding.FragmentListDataBinding
import com.example.apptask.databinding.ItemRowBinding
import com.example.apptask.model.Note
import com.example.apptask.utils.Callback
import com.example.apptask.utils.DataCallbackNote
import com.example.apptask.viewmodel.NoteViewModel
import com.example.apptask.viewmodel.ViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore

class ListDataFragment : Fragment(), DataCallbackNote, Callback {

    private var _binding: FragmentListDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingList : ItemRowBinding

    private  lateinit var lAdapter: ListAdapter
    private lateinit var viewModel : NoteViewModel
    private lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListDataBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        lAdapter = ListAdapter(this,this)
        init()
        observe()



    }
    private fun observe(){
        viewModel.getNote().observe(viewLifecycleOwner, Observer {
            lAdapter.setData(it)

        })
    }
    private fun init (){
        with(binding.rvListData) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = lAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCallback(note: Note) {
        val extraData = ListDataFragmentDirections.actionListDataFragmentToDetailFragment(note)
        view?.findNavController()?.navigate(extraData)
    }

    override fun onCallCallback(note: Note) {
        val extraData = ListDataFragmentDirections.actionListDataFragmentToEditFragment(note)
        view?.findNavController()?.navigate(extraData)
    }


}