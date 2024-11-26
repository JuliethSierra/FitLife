package com.example.fitlife.presentation.ui.screens.utils

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.Period
import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun showAShortMessage(stringId: String, context: Context) {
            Toast.makeText(context, stringId, Toast.LENGTH_SHORT).show()
        }

        fun showALongMessage(stringId: String, context: Context) {
            Toast.makeText(context, stringId, Toast.LENGTH_LONG).show()
        }

        fun isAValidEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
            return emailRegex.matches(email)
        }

        fun calculateAge(fechaNacimiento: String): Int {
            // Definir el formato de la fecha
            val formatoFecha = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

            // Convertir la fecha de nacimiento de String a Date
            val fechaNacimientoDate = formatoFecha.parse(fechaNacimiento)

            // Obtener la fecha actual
            val fechaActual = Calendar.getInstance()

            // Crear un objeto Calendar para la fecha de nacimiento
            val calendarNacimiento = Calendar.getInstance()
            calendarNacimiento.time = fechaNacimientoDate

            // Calcular la diferencia de años
            var edad = fechaActual.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR)

            // Ajustar si no ha cumplido años este año
            if (fechaActual.get(Calendar.MONTH) < calendarNacimiento.get(Calendar.MONTH) ||
                (fechaActual.get(Calendar.MONTH) == calendarNacimiento.get(Calendar.MONTH) &&
                        fechaActual.get(Calendar.DAY_OF_MONTH) < calendarNacimiento.get(Calendar.DAY_OF_MONTH))) {
                edad--
            }

            return edad
        }
    }
}