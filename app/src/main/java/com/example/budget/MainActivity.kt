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

        categories = arrayListOf(
            Category("Saved",1.00,"01/25/2012","Desc"),
            Category("Food",0.00,"01/25/2012","Desc"),
            Category("Transportation",0.00,"01/25/2012","Desc"),
            Category("Loans",0.00,"01/25/2012","Desc"),
            Category("Entertainment",0.00,"01/25/2012","Desc"))


        categoryAdapter = CategoryAdapter(categories)
        linearLayoutManager = LinearLayoutManager(this)

        setRecyclerView()

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

    private fun setRecyclerView()
    {
        val mainActivity = this
        categoryViewModel.categoryItems.observe(this){
            binding.recyclerview2.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = categoryAdapter
            }
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

    private fun updateDashboard(){

        categoryViewModel.categoryItems.observe(this) { list ->
            totalAmount = categoryViewModel.calculateTotalAmount(list) // Assuming you have a function to calculate total
            budgetAmount = categoryViewModel.calculateTotalBudget(list)
            expenseAmount = categoryViewModel.calculateTotalExpense(list)

            binding.balance.text = "$ %.2f".format(totalAmount)
            binding.budget.text = "$ %.2f".format(budgetAmount)
            binding.expense.text = "$ %.2f".format(abs(expenseAmount))
        }



    }
    private fun setChart(){

        categoryViewModel.categoryItems.observe(this) { list ->
            //saved
            var tooBig = false
            val temp = categoryViewModel.calculateTotalBudget(list) - categoryViewModel.calculateTotalExpense(list)
            if (temp > categoryViewModel.calculateTotalBudget(list)){
                categories[0].amount = -(temp)
                tooBig = true
            }
            else{
                categories[0].amount = temp
            }

            //food
            categories[1].amount = categoryViewModel.calculateFoodTotal(list)

            //transit
            categories[2].amount = categoryViewModel.calculateTransTotal(list)

            //loans
            categories[3].amount = categoryViewModel.calculateLoanTotal(list)

            //entertainment
            categories[4].amount = categoryViewModel.calculateFunTotal(list)

            profitValues.clear()

            for (category in categories){
                if (category.label == "Saved" && tooBig){
                    //do nothin
                } else {
                    profitValues.add(PieEntry(category.amount.toFloat(),category.label))
                }
            }

            setUpChart()

        }





    }

    private fun setUpChart(){
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


