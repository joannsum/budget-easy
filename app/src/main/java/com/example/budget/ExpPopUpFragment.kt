package com.example.budget

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.budget.databinding.FragmentExpPopUpBinding



class ExpPopUpFragment : DialogFragment() { //Exp stands for 'expense'

    private lateinit var binding: FragmentExpPopUpBinding
    private lateinit var categoryViewModel:CategoryViewModel

    var chosenItem : String = "" //Category for spinner
    //    var expArray: Array<String> = emptyArray()
//    var monArray: Array<Float> = emptyArray()
//    var dateArray: Array<String> = emptyArray()
    var inputAmount: String = ""
    var inputDate: String = ""
    var inputDesc: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog); //so we can set up the rounded theme + animation
    }

    override fun onCreateView(inflater:LayoutInflater,container:ViewGroup?, savedInstanceState: Bundle?):View?{
        binding = FragmentExpPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    //this creates actions on what happens when you actually click the buttons
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        val btnEditText = binding.btnExpButtonEditText
        val editTextExpense = binding.etExpExpense //stores the edited text? would want to keep this in mind for project revisions
        val editTextMoney = binding.etExpMoneyInput
        val editDate = binding.etExpDate
        val imgClose : ImageView = binding.expImgClose


        //beginning of dropdown/spinner code
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)
        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.exp_spinner)
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, languages)
        spinner.adapter = adapter

        if (spinner != null) {
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View,
                                            position: Int,
                                            id: Long) {
                    chosenItem = languages[position]                            //used to display later w/ add button
//
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                    Toast.makeText(context,"No category selected",Toast.LENGTH_LONG).show()
                }
            }
        }
        //end of dropdown/spinner code

        val application = requireActivity().application as? SomeApplication ?: throw IllegalStateException("Activity application is null or not an instance of SomeApplication")
        val repository = application?.repository
        if (repository == null) {
            throw IllegalStateException("Unable to retrieve repository from application") //check in case of error
        }
        categoryViewModel = ViewModelProvider(requireActivity(), CategoryModelFactory(repository)).get(CategoryViewModel::class.java)



        btnEditText.setOnClickListener{
            inputAmount = editTextMoney.text.toString() //actually takes in the user input and converts to a string
            inputDate = editDate.text.toString() //date
            inputDesc = editTextExpense.text.toString() //description

            try {
                inputAmount.toFloat()
            } catch (nfe: NumberFormatException) {
                println("Could not parse $nfe")
            }

            /* length_long displays text for about 3.5 secs */
            if(inputAmount.isEmpty() && inputDate.isEmpty() && inputDesc.isEmpty()) {
                Toast.makeText(context, "Please fill in the blanks first", Toast.LENGTH_LONG)
                    .show()
            } else if(inputDesc.isEmpty()) {
                Toast.makeText(context, "Please fill in the expense input first", Toast.LENGTH_LONG).show()
            } else if(inputAmount.isEmpty()){
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
                val connectedString = inputDesc + " $" + inputAmount + " " + inputDate + " Chosen category: " + chosenItem
                Toast.makeText(context, connectedString, Toast.LENGTH_LONG).show()   //displays converted user input
                insertDataToDatabase(chosenItem,inputAmount.toDouble(),inputDate,inputDesc)

                //navigate back
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