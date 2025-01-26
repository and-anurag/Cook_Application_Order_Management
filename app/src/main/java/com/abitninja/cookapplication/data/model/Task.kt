package com.abitninja.cookapplication.data.model

data class Task(
    var id: String = "",
    var dishName: String = "",
    var quantity: Int = 0,
    var notes: String = "",
    var completed: Boolean = false,
    val regularMeal: Boolean = true,
    val addOn: String = ""
)
