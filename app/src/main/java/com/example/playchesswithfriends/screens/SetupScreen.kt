package com.example.playchesswithfriends.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.playchesswithfriends.board.Time

data class Time(
    val time: Int,
    val increment: Int? = null
)

@Composable
fun SetupScreen(navController: NavController) {
    var selectedColor by remember { mutableStateOf("White") }
    // Default selected time: 1 minute (no increment)
    var selectedTimeControl by remember { mutableStateOf(Time(1)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // Color Selection Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "PIECE COLOR",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    PieceColorButton(
                        color = "White",
                        selectedColor = selectedColor,
                        modifier = Modifier.weight(1f)
                    ) { selectedColor = "White" }

                    PieceColorButton(
                        color = "Black",
                        selectedColor = selectedColor,
                        modifier = Modifier.weight(1f)
                    ) { selectedColor = "Black" }
                }
            }

            // Time Control Section
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // BULLET
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "BULLET",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimeControlButton(
                            time = Time(1),
                            description = "1 min",
                            isSelected = selectedTimeControl == Time(1),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(1) }

                        TimeControlButton(
                            time = Time(1, 1),
                            description = "1 min +1s",
                            isSelected = selectedTimeControl == Time(1, 1),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(1, 1) }

                        TimeControlButton(
                            time = Time(2, 1),
                            description = "2 min +1s",
                            isSelected = selectedTimeControl == Time(2, 1),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(2, 1) }
                    }
                }

                // BLITZ
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "BLITZ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimeControlButton(
                            time = Time(3),
                            description = "3 min",
                            isSelected = selectedTimeControl == Time(3),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(3) }

                        TimeControlButton(
                            time = Time(3, 2),
                            description = "3 min +2s",
                            isSelected = selectedTimeControl == Time(3, 2),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(3, 2) }

                        TimeControlButton(
                            time = Time(5),
                            description = "5 min",
                            isSelected = selectedTimeControl == Time(5),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(5) }
                    }
                }

                // RAPID
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "RAPID",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.7f),
                        letterSpacing = 1.sp
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TimeControlButton(
                            time = Time(10),
                            description = "10 min",
                            isSelected = selectedTimeControl == Time(10),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(10) }

                        TimeControlButton(
                            time = Time(15, 10),
                            description = "15 min +10s",
                            isSelected = selectedTimeControl == Time(15, 10),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(15, 10) }

                        TimeControlButton(
                            time = Time(30),
                            description = "30 min",
                            isSelected = selectedTimeControl == Time(30),
                            modifier = Modifier.weight(1f)
                        ) { selectedTimeControl = Time(30) }
                    }
                }
            }

            // Play Button: navigate to gameScreen, encoding the selected time as "minutes,increment"
            Button(
                onClick = {
                    val timeArg = "${selectedTimeControl.time},${selectedTimeControl.increment ?: 0}"
                    navController.navigate("gameScreen/$timeArg/$selectedColor")
                },
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    "START GAME",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun TimeControlButton(
    time: Time,
    description: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    // Create a text representation for display (e.g. "1" or "1|1")
    val timeText = time.increment?.let { "${time.time}|${it}" } ?: "${time.time}"
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.White else Color.Black
        ),
        modifier = modifier
            .height(64.dp)
            .then(
                if (!isSelected)
                    Modifier.border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = timeText,
                color = if (isSelected) Color.Black else Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                color = if (isSelected) Color.Black.copy(alpha = 0.7f)
                else Color.White.copy(alpha = 0.7f),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun PieceColorButton(
    color: String,
    selectedColor: String,
    modifier: Modifier = Modifier,
    onSelect: () -> Unit
) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedColor == color) Color.White else Color.Black
        ),
        modifier = modifier
            .height(48.dp)
            .then(
                if (selectedColor != color)
                    Modifier.border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                else Modifier
            ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = color,
            color = if (selectedColor == color) Color.Black else Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



