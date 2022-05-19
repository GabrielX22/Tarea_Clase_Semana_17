package com.example.tareaclasesemana17

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    //Se crean las variables lateinit
    private lateinit var redirecto: TextView
    lateinit var edtemail: EditText
    private lateinit var edtcontra: EditText
    lateinit var btnini: ImageButton
    //Se crea la autenticacion de la Firebase
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //buscando los objetos por sus ID
        redirecto = findViewById(R.id.txtcuenta)
        btnini = findViewById(R.id.imagelogin)
        edtemail = findViewById(R.id.edtemail)
        edtcontra = findViewById(R.id.edtcontra)
        //Se hace una instancia de la aunteticacion
        auth = FirebaseAuth.getInstance()
        //Boton que al oprimir ejecutara la funcion
        btnini.setOnClickListener {
            iniciar()
        }
        //Al oprimir el textview se pasara al Activity de Registro y finalizara en la que estamos
        redirecto.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
    //la funcion de "iniciar"
    private fun iniciar(){
        //definiendo las variables
        val email = edtemail.text.toString()
        val contra = edtcontra.text.toString()
        //La variable con el tipo de inicio de sesion, con sus parametros
        auth.signInWithEmailAndPassword(email,contra).addOnCompleteListener(this){
            //Si el proceso fue exitoso, marcar un Toast diciendo que el acceso fue autorizado
            if(it.isSuccessful){
                Toast.makeText(this, "Acceso Autorizado!", Toast.LENGTH_SHORT).show()
                //Se crea la variable intent, para enviarlo al activity de los contactos
                val intent = Intent(this, MainActivity3::class.java)
                //Iniciar Activity
                startActivity(intent)
                //Se finaliza el activity en que estamos
                finish()
            }else
            //De lo contrario, se mandara un Toast que el acceso no fue autorizado y se vacian los editext
                Toast.makeText(this, "Acceso no autorizado! ", Toast.LENGTH_SHORT).show()
            edtemail.setText("")
            edtcontra.setText("")
        }
    }
}