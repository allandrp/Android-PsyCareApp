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
import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.data.PostDiscussionsResponse
import com.example.psycareapp.databinding.ActivitySignupBinding
import com.example.psycareapp.utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var emailEditText: EmailEditText
    private lateinit var passwordEditText: PasswordEditText
    private lateinit var signupButton: Button

    private lateinit var fbAuth: FirebaseAuth

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
        Utils.isLoading(binding.progressBarSignUp, true)
        fbAuth.createUserWithEmailAndPassword(
            emailEditText.text.toString(),
            passwordEditText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = fbAuth.currentUser
                    if (currentUser != null) {
                        saveDataSignup(currentUser)
                    }
                } else {
                    Utils.isLoading(binding.progressBarSignUp, false)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveDataSignup(user: FirebaseUser) {
        ApiConfig.getApiPsyCare().addUserData(
            user.uid,
            user.email!!.split("@")[0]
        ).enqueue(object : Callback<PostDiscussionsResponse> {
            override fun onResponse(
                call: Call<PostDiscussionsResponse>,
                response: Response<PostDiscussionsResponse>
            ) {
                Utils.isLoading(binding.progressBarSignUp, false)
                finish()
            }

            override fun onFailure(call: Call<PostDiscussionsResponse>, t: Throwable) {
                Utils.isLoading(binding.progressBarSignUp, false)
                finish()
            }

        })
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