package com.example.budget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class ExpPopUpFragment : DialogFragment() { //Exp stands for 'expense'

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog); //so we can set up the rounded theme + animation
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exp_pop_up, container, false)
    }


    var chosenItem : String = "" //for spinner
    var expArray: Array<String> = emptyArray()
    var monArray: Array<Float> = emptyArray()
    var dateArray: Array<String> = emptyArray()
    //this creates actions on what happens when you actually click the buttons
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditText = view.findViewById<Button>(R.id.btn_exp_buttonEditText)
        val editTextExpense = view.findViewById<EditText>(R.id.et_exp_expense) //stores the edited text? would want to keep this in mind for project revisions
        val editTextMoney = view.findViewById<EditText>(R.id.et_exp_moneyInput)
        val editDate = view.findViewById<EditText>(R.id.et_exp_date)
        val imgClose : ImageView = view.findViewById(R.id.exp_imgClose)


        //beginning of dropdown/spinner code
        // access the items of the list
        val languages = resources.getStringArray(R.array.Languages)
        // access the spinner
        val spinner = view.findViewById<Spinner>(R.id.exp_spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    chosenItem = languages[position]                            //used to display later w/ add button
//                            Toast.makeText(
//                                context,
//                                getString(R.string.selected_item) + " " +
//                                        "" + languages[position], Toast.LENGTH_SHORT
//                            ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        //end of dropdown/spinner code


        btnEditText.setOnClickListener{
            val valueExp = editTextExpense.text.toString() //actually takes in the user input and converts to a string
            val valueMon = editTextMoney.text.toString()
            val valueDate = editDate.text.toString()

            expArray += valueExp
            try {
                monArray += valueMon.toFloat()
            } catch (nfe: NumberFormatException) {
                println("Could not parse $nfe")
            }
            dateArray += valueDate

            /* length_long displays text for about 3.5 secs */
            if(valueExp.isEmpty() && valueMon.isEmpty() && valueDate.isEmpty()) {
                Toast.makeText(context, "Please fill in the blanks first", Toast.LENGTH_LONG)
                    .show()
            } else if(valueExp.isEmpty()) {
                Toast.makeText(context, "Please fill in the expense input first", Toast.LENGTH_LONG).show()
            } else if(valueMon.isEmpty()){
                Toast.makeText(context, "Please fill in the cost input first", Toast.LENGTH_LONG).show()
            }
//                else if(valueMon.subSequence(valueMon.length - 2, valueMon.length).length < 1){                   //try to account for full decimal input (6.00 not 6)
//                    Toast.makeText(context, "Please rewrite your datte (Ex. 6.00)", Toast.LENGTH_LONG).show()
//                }
            else if(valueDate.isEmpty()) {
                Toast.makeText(context, "Please fill in the date first", Toast.LENGTH_LONG)
                    .show()
            } else if(valueDate.length <= 9) {
                Toast.makeText(context, "Please rewrite your date (Ex. 06/07/2004)", Toast.LENGTH_LONG)
                    .show()
            }
            else {
                val connectedString = valueExp + " $" + valueMon + " " + valueDate + " Chosen category: " + chosenItem
                Toast.makeText(context, connectedString, Toast.LENGTH_LONG).show()   //displays converted user input
                dismiss()
            }
        }

        //closes dialog fragment
        imgClose.setOnClickListener{
            dismiss()
        }
    }
}