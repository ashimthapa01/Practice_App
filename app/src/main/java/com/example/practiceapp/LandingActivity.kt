package com.example.practiceapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LandingActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    companion object {
        var FILE_NAME: String = "shared_pref"
    }

    private lateinit var btnsignup: Button
    private lateinit var btnsignin: Button
    private lateinit var closebtnsignup: Button
    private lateinit var closebtnsignin: Button
    private lateinit var signup: Button
    private lateinit var signin: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var confirmpass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getString("full name", "") != "") {
            val intent = Intent(this, DashActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
        setContentView(R.layout.activity_landing)

        firebaseAuth = FirebaseAuth.getInstance()

        // SIGN_UP
        var btmshtsignup = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignup))
        var btmshtsignin = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignin))
        btmshtsignup.setPeekHeight(0)
        btnsignup = findViewById(R.id.landingbtnsignup)
        closebtnsignup = findViewById(R.id.closebtn)
        btnsignup.setOnClickListener() {
            btmshtsignup.state = BottomSheetBehavior.STATE_EXPANDED
            signup = findViewById(R.id.btnSignUp)
            signup.setOnClickListener() {
                signupActivity()
            }
        }


        var btnsignup1 = findViewById<Button>(R.id.btnSignUp)
        sharedPreferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        btnsignup1.setOnClickListener() {
            val intent = Intent(this, DashActivity::class.java)
            startActivity(intent)
            finish()
        }


        closebtnsignup.setOnClickListener() {
            btmshtsignup.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        // SIGN_IN
        btmshtsignin.setPeekHeight(0)
        btnsignin = findViewById(R.id.landingbtnsignin)
        btnsignin.setOnClickListener() {
            signinActivity()

        }
        closebtnsignin = findViewById(R.id.closebtnsignin)
        closebtnsignin.setOnClickListener() {
            btmshtsignin.state = BottomSheetBehavior.STATE_COLLAPSED
        }

    }

    private fun signupActivity() {
        var btmshtsignup = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignup))
        var btmshtsignin = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignin))

        email = findViewById(R.id.etEmail)
        pass = findViewById(R.id.etPassword)
        confirmpass = findViewById(R.id.etConfirmPassword)
        val email: String = email.text.toString()
        val pass: String = pass.text.toString()
        val confirmpass: String = confirmpass.text.toString()
        if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()) {
            if (pass == confirmpass) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnFailureListener {
                        Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
                    }
                    .addOnSuccessListener {
                        btmshtsignup.state = BottomSheetBehavior.STATE_COLLAPSED
                        btmshtsignin.state = BottomSheetBehavior.STATE_EXPANDED
                        val nameId = findViewById<TextInputEditText>(R.id.etFullName)
                        val name: String = nameId.text.toString()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("full name", name)
                        editor.apply()

                    }

            } else {
                Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Empty fields are not allowed!!!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun signinActivity() {
        var btmshtsignin2 = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignin))
        btmshtsignin2.state = BottomSheetBehavior.STATE_EXPANDED
        email = findViewById(R.id.tietemail)
        pass = findViewById(R.id.tietpassword)
        signin = findViewById(R.id.btnSignIn)
        signin.setOnClickListener() {

            val email: String = email.text.toString()
            val pass: String = pass.text.toString()
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnFailureListener {
                    Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this, DashActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Empty fields are not allowed!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

}

