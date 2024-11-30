// GenderDropdownMenu.kt
package com.example.fitlife.presentation.ui.components.dropdownmenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import com.example.fitlife.domain.model.enums.GenderEnum

@Composable
fun GenderDropdownMenu(
    selectedGender: MutableState<GenderEnum>,
    labelId: String = "Género",
    focusRequester: FocusRequester = FocusRequester(),
    onNext: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = selectedGender.value.name,
        onValueChange = {},
        label = { Text(text = labelId) },
        readOnly = true,
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester) // Add the focusRequester here
            .clickable { expanded = true },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), // Set imeAction to Done
        keyboardActions = KeyboardActions(
            onDone = { onNext?.invoke() } // Invoke onNext when done
        )
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
                    onNext?.invoke() // Invoke onNext after selection
                },
                text = { Text(text = gender.name) }
            )
        }
    }
}