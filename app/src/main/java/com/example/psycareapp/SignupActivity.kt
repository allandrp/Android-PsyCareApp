package com.example.psycareapp

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.psycareapp.data.ApiConfig
import com.example.psycareapp.data.PostDiscussionsResponse
import com.example.psycareapp.databinding.ActivitySignupBinding
import com.example.psycareapp.utils.Utils
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var signupButton: Button

    private lateinit var fbAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbAuth = FirebaseAuth.getInstance()

        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        supportActionBar?.hide()

        emailEditText = binding.emailSignup
        passwordEditText = binding.passwordSignup
        usernameEditText = binding.usernameSignup
        signupButton = binding.signupButton

//        setSignupButtonEnable()
//        init()

        signupButton.setOnClickListener {
            imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
            if (usernameEditText.text.toString().isEmpty()) {
                binding.usernameSignupPostLayout.error = getString(R.string.invalid_empty)
            }

            if (emailEditText.text.toString().isEmpty()) {
                binding.emailSignupPostLayout.error = getString(R.string.invalid_empty)
            }

            if (passwordEditText.text.toString().isEmpty()) {
                binding.passwordSignupPostLayout.error = getString(R.string.invalid_empty)
            } else {
                signUp()
            }
        }

        binding.emailSignupPostLayout.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    binding.emailSignupPostLayout.error = getString(R.string.invalid_email)
                } else {
                    binding.emailSignupPostLayout.error = ""
                }
            } else {
                binding.emailSignupPostLayout.error = ""
            }
        }

        binding.passwordSignupPostLayout.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                if (text?.length!! < 6) {
                    binding.passwordSignupPostLayout.error = getString(R.string.invalid_password)
                } else {
                    binding.passwordSignupPostLayout.error = ""
                }
            } else {
                binding.passwordSignupPostLayout.error = ""
            }
        }

        binding.usernameSignupPostLayout.editText?.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotEmpty()) {
                if (text?.length!! < 6) {
                    binding.usernameSignupPostLayout.error = getString(R.string.invalid_username)
                } else {
                    binding.usernameSignupPostLayout.error = ""
                }
            } else {
                binding.usernameSignupPostLayout.error = ""
            }
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
                        saveDataSignup(currentUser, usernameEditText.text.toString())
                    }
                } else {
                    Utils.isLoading(binding.progressBarSignUp, false)
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveDataSignup(user: FirebaseUser, username: String) {
        ApiConfig.getApiPsyCare().addUserData(
            user.uid,
            username
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

//    private fun setSignupButtonEnable() {
//        val email = binding?.email?.text
//        val password = binding?.password?.text
//
//        signupButton.isEnabled =
//            email != null && Patterns.EMAIL_ADDRESS.matcher(email.toString().trim()).matches()
//                    && password != null && password.toString().length >= 6
//    }
//
//    private fun init() {
//
//        emailEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                setSignupButtonEnable()
//            }
//
//            override fun afterTextChanged(s: Editable) {
//            }
//        })
//
//        passwordEditText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                setSignupButtonEnable()
//            }
//
//            override fun afterTextChanged(s: Editable) {
//            }
//        })
//    }
}