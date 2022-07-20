package com.example.loginlogout.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.loginlogout.navigation.Screen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    navController: NavController
) {
    val openDialog = remember { mutableStateOf(false) }
    val currentUser =
        if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
            FirebaseAuth.getInstance().currentUser?.email?.split('@')?.get(0)
        else
            "N/A"

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Hello, ${currentUser.toString()}",
                fontSize = 30.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )

            Spacer(modifier = Modifier.padding(bottom = 20.dp))
            
            IconButton(
                onClick = {
                    openDialog.value = true
                }
            ) {
                Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout Icon")
            }

            if(openDialog.value) {
                AlertDialog(
                    onDismissRequest = {  openDialog.value = false },
                    title = { Text(text = "Dialog Title")},
                    text = { Text(text = "Are you sure you want to log out?")},
                    confirmButton = {
                        Button(onClick = {
                            openDialog.value = false
                            FirebaseAuth.getInstance().signOut().run {
                                navController.navigate(Screen.Login.route)
                            }
                        }) {
                            Text(text = "Yes")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            openDialog.value = false
                        }) {
                            Text(text = "No")
                        }
                    }
                )
            }
        }
    }
}