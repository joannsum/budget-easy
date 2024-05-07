package com.example.budget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {

    val categoryItems: LiveData<List<Category>> = repository.allCategory.asLiveData()
    val expenseItems: LiveData<List<Category>> = repository.allExpense.asLiveData()
    val budgetItems: LiveData<List<Category>> = repository.allSaved.asLiveData()
    val foodItems: LiveData<List<Category>> = repository.allFood.asLiveData()
    val transitItems: LiveData<List<Category>> = repository.allTransportation.asLiveData()
    val loanItems: LiveData<List<Category>> = repository.allLoans.asLiveData()
    val funItems: LiveData<List<Category>> = repository.allEntertainment.asLiveData()

    fun addCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertCategoryItem(category)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateCategoryItem(category)
        }
    }

    fun deleteCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteCategoryItem(category)
        }
    }
}



class CategoryModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



