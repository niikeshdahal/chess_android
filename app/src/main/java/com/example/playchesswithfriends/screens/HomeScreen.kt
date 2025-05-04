package com.example.playchesswithfriends.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.playchesswithfriends.R

@Composable
fun HomeScreen(navController: NavController) {
    var playerName by rememberSaveable { mutableStateOf("") }
    var isFirstLaunch by rememberSaveable { mutableStateOf(true) }

    if (isFirstLaunch) {
        NameInputDialog(
            onNameEntered = { name ->
                playerName = name
                isFirstLaunch = false
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Top right player icon and name
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = playerName,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Image(
                painter = painterResource(id = R.drawable.piece_pawn__side_white),
                contentDescription = "Player icon",
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
        }

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(32.dp)
                .align(Alignment.Center)
        ) {
            // Rest of your existing Column content...
            Image(
                painter = painterResource(id = R.drawable.piece_knight__side_white),
                contentDescription = "App logo",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 32.dp)
            )

            Button(
                onClick = { navController.navigate("setupScreen") },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "PLAY",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { navController.navigate("setupScreen") },
                colors = ButtonDefaults.buttonColors(Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(2.dp, Color.White, RoundedCornerShape(28.dp)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "PLAY WITH FRIENDS",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            TextButton(
                onClick = { navController.navigate("settingsScreen") },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    "SETTINGS",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun NameInputDialog(onNameEntered: (String) -> Unit) {
    var name by rememberSaveable { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { /* Prevent dismissing without entering a name */ },
        title = {
            Text(
                "Enter Your Name",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Your Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray
                ),
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = { if (name.isNotBlank()) onNameEntered(name) },
                colors = ButtonDefaults.buttonColors(Color.Black),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    "CONTINUE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        containerColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    )
}