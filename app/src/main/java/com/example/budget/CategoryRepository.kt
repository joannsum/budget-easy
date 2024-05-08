package com.example.budget

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {
    val allCategory: Flow<List<Category>> = categoryDao.getAllCategoryItems()
    val allExpense: Flow<List<Category>> = categoryDao.getAllExpenses()
    val allSaved: Flow<List<Category>> = categoryDao.getBudget()

    val allFood: Flow<List<Category>> = categoryDao.getFoodItems()
    val allTransportation: Flow<List<Category>> = categoryDao.getTransportationItems()
    val allLoans: Flow<List<Category>> = categoryDao.getLoanItems()
    val allEntertainment: Flow<List<Category>> = categoryDao.getEntertainmentItems()


    @WorkerThread
    suspend fun insertCategoryItem(category: Category)
    {
        categoryDao.insertCategoryItem(category)
    }

    @WorkerThread
    suspend fun updateCategoryItem(category: Category)
    {
        categoryDao.updateCategoryItem(category)
    }

    @WorkerThread
    suspend fun deleteCategoryItem(category: Category)
    {
        categoryDao.deleteCategoryItem(category)
    }
}