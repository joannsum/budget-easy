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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import java.nio.charset.Charset

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private lateinit var recyclerView:RecyclerView
    var titleList= arrayListOf<String>()
    var detailList= arrayListOf<String>()
    var dateList= arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView=findViewById(R.id.recyclerView)
        recyclerView.layoutManager=LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        binding.budgetPage.setOnClickListener{
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.settingsPage.setOnClickListener{
            startActivity(Intent(this,MainActivity3::class.java))
        }

        try{
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("Categories")
            for (i in 0 until userArray.length()) {
                val categories=userArray.getJSONObject(i)
                titleList.add(categories.getString("title"))
                detailList.add(categories.getString("detail"))
                dateList.add(categories.getString("date"))
            }
        }
        catch(e:JSONException){
            e.printStackTrace()
        }
        val customAdapter=CustomAdapter(this@MainActivity2,titleList,detailList,dateList)
        recyclerView.adapter=customAdapter

    }

    private fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("History.json")//replace with name of json you use
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        }
        catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }


}

