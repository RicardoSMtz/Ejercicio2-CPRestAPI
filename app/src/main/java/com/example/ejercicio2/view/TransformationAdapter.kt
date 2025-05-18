package com.example.ejercicio2.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicio2.data.remote.model.Transformation
import com.example.ejercicio2.databinding.TransformacionElementBinding

class TransformationAdapter(
    private val transformations: List<Transformation>
) : RecyclerView.Adapter<TransformationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransformationViewHolder {
        val binding = TransformacionElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransformationViewHolder(binding)
    }

    override fun getItemCount(): Int = transformations.size

    override fun onBindViewHolder(holder: TransformationViewHolder, position: Int) {
        holder.bind(transformations[position])
    }
}
