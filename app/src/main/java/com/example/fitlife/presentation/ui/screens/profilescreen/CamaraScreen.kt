package com.example.fitlife.presentation.ui.screens.profilescreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import coil.compose.rememberImagePainter
import com.example.fitlife.ui.theme.purple
import com.example.fitlife.ui.theme.yellowAccent
import java.io.File

@Composable
fun CameraScreen() {
    val context = LocalContext.current

    // Estado para manejar la URI de la imagen capturada
    var capturedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Lanzador para el permiso de cámara
    val launcherPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Si se otorga el permiso, lanzar la cámara
            launchCamera(context) { uri ->
                capturedImageUri = uri
            }
        } else {
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .background(purple),
            elevation = 4.dp
        ) {
            if (capturedImageUri != null) {
                Image(
                    painter = rememberImagePainter(capturedImageUri),
                    contentDescription = "Imagen de perfil",
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                     Text(
                        text = "Sin imagen",
                        color = yellowAccent,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(purple)
                .align(Alignment.Center)
                .clickable {
                    launcherPermission.launch(android.Manifest.permission.CAMERA)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = "Abrir Cámara",
                tint = Color.White
            )
        }
    }
}

private fun launchCamera(context: Context, onImageCaptured: (Uri) -> Unit) {
    // Crear un archivo temporal para la imagen capturada
    val photoFile = File(
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
        "profile_picture.jpg"
    ).apply {
        if (!exists()) {
            createNewFile()
        }
    }

    // Obtener la URI del archivo utilizando FileProvider
    val photoUri: Uri = FileProvider.getUriForFile(
        context,
        "com.example.fitlife.fileprovider", // Asegúrate de que coincida con el `Manifest`
        photoFile
    )

    // Crear el intent para capturar la imagen
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
        putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
    }

    // Verificar si hay una aplicación capaz de manejar el intent
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
        onImageCaptured(photoUri) // Retorna la URI al capturar
    } else {
        Toast.makeText(context, "No se encontró una aplicación de cámara", Toast.LENGTH_SHORT).show()
    }
}
