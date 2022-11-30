package id.mralifakbar.edlink.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.mralifakbar.edlink.MainActivity
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }
            toLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }

            btnRegis.setOnClickListener {
                registerAccount()
            }
        }
    }

    private fun registerAccount() {
        var valid = true

        binding.apply {
            val email = edtEmail.text.toString()
            val name = edtName.text.toString()
            val pass = edtPassword.text.toString()
            val rePass = edtRePassword.text.toString()

            if (email.isEmpty()) {
                valid = false
                edtEmail.error = "Email is required"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                valid = false
                edtEmail.error = "Email doesn't valid"
            }

            if (name.isEmpty()) {
                valid = false
                edtName.error = "Name is required"
            } else if (name.length <= 6) {
                valid = false
                edtName.error = "Name is too short, min 7 character"
            } else if (name.length >= 25) {
                valid = false
                edtName.error = "Name is too long, max 24 character"
            }

            if (pass.isEmpty()) {
                valid = false
                edtPassword.error = "Password is required"
            } else if (pass.length < 8) {
                valid = false
                edtPassword.error = "Password is too short, min 8 character"
            }

            if (rePass.isEmpty()) {
                valid = false
                edtRePassword.error = "Password Confirmation is required"
            } else if (rePass != pass) {
                valid = false
                edtRePassword.error = "Password doesn't match"
            }

            if (valid) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(
                        OnCompleteListener<AuthResult> { task ->
                            if (task.isSuccessful) {
                                val newUser: FirebaseUser = task.result!!.user!!

                                Snackbar.make(
                                    binding.root,
                                    "Registration successfull, please check your email!",
                                    Snackbar.LENGTH_SHORT
                                ).show()

                                newUser.sendEmailVerification()

                                val firestoreDb = Firebase.firestore

                                val prepareUser = hashMapOf(
                                    "name" to name,
                                    "email" to email,
                                    "profilePhoto" to "",
                                    "university" to "",
                                    "verified" to false,
                                    "registerAt" to System.currentTimeMillis().toString(),
                                    "lastLoginAt" to System.currentTimeMillis().toString(),
                                    "hpNumber" to ""
                                )

                                firestoreDb.collection("users")
                                    .document(newUser.uid)
                                    .set(prepareUser)
                                    .addOnSuccessListener {
                                        startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                                        finish()
                                    }.addOnFailureListener {
                                        Log.e("gagal", "yeah")
                                    }
                            } else {
                                Snackbar.make(
                                    binding.root,
                                    task.exception?.message.toString(),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
            }
        }
    }
}