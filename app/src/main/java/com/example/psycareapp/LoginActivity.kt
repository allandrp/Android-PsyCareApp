package com.example.psycareapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.widget.doOnTextChanged
import com.example.psycareapp.customview.*
import com.example.psycareapp.databinding.ActivityLoginBinding
import com.example.psycareapp.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginButton: Button
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var signupButton: TextView
    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        supportActionBar?.hide()

        loginButton = binding.login
        usernameEditText = binding.emailLogin
        passwordEditText = binding.password
        signupButton = binding.registerLink

        loginButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            if(usernameEditText.text.toString().isEmpty()){
                binding.emailLoginPostLayout.error = getString(R.string.invalid_empty)
            }

            if(passwordEditText.text.toString().isEmpty()){
                binding.passwordPostLayout.error = getString(R.string.invalid_empty)
            }else{
                login()
            }
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle())
        }
        
        binding.emailLoginPostLayout.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
                    binding.emailLoginPostLayout.error = getString(R.string.invalid_email)
                }else{
                    binding.emailLoginPostLayout.error = ""
                }
            }else{
                binding.emailLoginPostLayout.error = ""
            }
        }

        binding.passwordPostLayout.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                if(text?.length!! < 6){
                    binding.passwordPostLayout.error = getString(R.string.invalid_password)
                }else{
                    binding.passwordPostLayout.error = ""
                }
            }else{
                binding.passwordPostLayout.error = ""
            }
        }
    }

    private fun login() {
        Utils.isLoading(binding.progressBarLogin, true)
        fbAuth.signInWithEmailAndPassword(usernameEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    Utils.isLoading(binding.progressBarLogin, false)
                    finish()
                } else {
                    Utils.isLoading(binding.progressBarLogin, false)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
}