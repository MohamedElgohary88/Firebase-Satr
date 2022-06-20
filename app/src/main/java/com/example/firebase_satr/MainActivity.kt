package com.example.firebase_satr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register.setOnClickListener {
            registerUser()
        }
        tv_login_link.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }

    private fun registerUser() {
        val email = et_email_register.text.toString()
        val password = et_password_register.text.toString()

        if ( email.isNotEmpty() && password.isNotEmpty() ){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val firebaseUser = task.result!!.user!!
                        Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
                        val intent =Intent(this,SecondActivity::class.java)
                        intent.putExtra("user_id",firebaseUser.uid)
                        intent.putExtra("email_id",email)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
                    }

                }
        }

    }
}