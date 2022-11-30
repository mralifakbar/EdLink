package id.mralifakbar.edlink.ui.main.home

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var firesetoreDb = Firebase.firestore

        val docRef = firesetoreDb.collection("users").document(FirebaseAuth.getInstance().uid.toString())
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    binding.helloUser.text = "Hello ${document.data?.getValue("name")}"
                    Log.d(TAG, "DocumentSnapshot data: ${document.data?.getValue("name")}")
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }

}