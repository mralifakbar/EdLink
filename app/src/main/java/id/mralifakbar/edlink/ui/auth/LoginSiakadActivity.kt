package id.mralifakbar.edlink.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivityLoginSiakadBinding

class LoginSiakadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginSiakadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSiakadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
        }

    }
}