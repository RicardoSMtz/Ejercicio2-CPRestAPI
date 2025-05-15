package com.example.ejercicio2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.ejercicio2.data.remote.model.DragonBallAPI
import com.example.ejercicio2.databinding.FragmentListaPersonajesBinding
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
        _binding = FragmentListaPersonajesBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun instanciarRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(("https://dragonball-api.com/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dragonBallAPI = retrofit.create(DragonBallAPI::class.java)
        lifecycleScope.launch {
            try {
                val personajes=dragonBallAPI.getCharacters()
                Log.d("APPLOGS","Respuesta: $personajes")
            }
            catch (e: Exception){

            }
        }
    }



    //Instacionado interfaz API


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciarRetrofit()
        binding.btnPrueba.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.ConteinerFragmentMain, DetallePersonajeFragment()
            ).addToBackStack(null).commit()
        }
    }
}