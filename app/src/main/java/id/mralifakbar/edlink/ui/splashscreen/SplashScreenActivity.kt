package id.mralifakbar.edlink.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivitySplashScreenBinding
import id.mralifakbar.edlink.ui.auth.LoginActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            ivEdlinkLogo.alpha = 0f

            ivEdlinkLogo.animate().setDuration(1000).alpha(1f).withEndAction {
                startActivity(Intent(this@SplashScreenActivity, SplashMiddleActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }
    }
}