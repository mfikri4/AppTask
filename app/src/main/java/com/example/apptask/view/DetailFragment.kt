package com.example.apptask.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.apptask.R
import com.example.apptask.databinding.FragmentDetailBinding
import com.example.apptask.model.Note

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        showNote(args.noteDetail)
        binding.ivBack.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_detailFragment_to_listDataFragment)
        }
    }
    private fun showNote(data: Note?) {

        data?.let {
            with(binding) {
                tvDate.text = StringBuilder("${data.tanggal}")
                tvRp.text = StringBuilder("${data.uang}")
                tvDesc.text = StringBuilder("${data.ket}")
            }
        }
    }

}