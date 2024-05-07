package com.example.budget

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao{
    @Query("SELECT * FROM category_table")
    fun getAllCategoryItems(): Flow<List<Category>>

    //all category items that are expenses.
    @Query("SELECT * FROM category_table WHERE label != 'Saved'")
    fun getAllExpenses(): Flow<List<Category>>

    @Query("SELECT * FROM category_table WHERE label = 'Food'")
    fun getFoodItems(): Flow<List<Category>>

    @Query("SELECT * FROM category_table WHERE label = 'Transportation'")
    fun getTransportationItems(): Flow<List<Category>>

    @Query("SELECT * FROM category_table WHERE label = 'Loans'")
    fun getLoanItems(): Flow<List<Category>>

    @Query("SELECT * FROM category_table WHERE label = 'Entertainment'")
    fun getEntertainmentItems(): Flow<List<Category>>

    @Query("SELECT * FROM category_table WHERE label = 'Saved'")
    fun getBudget(): Flow<List<Category>>


    @Insert
    suspend fun insertCategoryItem(category: Category)
    @Update
    suspend fun updateCategoryItem(category: Category)

    @Delete
    suspend fun deleteCategoryItem(category: Category)
}