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

class BudPopUpFragment : DialogFragment() { //Bud stands for 'budget'
    var mainActivity = MainActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.dialog); //so we can set up the rounded theme + animation

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bud_pop_up, container, false)
    }

    //this creates actions on what happens when you actually click the buttons
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnEditText = view.findViewById<Button>(R.id.btn_bud_buttonEditText)
        val editTextMoney = view.findViewById<EditText>(R.id.et_bud_moneyInput)
        val imgClose : ImageView = view.findViewById(R.id.bud_imgClose)


        btnEditText.setOnClickListener{
            val valueMon = editTextMoney.text.toString()



            /* length_long displays text for about 3.5 secs */
            if(valueMon.isEmpty()){
                Toast.makeText(context, "Please fill in the cost input first", Toast.LENGTH_LONG).show()
            }
            else {
                try {
                    mainActivity.addBudget(valueMon.toDouble())
                } catch (nfe: NumberFormatException) {
                    println("Could not parse $nfe")
                }

                var connectedString = "$" + valueMon
//                for (item in mainActivity.transactions){
//                    connectedString += item.amount.toString()
//                }
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