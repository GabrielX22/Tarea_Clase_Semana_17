package com.example.tareaclasesemana17

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity3 : AppCompatActivity() {
    //Se declaran las variables y las variables lateinit
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    lateinit var previewimagen: ImageView
    lateinit var btnelegir: ImageButton
    lateinit var btnsubir: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        //Se asigna el id con la variable
        btnelegir = findViewById(R.id.elegirimagen)
        btnsubir = findViewById(R.id.subirimagen)
        previewimagen = findViewById(R.id.lapreview)
        //Se crea la instancia para guardar informacion en firebase
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        //Al oprimir el boton de elegir ejecutara la funcion galeria y el de subir ejecturara la funcion subir
        btnelegir.setOnClickListener { galeria() }
        btnsubir.setOnClickListener { subir() }
    }
    private fun galeria() {
        //Se crea un intent que abre abre la galeria, asi para seleccionar una imagen
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Elija una imagen"), PICK_IMAGE_REQUEST)
    }
    //Se devolvera el activity si se da hacia atras o si se elige un bitmap, el cual es una imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST  && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }
            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)
                previewimagen.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    //Se selecciona el objeto que fue elegido y se manda a la carpeta del storage en firebase, de lo contrario, pedira elegir una imagen
    private fun subir(){
        if(filePath != null){
            val ref = storageReference?.child("imagenes/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)
            Toast.makeText(this, "Se ha subido correctamente a la base", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "Porfavor, Seleccione una imagen", Toast.LENGTH_SHORT).show()
        }
    }
}