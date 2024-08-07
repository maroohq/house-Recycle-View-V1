package com.example.houses

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.example.houses.login

class register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val input_email = findViewById<EditText>(R.id.dtEmail_register)
        val input_pass1 = findViewById<EditText>(R.id.etPassword_register1)
        val input_pass2 = findViewById<EditText>(R.id.etPassword_register2)
        val btn_register = findViewById<Button>(R.id.btn_register_register)
        auth = Firebase.auth

        btn_register.setOnClickListener {
            if (input_pass1.text.isEmpty() || input_pass2.text.isEmpty() || input_email.text.isEmpty())
            {
                Toast.makeText(this,"Please fill all the fields",Toast.LENGTH_SHORT).show()

            }
           else
            if(input_pass1.text.toString() != input_pass2.text.toString() )
                {
                    Toast.makeText(this,"Password must Match",Toast.LENGTH_SHORT).show()
                }

            else
            {
            auth.createUserWithEmailAndPassword(input_email.text.toString(),input_pass1.text.toString()).addOnCompleteListener{task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(this,"Registeration Complete ${auth.currentUser?.email}",Toast.LENGTH_LONG).show()
                    val intent = Intent(this,MainActivity::class.java)
                    this.startActivity(intent)
                }
                else
                {

                    Toast.makeText(
                        baseContext,
                        task.exception?.message.toString(),
                        Toast.LENGTH_SHORT,
                    ).show()
                }

            }


            }





        }


    }
}