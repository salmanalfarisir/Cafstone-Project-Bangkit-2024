package com.cafstone.application.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cafstone.application.databinding.ActivitySignupBinding
import com.cafstone.application.view.ViewModelFactory

class SignupActivity : AppCompatActivity() {
    private val viewModel by viewModels<SignUpViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(false)
        setupView()
        setupAction()
        playAnimation()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setbuttonenabled() {
        if (
            binding.nameEditText.text.toString().isNotEmpty() &&
            (binding.emailEditText.text.toString()
                .isNotEmpty() && binding.emailEditTextLayout.error == null) &&
            (binding.passwordEditText.text.toString()
                .isNotEmpty() && binding.passwordEditTextLayout.error == null) &&
            (binding.passwordConfirmEditText.text.toString()
                .isNotEmpty() && binding.passwordConfirmEditTextLayout.error == null)
        ) {
            binding.signupButton.isEnabled = true
        } else {
            binding.signupButton.isEnabled = false
        }
    }

    private fun setupAction() {
        binding.passwordEditText.setErrorTextView(binding.passwordEditTextLayout)
        binding.passwordEditText.setPasswordTextView(binding.passwordConfirmEditText)
        binding.passwordEditText.setPasswordErrorTextView(binding.passwordConfirmEditTextLayout)
        binding.emailEditText.setErrorTextView(binding.emailEditTextLayout)
        binding.passwordConfirmEditText.setTextView(binding.passwordConfirmEditText)
        binding.passwordConfirmEditText.setErrorTextView(binding.passwordConfirmEditTextLayout)
        binding.passwordConfirmEditText.setpasswordTextView(binding.passwordEditText)
        setbuttonenabled()
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                setbuttonenabled()
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed
            }
        }
        binding.nameEditText.addTextChangedListener(textWatcher)
        binding.emailEditText.addTextChangedListener(textWatcher)
        binding.passwordEditText.addTextChangedListener(textWatcher)
        binding.passwordConfirmEditText.addTextChangedListener(textWatcher)

        binding.signupButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val nama = binding.nameEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.isNotEmpty() && nama.isNotEmpty() && password.isNotEmpty()) {
                if (binding.emailEditTextLayout.error == null && binding.passwordEditTextLayout.error == null) {
                    viewModel.register(nama, email, password)
                    viewModel.isLoading.observe(this) { ds ->
                        showLoading(ds)
                        if (ds == false) {
                            viewModel.isStatus.observe(this@SignupActivity) { cs ->
                                if (cs != null) {
                                    if (cs.error == false) {
                                        Toast.makeText(
                                            this,
                                            "Register Succesfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        finish()
                                    } else {
                                        AlertDialog.Builder(this@SignupActivity).apply {
                                            setTitle("Gagal")
                                            setMessage("${cs.message}")
                                            setNegativeButton("Lanjut") { dialog, _ ->
                                                dialog.dismiss()
                                            }
                                            create()
                                            show()
                                        }
                                    }
                                } else {
                                    AlertDialog.Builder(this@SignupActivity).apply {
                                        setTitle("Gagal")
                                        setMessage("$email Sudah Tersedia, Mohon menggunakan email yang lain")
                                        setNegativeButton("Lanjut") { dialog, _ ->
                                            dialog.dismiss()
                                        }
                                        create()
                                        show()
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Masih Ada Error di Email / Password", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Masih Belum Terisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(100)
        val nameTextView =
            ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(100)
        val nameEditTextLayout =
            ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val emailTextView =
            ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(100)
        val emailEditTextLayout =
            ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordTextView =
            ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(100)
        val passwordEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(100)
        val passwordconfirmTextView =
            ObjectAnimator.ofFloat(binding.passwordConfirmTextView, View.ALPHA, 1f).setDuration(100)
        val passwordconfirmEditTextLayout =
            ObjectAnimator.ofFloat(binding.passwordConfirmEditTextLayout, View.ALPHA, 1f)
                .setDuration(100)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(100)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                passwordconfirmTextView,
                passwordconfirmEditTextLayout,
                signup
            )
            startDelay = 100
        }.start()
    }

}