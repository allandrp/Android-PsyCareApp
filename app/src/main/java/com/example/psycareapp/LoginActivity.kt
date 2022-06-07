package com.example.psycareapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.psycareapp.customview.*
import com.example.psycareapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var loginButton: Button
    private lateinit var usernameEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
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
        usernameEditText = binding.username
        passwordEditText = binding.password
        signupButton = binding.registerLink

        setLoginButtonEnable()
        init()

        loginButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            login()
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login() {
        showLoading(true)
        fbAuth.signInWithEmailAndPassword(usernameEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    showLoading(false)
                    finish()
                } else {
                    showLoading(false)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setLoginButtonEnable() {
        val username = binding.username.text
        val password = binding.password.text
        loginButton.isEnabled =
                username != null && username.toString().isNotEmpty()
                && password != null && password.toString().length >= 6
    }

    private fun init(){
        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setLoginButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun showLoading(loading: Boolean){
        if(loading){
            binding.progressBarLogin.visibility = View.VISIBLE
        }else{
            binding.progressBarLogin.visibility = View.GONE
        }
    }
}