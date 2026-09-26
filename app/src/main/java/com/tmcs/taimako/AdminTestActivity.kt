package com.tmcs.taimako

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

class AdminTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { AdminTestScreen() } }
    }
}

@Composable
private fun AdminTestScreen() {
    var username by remember { mutableStateOf("Shugaba") }
    var password by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("") }
    var verified by remember { mutableStateOf(false) }
    var busy by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize().padding(22.dp)) {
        Spacer(Modifier.height(35.dp))
        Text("TAIMAKO ADMIN LOGIN", style = MaterialTheme.typography.headlineMedium)
        Text("Backend verification checkpoint. Financial transactions are disabled.")
        Spacer(Modifier.height(20.dp))
        if (!verified) {
            OutlinedTextField(username, { username = it }, label = { Text("Username") },
                singleLine = true, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(password, { password = it }, label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(), singleLine = true,
                modifier = Modifier.fillMaxWidth())
            Button(enabled = !busy && username.isNotBlank() && password.isNotBlank(),
                onClick = {
                    val entered = password
                    password = ""
                    busy = true
                    status = "Checking..."
                    scope.launch {
                        try {
                            val (code, result) = adminLogin(username, entered)
                            if (code == 200 && result.optString("role") == "ADMIN" &&
                                !result.optBoolean("mustChangeCredential", true)) {
                                verified = true
                                status = "Shugaba authenticated successfully."
                            } else if (code == 200 && result.optBoolean("mustChangeCredential")) {
                                status = "Password change required. Use the secure browser login page."
                            } else {
                                status = "Login unsuccessful (HTTP $code)."
                            }
                        } catch (_: Exception) { status = "Connection error. Check internet access." }
                        finally { busy = false }
                    }
                }) { Text("VERIFY ADMIN LOGIN") }
        } else {
            Text("ADMIN DASHBOARD", style = MaterialTheme.typography.headlineMedium)
            Text("Authentication verified. Admin operations are not yet enabled.")
            Button(onClick = { verified = false; status = "Logged out." }) { Text("LOG OUT") }
        }
        Text(status)
    }
}
