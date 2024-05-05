package com.example.budget

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.budget.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.budgetPage.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.historyPage.setOnClickListener {
            startActivity(Intent(this,MainActivity2::class.java))
        }


    }

}