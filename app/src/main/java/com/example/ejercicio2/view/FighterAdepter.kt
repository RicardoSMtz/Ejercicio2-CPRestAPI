package com.example.ejercicio2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio2.data.remote.model.Character
import com.example.ejercicio2.data.remote.model.CharacterDragon
import com.example.ejercicio2.databinding.FigtherElementBinding

class FighterAdepter(
    private val fighters: List<Character>,
    private val onFigtherClick: (Character) -> Unit

): RecyclerView.Adapter<FigtherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FigtherViewHolder {
        val binding = FigtherElementBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FigtherViewHolder(binding)
    }

    override fun getItemCount(): Int = fighters.size

    override fun onBindViewHolder(holder: FigtherViewHolder, position: Int){
        val fighter = fighters[position]
        holder.bind(fighter)
        holder.itemView.setOnClickListener{
            onFigtherClick(fighter)

        }
    }
}