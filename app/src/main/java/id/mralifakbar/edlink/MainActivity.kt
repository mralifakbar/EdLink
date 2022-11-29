package id.mralifakbar.edlink

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.mralifakbar.edlink.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitOnce:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val buttomnNavView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_explore, R.id.navigation_class, R.id.navigation_chat, R.id.navigation_account
            )
        )
        buttomnNavView.setupWithNavController(navController)
    }


    override fun onBackPressed() {
        if(doubleBackToExitOnce){
            val builder = MaterialAlertDialogBuilder(this)
            builder.setTitle("Keluar Aplikasi")
            builder.setMessage("Apakah yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Yakin", DialogInterface.OnClickListener { dialogInterface, i ->
                    finish()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.cancel()
                })

            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()
        }

        this.doubleBackToExitOnce = true

        Handler().postDelayed({
            kotlin.run { doubleBackToExitOnce = false }
        }, 2000)
    }
}