package com.example.pc02_dsm_delgado_linares.ui.gallery

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pc02_dsm_delgado_linares.R
import com.example.pc02_dsm_delgado_linares.adapter.MovimientoAdapter
import com.example.pc02_dsm_delgado_linares.databinding.FragmentGalleryBinding
import com.example.pc02_dsm_delgado_linares.databinding.FragmentHomeBinding
import com.example.pc02_dsm_delgado_linares.model.MovimientoModel
import com.example.pc02_dsm_delgado_linares.ui.home.HomeViewModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore

    // EditText variables
    private lateinit var etFechaGasto: EditText
    private lateinit var etDescripcionGasto: EditText
    private lateinit var etMontoGasto: EditText


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.etFechaGasto.setOnClickListener {
            showDatePickerDialog()
        }

        // Inicializar Firestore
        db = FirebaseFirestore.getInstance()

        // Inicializar los EditTexts
        etFechaGasto = binding.etFechaGasto
        etDescripcionGasto = binding.etDescripcionGasto
        etMontoGasto = binding.etMontoGasto

        // Configura el botón para guardar el registro
        binding.btRegistrarGasto.setOnClickListener {
            guardarRegistro()
        }

        return root
    }

    private fun guardarRegistro() {
        // Obtener los valores de los campos
        val fecha = etFechaGasto.text.toString()
        val descripcion = etDescripcionGasto.text.toString()
        val monto = etMontoGasto.text.toString().toDoubleOrNull()

        // Verificar si los campos son válidos
        if (fecha.isEmpty() || descripcion.isEmpty() || monto == null) {
            Toast.makeText(requireContext(), "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear el registro en formato HashMap
        val movimiento = hashMapOf(
            "fecha" to fecha,
            "descripcion" to descripcion,
            "monto" to monto
        )

        // Guardar en Firestore
        db.collection("Movimientos")  // Nombre de la colección
            .add(movimiento)  // Agregar un documento
            .addOnSuccessListener { documentReference ->
                // Mostrar mensaje de éxito
                Toast.makeText(requireContext(), "Registro agregado con éxito", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                // Mostrar mensaje de error
                Toast.makeText(requireContext(), "Error al agregar el registro", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            // Formatea la fecha seleccionada y la muestra en el EditText
            val formattedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            binding.etFechaGasto.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}