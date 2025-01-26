package com.abitninja.cookapplication.ui.screens.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abitninja.cookapplication.data.model.Task
import com.abitninja.cookapplication.data.repository.TaskRepository
import com.abitninja.cookapplication.ui.components.TaskItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import androidx.navigation.navOptions


@Composable
fun DashboardScreen(
    onTaskClicked: (Task) -> Unit,
    modifier: Modifier = Modifier.statusBarsPadding(),
    navController: NavController
) {

    val viewModel: DashboardViewModel = remember { DashboardViewModel(TaskRepository()) }
    val tasks = viewModel.tasks.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val ordersInProgress = tasks.filter { !it.completed }
    val completedOrders = tasks.filter { it.completed }
    val repo = TaskRepository()
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when {
            isLoading -> {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth()
                )
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage
                )
            }
            else -> {
                LazyColumn {

                    item {

                        Text(
                            modifier = Modifier.padding(start = 8.dp),
                            text = "Welcome Anurag Singh",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Daily Task Summary",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        DailyTaskSummary(tasks = tasks)
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Add on requests",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        AddOnRequests(tasks = tasks)
                    }



                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Orders List",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        OrderList(ordersInProgress = ordersInProgress, onTaskClicked = onTaskClicked, viewModel = viewModel)
                    }

                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            text = "Completed Orders",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    items(completedOrders.size) {
                        OrderHistory(task = completedOrders[it])
                    }


                    item {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                modifier = Modifier.padding(8.dp),
                                onClick = {
                                    scope.launch {
                                        repo.addTask(
                                            Task(
                                                id = "1",
                                                dishName = "Butter Chicken Burger",
                                                quantity = 1,
                                                notes = "Extra mayo",
                                                completed = false,
                                                regularMeal = true,
                                                addOn = "" // No add-on for regular meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "2",
                                                dishName = "Paneer Wrap",
                                                quantity = 2,
                                                notes = "Spicy sauce",
                                                completed = true,
                                                regularMeal = true,
                                                addOn = "" // No add-on for regular meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "3",
                                                dishName = "Masala Fries Combo",
                                                quantity = 1,
                                                notes = "With extra fries",
                                                completed = false,
                                                regularMeal = false,
                                                addOn = "Coke" // Add-on included in combo meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "4",
                                                dishName = "Tandoori Chicken Sub",
                                                quantity = 1,
                                                notes = "No onions",
                                                completed = true,
                                                regularMeal = true,
                                                addOn = "" // No add-on for regular meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "5",
                                                dishName = "Rajma Rice Bowl",
                                                quantity = 1,
                                                notes = "Less salt",
                                                completed = false,
                                                regularMeal = true,
                                                addOn = "" // No add-on for regular meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "6",
                                                dishName = "Aloo Tikki Burger Combo",
                                                quantity = 1,
                                                notes = "Extra ketchup",
                                                completed = false,
                                                regularMeal = false,
                                                addOn = "Pepsi" // Add-on included in combo meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "7",
                                                dishName = "Veggie Pizza Puff",
                                                quantity = 3,
                                                notes = "Serve hot",
                                                completed = false,
                                                regularMeal = true,
                                                addOn = "" // No add-on for regular meal
                                            )
                                        )
                                        repo.addTask(
                                            Task(
                                                id = "8",
                                                dishName = "Chicken Popcorn Meal",
                                                quantity = 1,
                                                notes = "Spicy",
                                                completed = true,
                                                regularMeal = false,
                                                addOn = "Sprite" // Add-on included in combo meal
                                            )
                                        )
                                        viewModel.loadTasks()
                                    }
                                }
                            ) {

                                Text(text = "Add dummy data to firebase")

                            }

                            Button(
                                modifier = Modifier.padding(8.dp),
                                onClick = {
                                    FirebaseAuth.getInstance().signOut()
                                    navController.navigate("login") {
                                        popUpTo("dashboard") { inclusive = true }
                                    }

                                }
                            ) {
                                
                                Text(text = "Logout")

                            }


                        }

                        Spacer(modifier = Modifier.height(12.dp))


                    }

                }
            }
        }

    }

}

@Composable
fun OrderHistory(task: Task) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = task.dishName
            )

            if(task.addOn != "") {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = "Add-on: ${task.addOn}",
                    fontStyle = FontStyle.Italic
                )
            }

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Qty: ${task.quantity}"
            )

        }

    }

}


@Composable
fun AddOnRequests(tasks: List<Task>) {

    val addOnTasks = tasks.filter { !it.regularMeal && !it.completed}

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

            Text(
                modifier = Modifier.padding(8.dp),
                text = "Add-on requests for orders in progress",
                fontSize = 16.sp
            )

            LazyRow {

                items(addOnTasks.size) {
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(200.dp)
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {

                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            text = "Dish: ${addOnTasks[it].dishName}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            text = "Add-on: ${addOnTasks[it].addOn}",
                            fontSize = 14.sp
                        )

                        Text(
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                            text = "Quantity: ${addOnTasks[it].quantity}",
                            fontSize = 14.sp
                        )


                    }
                }

            }


    }

}


@Composable
fun DailyTaskSummary(tasks: List<Task>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier
                    .weight(0.65f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Total tasks today: ${tasks.size}",
                )

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Completed Orders today: ${tasks.filter { it.completed }.size}"
                )

                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Pending Orders today: ${tasks.filter { !it.completed }.size}"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(color = Color(0xFF9B86BD))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Completed",
                        fontSize = 12.sp

                    )

                    Spacer(modifier = Modifier.width(20.dp))

                    Canvas(modifier = Modifier.size(20.dp)) {
                        drawCircle(color = Color(0xFFC06C84))
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = "Pending",
                        fontSize = 12.sp
                    )

                }

            }

            Column(
                modifier = Modifier.weight(0.35f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                PieChart(
                    pending = tasks.filter { !it.completed }.size.toFloat(),
                    total = tasks.size.toFloat()
                )


            }

        }

    }

}


@Composable
fun PieChart(pending: Float, total: Float) {
    // Validate inputs
    if (total <= 0f || pending < 0f || pending > total) return

    val completed = total - pending
    val completedAngle = (completed / total) * 360f
    val pendingAngle = (pending / total) * 360f

    Canvas(
        modifier = Modifier
            .size(120.dp)
            .padding(16.dp)
    ) {
        drawArcSection(
            color = Color(0xFF9B86BD), // Completed portion
            startAngle = 0f,
            sweepAngle = completedAngle
        )
        drawArcSection(
            color = Color(0xFFC06C84), // Pending portion
            startAngle = completedAngle,
            sweepAngle = pendingAngle
        )
    }
}

private fun DrawScope.drawArcSection(
    color: Color,
    startAngle: Float,
    sweepAngle: Float
) {
    drawArc(
        color = color,
        startAngle = startAngle,
        sweepAngle = sweepAngle,
        useCenter = true, // Filled arc (pie slice)
        size = Size(size.width, size.height),
        topLeft = Offset(0f, 0f)
    )
}


@Composable
fun OrderList(ordersInProgress: List<Task>, onTaskClicked: (Task) -> Unit, viewModel: DashboardViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Text(
            modifier = Modifier.padding(8.dp),
            text = "Orders in progress",
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic
        )

        LazyRow {
            items(ordersInProgress.size) {index ->
                val task = ordersInProgress[index]
                TaskItem(
                    task = task,
                    onDetailsClicked = { onTaskClicked(task) },
                    onMarkComplete = { viewModel.markTaskAsCompleted(task.id) }
                )
            }
        }

    }

}








