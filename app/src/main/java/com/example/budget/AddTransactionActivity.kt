package com.example.budget

import androidx.appcompat.app.AppCompatActivity
import com.example.budget.databinding.ActivityAddTransactionBinding
import android.os.Bundle
import androidx.core.widget.addTextChangedListener

class AddTransactionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_add_transaction)
        val label = binding.labelInput.text.toString()
        val amount = binding.amountInput.text.toString().toDoubleOrNull()


        binding.labelInput.addTextChangedListener {
            if (label.isNotEmpty())
                binding.labelLayout.error = null
        }
        binding.amountInput.addTextChangedListener {
            if (amount != null)
                binding.amountLayout.error = null
        }

        binding.addTransactionBtn.setOnClickListener {
            if(label.isEmpty())
                binding.labelLayout.error = "Please enter a valid label"
            else if(amount==null)
                binding.amountLayout.error = "Please enter a valid amount"
            else{
                this.finish()
            }
        }

        binding.closeBtn.setOnClickListener{
            this.finish()
        }
    }
}