package id.mralifakbar.edlink.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import id.mralifakbar.edlink.MainActivity
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivitySplashMiddleBinding
import id.mralifakbar.edlink.ui.auth.LoginActivity
import id.mralifakbar.edlink.ui.auth.LoginSiakadActivity
import id.mralifakbar.edlink.ui.auth.RegisterActivity
import id.mralifakbar.edlink.utils.Preferences

class SplashMiddleActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashMiddleBinding
    private lateinit var preferences: Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashMiddleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences(this)

        if (preferences.getValues("login_status").equals("1")) {
            val moveToMain = Intent(this@SplashMiddleActivity, MainActivity::class.java)
            startActivity(moveToMain)
            finish()
        }

        binding.apply {
            loginWithEmail.setOnClickListener {
                startActivity(Intent(this@SplashMiddleActivity, LoginActivity::class.java))
            }

            loginWithSiakad.setOnClickListener {
                startActivity(
                    Intent(
                        this@SplashMiddleActivity,
                        LoginSiakadActivity::class.java
                    )
                )
            }

            toRegister.setOnClickListener {
                startActivity(Intent(this@SplashMiddleActivity, RegisterActivity::class.java))
            }
        }
    }
}


