package com.example.fitlife.presentation.ui.screens.utils

import android.content.Context
import android.widget.Toast
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

        fun calculateAge(bornDate: String): Int {
                // Definir el formato de la fecha
                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

                // Convertir la fecha de nacimiento de String a Date
                val formatBornDate = dateFormat.parse(bornDate)

                // Obtener la fecha actual
                val actualDate = Calendar.getInstance()

                // Crear un objeto Calendar para la fecha de nacimiento
                val calendarNacimiento = Calendar.getInstance()
                calendarNacimiento.time = formatBornDate

                // Calcular la diferencia de años
                var edad = actualDate.get(Calendar.YEAR) - calendarNacimiento.get(Calendar.YEAR)

                // Ajustar si no ha cumplido años este año
                if (actualDate.get(Calendar.MONTH) < calendarNacimiento.get(Calendar.MONTH) ||
                    (actualDate.get(Calendar.MONTH) == calendarNacimiento.get(Calendar.MONTH) &&
                            actualDate.get(Calendar.DAY_OF_MONTH) < calendarNacimiento.get(Calendar.DAY_OF_MONTH))) {
                    edad--
                }

                return edad
        }

        fun calculateIMC(weight: Float?, height: Float?): Float {
            var imc = 0.0f
            imc = if(weight != null && height != null){
                val heightInMeters = height / 100
                weight / (heightInMeters * heightInMeters)
            }else{
                0.0f
            }
            return imc
        }

    }
}