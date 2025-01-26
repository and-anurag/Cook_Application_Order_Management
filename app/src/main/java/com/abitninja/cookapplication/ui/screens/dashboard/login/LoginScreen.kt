package com.abitninja.cookapplication.ui.screens.dashboard.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {

    val loginViewModel = remember { LoginViewModel() }
    val loginState by loginViewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("admin@gmail.com") }
    var password by remember { mutableStateOf("admin@123") }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Text(
            text = "Login",
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
        )

        Box(modifier = Modifier.fillMaxWidth()) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))

            }

        }




        when (loginState) {
            is LoginState.Loading -> LinearProgressIndicator()
            is LoginState.Error -> {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (loginState as LoginState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            is LoginState.Success -> {
                onLoginSuccess()
            }
            else -> Unit
        }

        Button(
            onClick = { loginViewModel.login(email, password) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "Login"
            )
        }
    }

}
