package com.example.psycareapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.psycareapp.customview.LoginButton
import com.example.psycareapp.customview.PasswordEditText
import com.example.psycareapp.customview.SignupButton
import com.example.psycareapp.customview.UsernameEditText
import com.example.psycareapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private var _activityLoginBinding: ActivityLoginBinding? = null
    private val binding get() = _activityLoginBinding

    private lateinit var loginButton: LoginButton
    private lateinit var usernameEditText: UsernameEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signupButton: SignupButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        loginButton = binding!!.login
        usernameEditText = binding!!.username
        passwordEditText = binding!!.password
        signupButton = binding!!.signup

        setLoginButtonEnable()
        init()

        loginButton.setOnClickListener {
            Toast.makeText(this, "Hello Wahid 😁", Toast.LENGTH_SHORT).show()
        }

        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setLoginButtonEnable() {
        val username = binding?.username?.text
        val password = binding?.password?.text
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
}