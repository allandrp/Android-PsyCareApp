package com.example.psycareapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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

    private var _activitySignupBinding: ActivitySignupBinding? = null
    private val binding get() = _activitySignupBinding

    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signupButton: SignupButton

    private lateinit var fbAuth: FirebaseAuth
    private val dbFirestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        fbAuth = FirebaseAuth.getInstance()

        supportActionBar?.hide()

        emailEditText = binding!!.email
        passwordEditText = binding!!.password
        signupButton = binding!!.signup

        setSignupButtonEnable()
        init()

        signupButton.setOnClickListener {
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

        dbFirestore.collection("users")
            .add(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Signup succesful", Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                finish()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                Firebase.auth.signOut()
                finish()
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