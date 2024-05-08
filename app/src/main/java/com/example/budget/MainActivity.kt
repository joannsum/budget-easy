package com.example.budget

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.budget.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import kotlin.math.abs
import android.graphics.Color
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.github.mikephil.charting.animation.Easing

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var transactions : ArrayList<Transaction> // for time
    private lateinit var categories: ArrayList<Category>
    private lateinit var categoryCounter: List<String>
    private lateinit var transactionAdapter : TransactionAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var pieChart: PieChart

    val categoryViewModel: CategoryViewModel by viewModels {
        CategoryModelFactory((application as SomeApplication).repository)
    }

    //private var totalBudget = 400.0
    private var totalAmount = 0.0

    private var budgetAmount = 0.0

    private var expenseAmount = 0.0
    private val profitValues = ArrayList<PieEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        transactions = arrayListOf(
            Transaction("Income",400.00, "Weekend Budget"),
            Transaction("Food",-20.00, "Bananas"),
            Transaction("Transportation",-40.00, "Gas"),
            Transaction("Food",-100.00,"Breakfast"),
            Transaction("Food",0.00,"Water Bottle"),
            Transaction("Other",-10.00,"Sunscreen"),
            Transaction("Transportation",0.00,"Car Wash")
        )


//        transactionAdapter = TransactionAdapter(transactions)
//        linearLayoutManager = LinearLayoutManager(this)

        //display total saved - total of all categoryitems where label != Saved
        categories = arrayListOf(
            Category("Saved",(400.00),"01/25/2012","Desc"),
            Category("Food",20.00,"01/25/2012","Desc"),
            Category("Transportation",40.00,"01/25/2012","Desc"),
            Category("Loans",100.00,"01/25/2012","Desc"),
            Category("Entertainment",10.00,"01/25/2012","Desc"),
        )
        //categories.add(Category("Saved",totalAmount.toFloat()))

        categoryAdapter = CategoryAdapter(categories)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerview2.apply {
            adapter = categoryAdapter
            layoutManager = linearLayoutManager
        }

        updateDashboard()//updates text
        setChart()

        binding.addBudget.setOnClickListener{
            val showPopUpBudget = BudPopUpFragment()
            showPopUpBudget.show(supportFragmentManager, "showPopUpBudget")
        }

        binding.addExpense.setOnClickListener{
            val showPopUpExpense = ExpPopUpFragment()
            showPopUpExpense.show(supportFragmentManager, "showPopUpExpense")
        }

        binding.historyPage.setOnClickListener{
            startActivity(Intent(this,MainActivity2::class.java))
        }

        binding.settingsPage.setOnClickListener{
            startActivity(Intent(this,MainActivity3::class.java))
        }


    }

    fun onClick(v: View?) {
        when(v?.id) {
            R.id.historyPage->{val intent1= Intent(this,MainActivity2::class.java)//replace MainActivity2 with whatever History page is named
                startActivity(intent1)}
            R.id.settingsPage->{val intent2= Intent(this,MainActivity3::class.java)//replace MainActivity3 with whatever Credit page is named
                startActivity(intent2)}
        }
    }

    fun updateCategories(){
//
    }

     fun addBudget( valueMon: Double){
         if (::transactions.isInitialized) {
             transactions.add(Transaction("Saved",valueMon.toDouble(),"GOT PAIDD"))

             categories[0].amount = valueMon

             var temp = "Categories: "
             for (thing in categories){
                 temp += (thing.label + " " + thing.amount.toString())
             }
             Toast.makeText(baseContext, temp, Toast.LENGTH_LONG).show()   //displays converted user input

         }

//         updateDashboard()
//         setChart()

    }

    private fun updateDashboard(){

        totalAmount = transactions.sumOf { it.amount }
        budgetAmount = transactions.filter { it.amount > 0 }.sumOf { it.amount }
        expenseAmount = totalAmount - budgetAmount

        binding.balance.text = "$ %.2f".format(totalAmount)
        binding.budget.text = "$ %.2f".format(budgetAmount)
        binding.expense.text = "$ %.2f".format(abs(expenseAmount))

    }
    private fun setChart(){
        for (category in categories) {
            val fraction = ((category.amount/400)*100).toFloat()
            profitValues.add(PieEntry(category.amount.toFloat(),category.label))
        }

        pieChart = binding.chart
        pieChart.setUsePercentValues(true)
        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.dragDecelerationFrictionCoef = 0.95f


        pieChart.isDrawHoleEnabled = true

        pieChart.setHoleColor(Color.BLACK)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.BLACK)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f

        pieChart.setDrawCenterText(true)


        pieChart.rotationAngle = 0f

        // enable rotation of the pieChart by touch
        pieChart.isRotationEnabled = true
        pieChart.isHighlightPerTapEnabled = true
        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

        val pieDataSetter: PieDataSet

        if(binding.chart.data != null && binding.chart.data.dataSetCount > 0) {
            pieDataSetter = binding.chart.data.getDataSetByIndex(0) as PieDataSet
            pieDataSetter.values = profitValues
            binding.chart.data.notifyDataChanged()
            binding.chart.notifyDataSetChanged()
        }
        else {
            pieDataSetter = PieDataSet(profitValues, "Data set")
            pieDataSetter.setColors(*ColorTemplate.VORDIPLOM_COLORS)
            pieDataSetter.setDrawValues(false)
            pieDataSetter.sliceSpace = 3f
            pieDataSetter.iconsOffset = MPPointF(10f,10f)
            pieDataSetter.selectionShift = 10f

            val dataSets = ArrayList<IPieDataSet>()
            dataSets.add(pieDataSetter)

            val data = PieData(pieDataSetter)
            binding.chart.data = data
            binding.chart.invalidate()
            binding.chart.description.isEnabled = false

        }
    }

}


