package id.mralifakbar.edlink.ui.main.account

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import id.mralifakbar.edlink.R
import id.mralifakbar.edlink.databinding.FragmentAccountBinding
import id.mralifakbar.edlink.ui.auth.LoginActivity
import id.mralifakbar.edlink.ui.splashscreen.SplashMiddleActivity
import id.mralifakbar.edlink.utils.Preferences


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = Preferences(requireContext())

        binding.apply {
            btnLogout.setOnClickListener {
                val builder = MaterialAlertDialogBuilder(requireContext())

                builder.setTitle("Logout")
                builder.setMessage("Are you sure to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->
                        preferences.setValues("login_status", "0")

                        FirebaseAuth.getInstance().signOut()
                        Log.e("Keluar", "Berhasil")
                        startActivity(Intent(requireContext(), SplashMiddleActivity::class.java))
                        activity?.finish()
                    })
                    .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                        dialogInterface.cancel()
                    })
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        }
    }
}