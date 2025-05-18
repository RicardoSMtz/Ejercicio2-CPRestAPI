package com.example.ejercicio2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ejercicio2.data.remote.model.DragonBallAPI
import com.example.ejercicio2.data.remote.model.util.Constants
import com.example.ejercicio2.databinding.FragmentDetallePersonajeBinding
import com.example.ejercicio2.view.TransformationAdapter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val ARG_ID = "id"

class DetallePersonajeFragment : Fragment() {
    private var id: String? = null
    private var _binding: FragmentDetallePersonajeBinding? = null
    private val binding get() = _binding!!
    private lateinit var dragonBallAPI: DragonBallAPI
    private lateinit var retrofit: Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallePersonajeBinding.inflate(inflater, container,false)
        return binding.root
    }

    private fun instanciarRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        dragonBallAPI = retrofit.create(DragonBallAPI::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciarRetrofit()
        binding.trans.visibility= View.INVISIBLE

        lifecycleScope.launch {
            try {

               val personaje = dragonBallAPI.getCharacterDetail(id)
                binding.tvNameDetail.text = personaje.name
                binding.tvKi.text = personaje.ki
                binding.tvAffiliationDetail.text = personaje.affiliation
                binding.tvMaxKi.text = personaje.maxKi
                binding.tvGender.text = personaje.gender
                binding.tvLongDesc.text = personaje.description
                binding.tvRace.text = personaje.race

                Glide.with(binding.root.context)
                    .load(personaje.image)
                    .into(binding.ivImageDetail)
                if (!personaje.transformations.isNullOrEmpty()) {
                    val adapter = TransformationAdapter(personaje.transformations)
                    binding.rvTransformaciones.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvTransformaciones.adapter = adapter
                    binding.rvTransformaciones.visibility = View.VISIBLE
                    binding.trans.visibility= View.VISIBLE
                } else {
                    binding.rvTransformaciones.visibility = View.GONE
                    binding.trans.visibility= View.INVISIBLE
                }

            } catch (e: Exception){
                //Manejar error
            } finally {
                binding.pbLoading.visibility = View.INVISIBLE
            }
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(id: String) =
            DetallePersonajeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}