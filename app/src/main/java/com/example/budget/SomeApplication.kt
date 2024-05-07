package com.example.budget

import android.app.Application

class SomeApplication : Application(){

    private val database by lazy {
        CategoryDatabase.getDatabase(this)
    }
    val repository by lazy {
        CategoryRepository(database.categoryDao())
    }
}