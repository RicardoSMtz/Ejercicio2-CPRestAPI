package com.example.ejercicio2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ejercicio2.R
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
        binding.tvLongDesc.visibility = View.INVISIBLE
        binding.rvTransformaciones.visibility = View.INVISIBLE
        binding.bgImageView.visibility= View.INVISIBLE


        lifecycleScope.launch {
            try {

               val personaje = dragonBallAPI.getCharacterDetail(id)
                //Log.d("Transformations", personaje.transformations?.joinToString { it.name.toString() } ?: "Sin transformaciones")
                binding.tvNameDetail.text =  personaje.name
                binding.tvKi.text = getString(R.string.ki, personaje.ki)//personaje.ki
                binding.tvAffiliationDetail.text = getString(R.string.affiliation, personaje.affiliation)
                binding.tvMaxKi.text = getString(R.string.max_ki,personaje.maxKi)// personaje.maxKi
                binding.tvGender.text = getString(R.string.gender,personaje.gender )//personaje.gender
                binding.tvLongDesc.text = personaje.description//personaje.description
                binding.tvRace.text = getString(R.string.race,personaje.race )//personaje.race

                Glide.with(binding.root.context)
                    .load(personaje.image)
                    .into(binding.ivImageDetail)

                if (!personaje.transformations.isNullOrEmpty()) {
                    binding.trans.visibility= View.VISIBLE
                    binding.tvLongDesc.visibility = View.VISIBLE
                    binding.rvTransformaciones.visibility = View.VISIBLE
                    binding.bgImageView.visibility = View.VISIBLE
                    val adapter = TransformationAdapter(personaje.transformations)
                    binding.rvTransformaciones.layoutManager = LinearLayoutManager(requireContext())
                    binding.rvTransformaciones.adapter = adapter


                } else {
                    binding.rvTransformaciones.visibility = View.GONE
                    binding.trans.visibility= View.INVISIBLE
                    binding.tvLongDesc.visibility = View.VISIBLE
                    binding.rvTransformaciones.visibility = View.VISIBLE
                    binding.bgImageView.visibility = View.VISIBLE
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