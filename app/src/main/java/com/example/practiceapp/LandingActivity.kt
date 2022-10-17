package com.example.practiceapp

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LandingActivity : AppCompatActivity() {

    companion object {
        public lateinit var email: EditText
        public lateinit var pass: EditText
        public lateinit var confirmpass: EditText
    }

    private lateinit var btnsignup: Button
    private lateinit var btnsignin: Button
    private lateinit var closebtnsignup: Button
    private lateinit var closebtnsignin: Button
    private lateinit var signup: Button
    private lateinit var signin: Button
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        closebtnsignup.setOnClickListener() {
            btmshtsignup.state = BottomSheetBehavior.STATE_COLLAPSED
        }


        // SIGN_IN
        //    var btmshtsignin2 = BottomSheetBehavior.from(findViewById(R.id.btmsheetsignin))
        btmshtsignin.setPeekHeight(0)
        btnsignin = findViewById(R.id.landingbtnsignin)
        btnsignin.setOnClickListener() {
        //   signinActivity()
            val intent = Intent(this, DashActivity::class.java)
            startActivity(intent)
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
        Log.i("as","asdf")
        if (email.isNotEmpty() && pass.isNotEmpty() && confirmpass.isNotEmpty()) {
            if (pass == confirmpass) {
                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnFailureListener{
                        Toast.makeText(this,""+it.message,Toast.LENGTH_SHORT).show()
                    }
                    .addOnCompleteListener {
                        Log.i("ashim","Here")
                        if (it.isSuccessful) {
                            btmshtsignup.state = BottomSheetBehavior.STATE_COLLAPSED
                            btmshtsignin.state = BottomSheetBehavior.STATE_EXPANDED
                        } else {
                            Toast.makeText(
                                this,
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
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
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnFailureListener{
                    Toast.makeText(this,""+it.message,Toast.LENGTH_SHORT).show()
                }.addOnCompleteListener {
                    if (it.isSuccessful) {
                        //     btmshtsignin2.state = BottomSheetBehavior.STATE_COLLAPSED
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

