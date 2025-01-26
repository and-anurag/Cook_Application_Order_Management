package com.abitninja.cookapplication.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.abitninja.cookapplication.data.model.Task


@Composable
fun TaskItem(
    task: Task,
    onDetailsClicked: () -> Unit,
    onMarkComplete: () -> Unit
) {

    val isExpanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                text = task.dishName
            )

            Text(
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                text = "Quantity: ${task.quantity}"
            )

            if (isExpanded.value) {
                if (task.notes.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = "Notes: ${task.notes}"
                    )
                }
                else{
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = "Notes: None"
                    )
                }

                if (task.addOn.isNotEmpty()) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = "Add-on: ${task.addOn}"
                    )
                }
                else {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = "Add-on: None"
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onDetailsClicked()
                    isExpanded.value = !isExpanded.value
                }
            ) {
                Text(
                    text = if (isExpanded.value) "Hide Details" else "Show Details"
                )
            }

            if (!task.completed) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onMarkComplete
                ) {
                    Text("Mark Complete")
                }
            }

        }
    }
}