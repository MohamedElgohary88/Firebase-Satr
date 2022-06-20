package com.example.firebase_satr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        var userId = intent.getStringExtra("user_id")
        var emailId = intent.getStringExtra("email_id")

       findViewById<TextView>(R.id.tv_user_id).text = userId
       findViewById<TextView>(R.id.tv_email_id).text = emailId


        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }



    }
}