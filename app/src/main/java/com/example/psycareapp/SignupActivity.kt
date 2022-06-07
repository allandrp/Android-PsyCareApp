package com.example.psycareapp

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.psycareapp.customview.EmailEditText
import com.example.psycareapp.customview.PasswordEditText
import com.example.psycareapp.customview.SignupButton
import com.example.psycareapp.customview.UsernameEditText
import com.example.psycareapp.data.User
import com.example.psycareapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signupButton: Button

    private lateinit var fbAuth: FirebaseAuth
    private val dbFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        fbAuth = FirebaseAuth.getInstance()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        supportActionBar?.hide()

        emailEditText = binding!!.email
        passwordEditText = binding!!.password
        signupButton = binding!!.signup

        setSignupButtonEnable()
        init()

        signupButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            signUp()
        }
    }

    private fun signUp() {
        fbAuth.createUserWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveDataSignup(Firebase.auth.currentUser)
                } else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveDataSignup(user: FirebaseUser?) {
        val userData = User(
            user?.uid.toString(),
            emailEditText.text?.split("@")?.get(0) ?: ""
        )

        fbAuth.currentUser?.let {
            dbFirestore.collection("users").document(it.uid)
                .set(userData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Signup succesful", Toast.LENGTH_SHORT).show()
                    fbAuth.signOut()
                    finish()
                }

                .addOnFailureListener { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    fbAuth.signOut()
                    finish()
                }
        }
    }

    private fun setSignupButtonEnable() {
        val email = binding?.email?.text
        val password = binding?.password?.text

        signupButton.isEnabled =
                email != null && Patterns.EMAIL_ADDRESS.matcher(email.toString().trim()).matches()
                && password != null && password.toString().length >= 6
    }

    private fun init() {

        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setSignupButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setSignupButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }
}