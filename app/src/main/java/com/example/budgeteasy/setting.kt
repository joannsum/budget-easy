package com.example.budgeteasy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class setting : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        var history=findViewById<Button>(R.id.history)
        var settings=findViewById<Button>(R.id.settings)
        var budget=findViewById<Button>(R.id.budget)
        history.setOnClickListener(this)
        settings.setOnClickListener(this)
        budget.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.history->{val intent1= Intent(this,history::class.java)
                startActivity(intent1)}
            R.id.budget->{val intent2= Intent(this,Budget::class.java)
                startActivity(intent2)}
        }
    }
}