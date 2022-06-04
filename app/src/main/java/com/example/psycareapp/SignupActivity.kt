package com.example.psycareapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.Toast
import com.example.psycareapp.customview.EmailEditText
import com.example.psycareapp.customview.PasswordEditText
import com.example.psycareapp.customview.SignupButton
import com.example.psycareapp.customview.UsernameEditText
import com.example.psycareapp.databinding.ActivityLoginBinding
import com.example.psycareapp.databinding.ActivitySignupBinding
import java.util.regex.Pattern

class SignupActivity : AppCompatActivity() {

    private var _activitySignupBinding: ActivitySignupBinding? = null
    private val binding get() = _activitySignupBinding

    private lateinit var emailEditText: EmailEditText
    private lateinit var usernameEditText: UsernameEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signupButton: SignupButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activitySignupBinding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.hide()

        emailEditText = binding!!.email
        usernameEditText = binding!!.username
        passwordEditText = binding!!.password
        signupButton = binding!!.signup

        setSignupButtonEnable()
        init()

        signupButton.setOnClickListener {
            Toast.makeText(this, "Selamat Mendaftar, Wahid 😍", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSignupButtonEnable() {
        val email = binding?.email?.text
        val username = binding?.username?.text
        val password = binding?.password?.text

//        var emailChecker = if (Patterns.EMAIL_ADDRESS.matcher(email.toString().trim()).matches()){
//        }

        signupButton.isEnabled =
//                (!email.let { Patterns.EMAIL_ADDRESS.matcher(it.toString()).matches() })
//                email != null && email.toString().isNotEmpty()
                email != null && Patterns.EMAIL_ADDRESS.matcher(email.toString().trim()).matches()
                && username != null && username.toString().isNotEmpty()
                && password != null && password.toString().length >= 6
    }

    private fun init(){
        usernameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setSignupButtonEnable()
            }
            override fun afterTextChanged(s: Editable) {
            }
        })

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