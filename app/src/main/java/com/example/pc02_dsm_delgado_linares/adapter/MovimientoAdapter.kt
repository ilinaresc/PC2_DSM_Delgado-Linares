package com.example.pc02_dsm_delgado_linares.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pc02_dsm_delgado_linares.R
import com.example.pc02_dsm_delgado_linares.model.Movimiento

class MovimientoAdapter(private val movimientos: List<Movimiento>) : RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder>() {

    class MovimientoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDescription: TextView = view.findViewById(R.id.tvDescription)
        val tvFecha: TextView = view.findViewById(R.id.tvFecha)
        val tvMonto: TextView = view.findViewById(R.id.tvMonto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movimiento, parent, false)
        return MovimientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {
        val movimiento = movimientos[position]
        holder.tvDescription.text = movimiento.description
        holder.tvFecha.text = movimiento.fecha
        holder.tvMonto.text = "S/ ${movimiento.monto}"
    }

    override fun getItemCount(): Int = movimientos.size
}
