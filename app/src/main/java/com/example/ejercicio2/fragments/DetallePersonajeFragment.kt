package com.example.ejercicio2.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

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
    ): View {
        _binding = FragmentDetallePersonajeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun instanciarRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        dragonBallAPI = retrofit.create(DragonBallAPI::class.java)
    }

    private fun Fragment.showError(messageResId: Int) {
        Toast.makeText(requireContext(), getString(messageResId), Toast.LENGTH_SHORT).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        instanciarRetrofit()
        binding.trans.visibility = View.INVISIBLE
        binding.bgImageView.visibility = View.INVISIBLE
        binding.tvLongDesc.visibility = View.INVISIBLE

        binding.btnRetry.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            binding.btnRetry.visibility = View.GONE
            cargarDatos()
        }
        binding.pbLoading.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.GONE
        cargarDatos()
    }

    private fun cargarDatos() {
        lifecycleScope.launch {
            try {
                val personaje = dragonBallAPI.getCharacterDetail(id)
                mostrarDatosPersonaje(personaje)
                binding.btnRetry.visibility = View.GONE

            } catch (e: IOException) {
                binding.btnRetry.visibility = View.VISIBLE
                showError(R.string.network)
            } catch (e: HttpException) {
                binding.btnRetry.visibility = View.VISIBLE
                showError(R.string.server)
            } catch (e: Exception) {
                binding.btnRetry.visibility = View.VISIBLE
                showError(R.string.unexpected)
            } finally {
                binding.pbLoading.visibility = View.INVISIBLE
            }
        }
    }

    private fun mostrarDatosPersonaje(personaje: com.example.ejercicio2.data.remote.model.DetailDragon) {
        binding.tvNameDetail.text = personaje.name
        binding.tvKi.text = getString(R.string.ki, personaje.ki)
        binding.tvAffiliationDetail.text = getString(R.string.affiliation, personaje.affiliation)
        binding.tvMaxKi.text = getString(R.string.max_ki, personaje.maxKi)
        binding.tvGender.text = getString(R.string.gender, personaje.gender)
        binding.tvLongDesc.text = personaje.description
        binding.tvRace.text = getString(R.string.race, personaje.race)

        Glide.with(binding.root.context)
            .load(personaje.image)
            .into(binding.ivImageDetail)

        if (!personaje.transformations.isNullOrEmpty()) {
            binding.trans.visibility = View.VISIBLE
            binding.rvTransformaciones.visibility = View.VISIBLE
            val adapter = TransformationAdapter(personaje.transformations)
            binding.rvTransformaciones.layoutManager = LinearLayoutManager(requireContext())
            binding.rvTransformaciones.adapter = adapter
        } else {
            binding.trans.visibility = View.INVISIBLE
            binding.rvTransformaciones.visibility = View.INVISIBLE
        }
        binding.tvLongDesc.visibility = View.VISIBLE
        binding.bgImageView.visibility = View.VISIBLE
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
