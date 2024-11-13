package com.example.pc02_dsm_delgado_linares.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pc02_dsm_delgado_linares.R
import com.example.pc02_dsm_delgado_linares.adapter.MovimientoAdapter
import com.example.pc02_dsm_delgado_linares.databinding.FragmentGalleryBinding
import com.example.pc02_dsm_delgado_linares.model.Movimiento
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {
    private lateinit var rvMovimientos: RecyclerView
    private lateinit var tvBalanceTotal: TextView
    private lateinit var movimientoAdapter: MovimientoAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        rvMovimientos = view.findViewById(R.id.rvMovimientos)
        tvBalanceTotal = view.findViewById(R.id.tvBalanceTotal)

        rvMovimientos.layoutManager = LinearLayoutManager(requireContext())

        cargarMovimientos()

        return view
    }

    private fun cargarMovimientos() {
        db.collection("Movimientos")
            .get()
            .addOnSuccessListener { documents ->
                val movimientos = mutableListOf<Movimiento>()
                var balanceTotal = 0.0

                for (document in documents) {
                    val description = document.getString("description") ?: ""
                    val fecha = document.getString("fecha") ?: ""
                    val monto = document.getDouble("monto") ?: 0.0

                    val movimiento = Movimiento(description, fecha, monto)
                    movimientos.add(movimiento)
                    balanceTotal += monto
                }

                // Configurar el adaptador y el RecyclerView
                movimientoAdapter = MovimientoAdapter(movimientos)
                rvMovimientos.adapter = movimientoAdapter

                // Mostrar el balance total
                tvBalanceTotal.text = "Balance Total: S/ $balanceTotal"
            }
            .addOnFailureListener { exception ->
                // Manejar el error si ocurre
            }
    }
}