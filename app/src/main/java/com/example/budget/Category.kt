package com.example.budget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="category_table")
data class Category(
    @ColumnInfo(name = "label") var label:String,
    @ColumnInfo(name = "amount") var amount:Double,
    @ColumnInfo(name = "date") var date:String,
    @ColumnInfo(name = "description") var description:String,
    @PrimaryKey(autoGenerate = true) var id:Int = 0)
{
}
