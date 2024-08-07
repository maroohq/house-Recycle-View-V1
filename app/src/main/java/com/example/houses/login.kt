package com.example.houses

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val auth = FirebaseAuth.getInstance()

        val btn_register = findViewById<Button>(R.id.btn_register)
        val email = findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = findViewById<EditText>(R.id.editTextPhone)
        val btn_login = findViewById<Button>(R.id.btn_login)

        btn_login.setOnClickListener{

            auth.signInWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener {
                task -> if (task.isSuccessful)
                {
                    val intent = Intent(this,MainActivity::class.java)
                    this.startActivity(intent)
                }
                else
                {
                    Toast.makeText(this,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }





        }


        btn_register.setOnClickListener{
            val intent = Intent(this,register::class.java)
            this.startActivity(intent)
        }

    }
}