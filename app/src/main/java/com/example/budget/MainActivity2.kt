package com.example.budget

import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.budget.databinding.ActivityMain2Binding

import android.view.View

import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import java.nio.charset.Charset
import kotlin.math.abs

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var recyclerView:RecyclerView
    private var titleList= arrayListOf<String>()
    private var detailList= arrayListOf<String>()
    private var dateList= arrayListOf<String>()
    private var descriptionList = arrayListOf<String>()
    private lateinit var customAdapter: CustomAdapter


    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryModelFactory((application as SomeApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //linearLayoutManager = LinearLayoutManager(this)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView

        setRecyclerView()

        binding.budgetPage.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.settingsPage.setOnClickListener{
            startActivity(Intent(this,MainActivity3::class.java))
        }

        try{
            updateHistoryLog()
        }
        catch(e:JSONException){
            e.printStackTrace()
        }
        customAdapter=CustomAdapter(this@MainActivity2,titleList,detailList,dateList,descriptionList)
        recyclerView.adapter=customAdapter

    }

    private fun setRecyclerView()
    {
        val mainActivity = this
        categoryViewModel.categoryItems.observe(this){
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = customAdapter
            }
        }
    }

    private fun updateHistoryLog(){
        categoryViewModel.categoryItems.observe(this) { list ->
            for (item in list){
                titleList.add(item.label)
                detailList.add(item.amount.toString())
                dateList.add(item.date)
                descriptionList.add(item.description)
            }
        }
    }

//    private fun updateHistoryLog(){
//        val obj = JSONObject(loadJSONFromAsset())
//        val userArray = obj.getJSONArray("Categories")
//        for (i in 0 until userArray.length()) {
//            val categories=userArray.getJSONObject(i)
//            titleList.add(categories.getString("label"))
//            detailList.add(categories.getString("amount"))
//            dateList.add(categories.getString("date"))
//            descriptionList.add(categories.getString("details"))
//
//        }
//    }

//    private fun loadJSONFromAsset(): String {
//        val json: String?
//        try {
//            val inputStream = assets.open("History.json")//replace with name of json you use
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//            val charset: Charset = Charsets.UTF_8
//            inputStream.read(buffer)
//            inputStream.close()
//            json = String(buffer, charset)
//        }
//        catch (ex: IOException) {
//            ex.printStackTrace()
//            return ""
//        }
//        return json
//    }


}

