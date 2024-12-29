package com.test.shopeeclone.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.test.shopeeclone.R
import com.test.shopeeclone.data.model.DataModel
import com.test.shopeeclone.databinding.ActivityLoginBinding
import com.test.shopeeclone.helper.Result
import com.test.shopeeclone.helper.ViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels { ViewModelFactory.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        loginViewModel.getLoginData().observe(this) {
            if (it.isLogin) {
                val moveIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(moveIntent)
            }
        }

        binding.btnLogin.setOnClickListener {

            loginViewModel.login(binding.inputUser.text.toString(), binding.inputPassword.text.toString()).observe(this) {
                if (it != null) {
                    when (it) {
                        is Result.Failure -> {
                            Toast.makeText(this@LoginActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                        }
                        Result.Loading -> {

                        }
                        is Result.Success -> {
                            loginViewModel.saveData(DataModel(it.data.token.toString(), true))
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

        }


    }
}