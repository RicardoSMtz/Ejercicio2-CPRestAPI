package com.example.ejercicio2.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejercicio2.data.remote.model.DragonBallAPI
import com.example.ejercicio2.data.remote.model.util.Constants
import com.example.ejercicio2.databinding.FragmentListaPersonajesBinding
import com.example.ejercicio2.view.FighterAdepter
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaPersonajesFragment : Fragment() {

    private var _binding: FragmentListaPersonajesBinding? = null
    private val binding get() = _binding!!
    private lateinit var dragonBallAPI: DragonBallAPI
    private lateinit var retrofit: Retrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaPersonajesBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun instanciarRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dragonBallAPI = retrofit.create(DragonBallAPI::class.java)
        //lifecycleScope.launch {
         //   try {
                /*val personajes=dragonBallAPI.getCharacters()
                Log.d("APPLOGS","Respuesta: $personajes")
                val personaje = dragonBallAPI.getCharacterDetail(1) // Goku
                Log.d("API_TEST", "Nombre: ${personaje.name}")
                Log.d("API_TEST", "Raza: ${personaje.race}")
                Log.d("API_TEST", "MaxKI: ${personaje.maxKi}")
                Log.d("API_TEST", "Descripcion: ${personaje.description}")
                Log.d("API_TEST", "Genero: ${personaje.gender}")
                Log.d("API_TEST", "Transformaciones: ${personaje.transformations?.joinToString { it.name ?: "Sin nombre" }}")*/
          //  }
           // catch (e: Exception){
                //Log.e("API_TEST", "Error al obtener detalle del personaje", e)
          //  }
        //}

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciarRetrofit()
        lifecycleScope.launch {
            try {
            val respuesta=dragonBallAPI.getCharacters()
            val personajes = respuesta.info
            binding.rv1.layoutManager = GridLayoutManager(requireContext(),2)
            binding.rv1.adapter = FighterAdepter(personajes)
              }
             catch (e: Exception){
            Log.e("API_TEST", "Error al obtener detalle del personaje", e)
             }
            finally {
                binding.pbLoading.visibility = View.INVISIBLE
            }
            }
        //binding.btnPrueba.setOnClickListener{
        //    requireActivity().supportFragmentManager.beginTransaction().replace(
        //        R.id.ConteinerFragmentMain, DetallePersonajeFragment()
        //    ).addToBackStack(null).commit()
        //}
    }
}