package com.example.cryptohub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cryptohub.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.navContainer)
        binding.navBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, nd: NavDestination, _->
            Log.d("main activity", "destination listener called")
            if (nd.id != R.id.detailsFragment && nd.id != R.id.detailsFragment2) {
                binding.navBar.visibility = View.VISIBLE
            }
            else {
                binding.navBar.visibility = View.GONE
            }
        }

    }
}