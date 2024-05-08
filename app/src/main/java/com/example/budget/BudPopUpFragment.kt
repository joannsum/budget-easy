package com.example.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.budget.databinding.FragmentBudPopUpBinding
import kotlin.math.abs

class BudPopUpFragment : DialogFragment() { //Bud stands for 'budget'

    private lateinit var binding: FragmentBudPopUpBinding
    var mainActivity = MainActivity()
    private lateinit var categoryViewModel:CategoryViewModel

    var inputAmount: String = ""
    var inputDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog); //so we can set up the rounded theme + animation

    }

    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?, savedInstanceState: Bundle?):View?{
        binding = FragmentBudPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    //this creates actions on what happens when you actually click the buttons
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditText = view.findViewById<Button>(R.id.btn_bud_buttonEditText)
        val editTextMoney = view.findViewById<EditText>(R.id.et_bud_moneyInput)
        val editDate = binding.etBudDate
        val imgClose : ImageView = view.findViewById(R.id.bud_imgClose)

        val application = requireActivity().application as? SomeApplication
        val repository = application?.repository
        if (repository == null) {
            throw IllegalStateException("Unable to retrieve repository from application") //check in case of error
        }
        categoryViewModel = ViewModelProvider(requireActivity(), CategoryModelFactory(repository)).get(CategoryViewModel::class.java)



        btnEditText.setOnClickListener{
            val valueMon = editTextMoney.text.toString()
            inputDate = editDate.text.toString() //date


            /* length_long displays text for about 3.5 secs */
            if(valueMon.isEmpty()){
                Toast.makeText(context, "Please fill in the cost input first", Toast.LENGTH_LONG).show()
            }
            else if(inputDate.isEmpty()) {
                Toast.makeText(context, "Please fill in the date first", Toast.LENGTH_LONG)
                    .show()
            } else if(inputDate.length <= 9) {
                Toast.makeText(context, "Please rewrite your date (Ex. 06/07/2004)", Toast.LENGTH_LONG)
                    .show()
            }
            else {
                try {
                    var connectedString = "$" + valueMon + " " + inputDate

                    Toast.makeText(context, connectedString, Toast.LENGTH_LONG).show()   //displays converted user input
                    insertDataToDatabase("Saved",valueMon.toDouble(),inputDate,"Increase budget")

//                    mainActivity.updateDashboard()

                } catch (nfe: NumberFormatException) {
                    println("YOU FAILED IN LIFE and to parse here $nfe")
                }

                  //inputDesc not available???
                dismiss()
            }
        }

        //closes dialog fragment
        imgClose.setOnClickListener{
            dismiss()
        }
    }

    private fun insertDataToDatabase(chosenItem:String, inputAmount:Double, inputDate:String, inputDesc: String){
        val categoryItem = Category(chosenItem,inputAmount,inputDate,inputDesc)
        categoryViewModel.addCategory(categoryItem)

        Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_LONG).show()
    }
}