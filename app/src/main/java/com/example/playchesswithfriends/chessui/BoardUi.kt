package com.example.playchesswithfriends.chessui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.playchesswithfriends.board.Board
import com.example.playchesswithfriends.board.BoardXCoordinates
import com.example.playchesswithfriends.board.BoardYCoordinates
import com.example.playchesswithfriends.board.BoardYCoordinatesBlack
import com.example.playchesswithfriends.board.rememberIsAvailableMove
import com.example.playchesswithfriends.board.rememberPieceAt


@Composable
fun BoardUi(
    board: Board,
    modifier: Modifier = Modifier,
    selectedColor: String,
    navController: NavController,
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .border(8.dp, Color.White)
                .padding(8.dp)
        ) {
            val coordinates = if (selectedColor == "Black") {
                BoardYCoordinatesBlack
            } else {
                BoardYCoordinates
            }

            coordinates.forEach { y ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    BoardXCoordinates.forEach { x ->
                        val piece = board.rememberPieceAt(x, y)
                        val isAvailableMove = board.rememberIsAvailableMove(x, y)

                        BoardCell(
                            x = x,
                            y = y,
                            piece = piece,
                            board = board,
                            isAvailableMove = isAvailableMove,
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        )
                    }
                }
            }
        }

        // ✅ Game Over Overlay
        if (board.isGameOver) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)) // semi-transparent overlay
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF2C2C2C), shape = RoundedCornerShape(16.dp)) // dark card background
                        .padding(24.dp)
                ) {
                    Text(
                        text = "Game Over",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "${board.winner?.name ?: "Unknown"} Wins!",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {  navController.navigate("homeScreen") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Go Home",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}


