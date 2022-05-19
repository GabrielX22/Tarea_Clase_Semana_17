package com.example.tareaclasesemana17

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    //Se crean las variables lateinit
    lateinit var edtemail: EditText
    lateinit var etrePass: EditText
    private lateinit var etPass: EditText
    private lateinit var btnregistro: ImageButton
    lateinit var txtredireccion: TextView
    // Se crea el objeto de autentiacion de la base firebase
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //Se buscan las variables por sus ID
        edtemail = findViewById(R.id.edtelemail)
        etrePass = findViewById(R.id.edtrepass)
        etPass = findViewById(R.id.edtpass)
        btnregistro = findViewById(R.id.imageregistro)
        txtredireccion = findViewById(R.id.txtini)

        // Se inicializa la autenticacion de la firebase
        auth = Firebase.auth
        //Al oprimir el boton, se ejecutara lo que haya en la funcion
        btnregistro.setOnClickListener {
            elregistro()
        }
        //Al oprimir el Textview, lo redigira al activity de inicar sesion
        txtredireccion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    //La funcion "elregistro"
    private fun elregistro(){
        //Se asignan las variables
        val email = edtemail.text.toString()
        val contra = etPass.text.toString()
        val recontra = etrePass.text.toString()
        //Si cualquiera de todos los textview esta vacio, saldra un toast que pida que se rellene los parametros
        if(email.isBlank() || contra.isBlank() || recontra.isBlank()){
            Toast.makeText(this, "Se necesitan llenar todos los parametros", Toast.LENGTH_SHORT).show()
            return
        }
        //Si la contraseña es diferente a la confirmacion,se muestra un Toast de que las contraseñas no coinciden
        if(contra != recontra){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                .show()
            return
        }
        //Se usa la variable auth., para crear usuario con correo y contraseña, trayendo los parametros
        auth.createUserWithEmailAndPassword(email, contra)
            .addOnCompleteListener(this) { task ->
                //Si se hizo correctamente el registro, se manda un toast diciendo que se ha registrado correctamente
                //y tambien pasa al activity de login y se finaliza este
                if (task.isSuccessful) {
                    //Inicio de sesión exitoso, actualización de la interfaz de usuario con la información del usuario registrado
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "¡Usuario Registrado Correctamente!",
                        Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    //Si hubo fallos a la hora de crearlo, se envia un Toast diciendo que hubo fallo de autentificacion
                    //y se vacian los edittext
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Fallo de Autenticacion",
                        Toast.LENGTH_SHORT).show()
                    edtemail.setText("")
                    etPass.setText("")
                    etrePass.setText("")
                }
            }
    }
}