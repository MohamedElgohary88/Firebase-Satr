package com.example.firebase_satr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    val REQUEST_CODE_SIGNIN = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login.setOnClickListener {
            loginUser()
        }

       sign_with_google.setOnClickListener {
           val options =GoogleSignInOptions
               .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken(getString(R.string.web_client))
               .requestEmail()
               .build()
           val googleSignInClient = GoogleSignIn.getClient(this,options)
           googleSignInClient.signInIntent.also {
               startActivityForResult(it,REQUEST_CODE_SIGNIN)
           }
       }

 /*
        sign_with_google.setOnClickListener {
           val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        // Your server's client ID, not your Android client ID.
                        .setServerClientId(getString(R.string.web_client))
                        // Only show accounts previously used to sign in.
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build()
        }
*/
    }

    private fun loginUser() {

        val email = et_email_login.text.toString()
        val password = et_password_login.text.toString()

        if ( email.isNotEmpty() && password.isNotEmpty() ) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = task.result!!.user!!
                        Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, SecondActivity::class.java)
                        intent.putExtra("user_id", firebaseUser.uid)
                        intent.putExtra("email_id", email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun googleAuthFirebase(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        FirebaseAuth.getInstance().signInWithCredential(credentials)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Logged In",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message,Toast.LENGTH_SHORT).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SIGNIN){
            val account = GoogleSignIn.getSignedInAccountFromIntent(data).result
            account?.let {
                googleAuthFirebase(it)
            }
        }
    }
}