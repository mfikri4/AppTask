package com.example.apptask.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apptask.R
import com.example.apptask.adapter.ListAdapter
import com.example.apptask.databinding.*
import com.example.apptask.model.Note
import com.example.apptask.utils.Callback
import com.example.apptask.utils.DataCallbackNote
import com.example.apptask.view.auth.LoginActivity
import com.example.apptask.viewmodel.NoteViewModel
import com.example.apptask.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment(), DataCallbackNote, Callback {

    private lateinit var auth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingTop: CustomMenuHomeBinding

    private  lateinit var lAdapter: ListAdapter
    private lateinit var viewModel : NoteViewModel
    private lateinit var viewModelFactory: ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        bindingTop = CustomMenuHomeBinding.inflate(layoutInflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.txtDataPencairan.setOnClickListener {
            auth.signOut()
            Intent(requireActivity(), LoginActivity::class.java).also{
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }

        binding.txtViewAll.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            val ldFrag = ListDataFragment()
            transaction.replace(R.id.homeFragment, ldFrag)
            transaction.addToBackStack(null)
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }

        viewModelFactory = ViewModelFactory.getInstance(requireActivity())
        viewModel = ViewModelProvider(this, viewModelFactory).get(NoteViewModel::class.java)

        lAdapter = ListAdapter(this,this)
        init()
        observe()
    }
    private fun observe(){
        viewModel.getNoteFew().observe(viewLifecycleOwner, Observer { list ->
            lAdapter.setData(list)

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
        val extraData = HomeFragmentDirections.actionHomeFragmentToDetailFragment(note)
        view?.findNavController()?.navigate(extraData)
    }

    override fun onCallCallback(note: Note) {
        val extraData = HomeFragmentDirections.actionHomeFragmentToEditFragment(note)
        view?.findNavController()?.navigate(extraData)
    }

}