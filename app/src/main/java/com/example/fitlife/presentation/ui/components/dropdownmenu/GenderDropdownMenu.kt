// GenderDropdownMenu.kt
package com.example.fitlife.presentation.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.fitlife.domain.model.enums.GenderEnum

@Composable
fun GenderDropdownMenu(
    selectedGender: MutableState<GenderEnum>,
    labelId: String = "Género"
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedGender.value.name,
        onValueChange = {},
        label = { Text(text = labelId) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true},
    )

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        GenderEnum.values().forEach { gender ->
            DropdownMenuItem(
                onClick = {
                    selectedGender.value = gender
                    expanded = false
                },
                text = { Text(text = gender.name) }
            )
        }
    }
}
