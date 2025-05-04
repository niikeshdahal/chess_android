package com.example.playchesswithfriends.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.playchesswithfriends.board.CapturedPieceBlack
import com.example.playchesswithfriends.board.CapturedPieceWhite
import com.example.playchesswithfriends.board.InitialEncodedPiecesPosition
import com.example.playchesswithfriends.board.rememberBoard
import com.example.playchesswithfriends.chessui.BoardUi
import com.example.playchesswithfriends.pieces.Piece
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    navController: NavController,
    initialMinutes: Int,
    initialIncrement: Int,
    selectedColor: String,
) {
    val board = rememberBoard(initialMinutes, initialIncrement, InitialEncodedPiecesPosition)

    // Timer coroutine (same as before) …
    LaunchedEffect(key1 = board.playerTurn) {
        while (!board.isGameOver) {
            delay(1000)
            if (board.playerTurn.isWhite) {
                board.remainingTimeWhite.value = (board.remainingTimeWhite.value - 1).coerceAtLeast(0)
                if (board.remainingTimeWhite.value == 0) {
                    board.isGameOver = true
                    board.winner = Piece.Color.Black
                    break
                }
            } else {
                board.remainingTimeBlack.value = (board.remainingTimeBlack.value - 1).coerceAtLeast(0)
                if (board.remainingTimeBlack.value == 0) {
                    board.isGameOver = true
                    board.winner = Piece.Color.White
                    break
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Main content.
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    // Opponent's timer.
                    GameTimer(
                        remainingSeconds = if (selectedColor == "White")
                            board.remainingTimeBlack.value
                        else
                            board.remainingTimeWhite.value,
                    )
                    if(selectedColor == "White")
                        CapturedPiecesViewWhite(capturedPieces = board.capturedPiecesWhite)
                    else
                        CapturedPiecesViewBlack(capturedPieces = board.capturedPiecesBlack)



                    // Chess board UI.
                    BoardUi(
                        board = board,
                        selectedColor = selectedColor,
                        navController = navController
                    )
                    if(selectedColor == "White")
                        CapturedPiecesViewBlack(capturedPieces = board.capturedPiecesBlack)
                    else
                        CapturedPiecesViewWhite(capturedPieces = board.capturedPiecesWhite)

                    // Player's timer.
                    GameTimer(
                        remainingSeconds = if (selectedColor == "White")
                            board.remainingTimeWhite.value
                        else
                            board.remainingTimeBlack.value,
                    )
                }
            }

            GameControlBar(
                onDrawClick = {
                    board.isGameOver = true
                    board.winner = if (selectedColor == "White") Piece.Color.Black else Piece.Color.White
                },
                onResignClick = {
                    board.isGameOver = true
                    board.winner = Piece.Color.Black
                },
                onPreviousMove = { board.goToPreviousMove() },
                onNextMove = { board.goToNextMove() }
            )
        }
    }
}

@Composable
private fun GameTimer(
    remainingSeconds: Int,
) {
    val minutes = remainingSeconds / 60
    val seconds = remainingSeconds % 60
    Text(
        text = String.format("%d:%02d", minutes, seconds),
        color = Color.White,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(8.dp)
    )
}


@Composable
fun GameControlBar(
    onDrawClick: () -> Unit,
    onResignClick: () -> Unit,
    onPreviousMove: () -> Unit,
    onNextMove: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(bottom = 16.dp),
        color = Color(0xFF2A2A2A),
        tonalElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation buttons for move history.
            IconButton(onClick = onPreviousMove) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous move",
                    tint = Color.White
                )
            }

            IconButton(onClick = onNextMove) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next move",
                    tint = Color.White
                )
            }

            // Vertical divider.
            HorizontalDivider(
                modifier = Modifier
                    .height(24.dp)
                    .width(1.dp),
                color = Color.Gray.copy(alpha = 0.5f)
            )

            // Game control buttons.
            FilledTonalButton(
                onClick = onDrawClick,
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF404040),
                    contentColor = Color.White
                )
            ) {
                Text("Draw")
            }

            FilledTonalButton(
                onClick = onResignClick,
                modifier = Modifier.height(36.dp),
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = Color(0xFF404040),
                    contentColor = Color.White
                )
            ) {
                Text("Resign")
            }
        }
    }
}
@Composable
fun CapturedPiecesViewWhite(capturedPieces: List<CapturedPieceWhite>) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        capturedPieces.forEach { capturedPiece ->
            Image(
                painter = painterResource(id = capturedPiece.drawableRes),
                contentDescription = "Captured Piece",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
@Composable
fun CapturedPiecesViewBlack(capturedPieces: List<CapturedPieceBlack>) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        capturedPieces.forEach { capturedPiece ->
            Image(
                painter = painterResource(id = capturedPiece.drawableRes),
                contentDescription = "Captured Piece",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

