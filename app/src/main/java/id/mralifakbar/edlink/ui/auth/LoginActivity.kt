package id.mralifakbar.edlink.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.mralifakbar.edlink.MainActivity
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivityLoginBinding
import id.mralifakbar.edlink.utils.Preferences

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = Preferences(this)

        if (preferences.getValues("login_status").equals("1")){
            val moveToMain = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(moveToMain)
            finish()
        }

        binding.apply {
            toRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            backBtn.setOnClickListener {
                finish()
            }
            btnLogin.setOnClickListener {
                when {
                    TextUtils.isEmpty(edtEmail.text.toString().trim() {it <= ' '}) -> {
                        edtEmail.error = "Email is required"
                    }
                    TextUtils.isEmpty(edtPassword.text.toString().trim() {it <= ' '}) -> {
                        edtPassword.error = "Password is required"
                    }
                    else -> {
                        val email:String = edtEmail.text.toString().trim() {it <= ' '}
                        val password:String = edtPassword.text.toString()

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                OnCompleteListener<AuthResult> { task ->
                                    if (task.isSuccessful) {
                                        val lastLogin = hashMapOf(
                                            "lastLoginAt" to System.currentTimeMillis().toString()
                                        )
                                        val firestoreDb = Firebase.firestore

                                        firestoreDb.collection("users")
                                            .document(FirebaseAuth.getInstance().uid.toString())
                                            .set(lastLogin, SetOptions.merge())

                                        Snackbar.make(
                                            binding.root,
                                            "Success login!!",
                                            Snackbar.LENGTH_SHORT
                                        ).show()

                                        preferences.setValues("login_status", "1")
                                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                        finish()
                                    } else {
                                        Snackbar.make(
                                            binding.root,
                                            task.exception!!.message.toString(),
                                            Snackbar.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            )
                    }
            }
        }
    }
}}