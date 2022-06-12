package com.example.apptask.adapter

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.apptask.databinding.ItemRowBinding
import com.example.apptask.model.Note
import com.example.apptask.utils.Callback
import com.example.apptask.utils.DataCallbackNote
import com.example.apptask.view.EditDataActivity


class ListAdapter (private val dataCallback: DataCallbackNote, private val callback: Callback
): RecyclerView.Adapter<ListAdapter.Holder>(){

    private val listData = ArrayList<Note>()

    fun setData(nt : List<Note>){
        this.listData.addAll(nt)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }


    inner class Holder (private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(data : Note){
            with(binding){
                tvUang.text = data.uang
                tvDate.text = data.tanggal
                tvDate.setOnClickListener { dataCallback.onCallback(data) }
                txtPemasukan.setOnClickListener { dataCallback.onCallback(data) }
                tvUang.setOnClickListener { dataCallback.onCallback(data) }
                ivDot.setOnClickListener { callback.onCallCallback(data)}
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val list = listData[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = listData.size

}
