package com.example.pc02_dsm_delgado_linares.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pc02_dsm_delgado_linares.R
import com.example.pc02_dsm_delgado_linares.model.MovimientoModel

class MovimientoAdapter(private val movimientos: List<MovimientoModel>) :
    RecyclerView.Adapter<MovimientoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvMonto: TextView = itemView.findViewById(R.id.tvMonto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movimiento, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimiento = movimientos[position]
        holder.tvDescripcion.text = movimiento.descripcion
        holder.tvFecha.text = movimiento.fecha
        holder.tvMonto.text = String.format("S/ %.2f", movimiento.monto)
    }

    override fun getItemCount(): Int = movimientos.size
}