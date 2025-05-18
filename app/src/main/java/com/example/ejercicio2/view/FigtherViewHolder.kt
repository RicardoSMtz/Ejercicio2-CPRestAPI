package com.example.ejercicio2.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio2.data.remote.model.Character
import com.example.ejercicio2.databinding.FigtherElementBinding

class FigtherViewHolder (
    private val binding: FigtherElementBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(fighter : Character){
    binding.tvName.text = fighter.name
    binding.tvAfiliation.text = fighter.affiliation

    Glide.with(binding.root.context)
        .load(fighter.image)
        .into(binding.ivThumbnail)
        }

}