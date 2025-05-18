package com.example.ejercicio2.view

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejercicio2.data.remote.model.Transformation
import com.example.ejercicio2.databinding.TransformacionElementBinding

class TransformationViewHolder(
    private val binding: TransformacionElementBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(transformation: Transformation) {
        binding.tvNombreTransformacion.text = transformation.name
        binding.tvKiTransformacion.text = transformation.ki
        Glide.with(binding.root.context)
            .load(transformation.image)
            .into(binding.ivTransformacion)
    }
}
