package id.mralifakbar.edlink.ui.main.classroom

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import com.google.api.Authentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.mralifakbar.edlink.MainActivity
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.ActivityCreateClassBinding
import kotlin.random.Random

class CreateClassActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateClassBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            backBtn.setOnClickListener {
                finish()
            }

            btnAddClass.setOnClickListener {
                addClass()
            }

        }
    }

    public fun addClass() {
        binding.apply {
            val className = edtClassName.text.toString()
            val classCode = edtClassCode.text.toString()
            val classDesc = edtClassDesc.text.toString()
            var valid = true
            if (className.isEmpty()) {
                valid = false
                edtClassName.error = "Class name is required"
            } else if (className.length <= 6) {
                valid = false
                edtClassName.error = "Class name is too short, min 7 character"
            } else if (className.length >= 25) {
                valid = false
                edtClassName.error = "Class name is too long, max 24 character"
            }

            if (classCode.isEmpty()) {
                valid = false
                edtClassCode.error = "Class code is required"
            } else if (classCode.length < 6) {
                valid = false
                edtClassCode.error = "Class code is too short, min 6 character"
            } else if (classCode.length > 10) {
                valid = false
                edtClassCode.error = "Class code is too long, max 10 character"
            }

            if (classDesc.isEmpty()) {
                valid = false
                edtClassDesc.error = "Class code is required"
            }

            if (valid) {
                val db = Firebase.firestore
                var creatorName: String = ""
                val docRef =
                    db.collection("users").document(FirebaseAuth.getInstance().uid.toString())
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            creatorName = document.data?.getValue("name") as String
                            Log.d(
                                ContentValues.TAG,
                                "DocumentSnapshot data: ${document.data?.getValue("name")}"
                            )
                        } else {
                            Log.d(ContentValues.TAG, "No such document")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d(ContentValues.TAG, "get failed with ", exception)
                    }

                val prepareClassData = hashMapOf(
                    "name" to className,
                    "code" to classCode,
                    "desc" to classDesc,
                    "creatorUid" to FirebaseAuth.getInstance().uid.toString(),
                    "creatorName" to creatorName
                )

                var docName = FirebaseAuth.getInstance().uid.toString() + classCode + Random.nextInt(100, 999).toString()
                db.collection("class")
                    .document(docName)
                    .set(prepareClassData)
                    .addOnSuccessListener {
                        Log.e("berhasil", "yeah")
                        Snackbar.make(
                            binding.root,
                            "Class berhasil dibuat!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@CreateClassActivity, MainActivity::class.java))
                        finish()
                    }.addOnFailureListener {
                        Log.e("gagal", "yeah")
                    }
            }
        }

    }
}