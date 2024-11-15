package com.example.pc02_dsm_delgado_linares.ui.slideshow

import android.os.Bundle
import android.util.Log
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
import com.example.pc02_dsm_delgado_linares.databinding.FragmentSlideshowBinding
import com.example.pc02_dsm_delgado_linares.model.MovimientoModel
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowFragment : Fragment() {
    private lateinit var rvMovimientos: RecyclerView
    private lateinit var tvBalanceTotal: TextView
    private lateinit var movimientoAdapter: MovimientoAdapter

    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_slideshow, container, false)
        rvMovimientos = view.findViewById(R.id.rvMovimientos)
        tvBalanceTotal = view.findViewById(R.id.tvBalanceTotal)
        rvMovimientos.layoutManager = LinearLayoutManager(requireContext())

        cargarMovimientos("11") // ejemplo para el mes de noviembre

        return view
    }

    private fun cargarMovimientos(mes: String) {
        db.collection("Movimientos")
            .get()
            .addOnSuccessListener { documents ->
                val movimientos = mutableListOf<MovimientoModel>()
                var balanceTotal = 0.0

                for (document in documents) {
                    val descripcion = document.getString("descripcion") ?: ""
                    val fecha = document.getString("fecha") ?: ""
                    val monto = document.getDouble("monto") ?: 0.0

                    val movimiento = MovimientoModel(descripcion, fecha, monto)
                    movimientos.add(movimiento)
                    balanceTotal += monto
                }

                movimientoAdapter = MovimientoAdapter(movimientos)
                rvMovimientos.adapter = movimientoAdapter
                tvBalanceTotal.text = "Balance Total: S/ $balanceTotal"
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Error al obtener documentos", exception)
            }

    }
}