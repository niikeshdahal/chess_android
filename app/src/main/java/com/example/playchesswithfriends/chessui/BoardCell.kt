package com.example.playchesswithfriends.chessui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import com.example.playchesswithfriends.board.Board
import com.example.playchesswithfriends.pieces.Piece
import com.example.playchesswithfriends.ui.theme.ActiveColor
import com.example.playchesswithfriends.ui.theme.DarkColor
import com.example.playchesswithfriends.ui.theme.LightColor
import  androidx.compose.ui.Modifier
import com.example.playchesswithfriends.board.BoardXCoordinates

@Composable
fun BoardCell(
    x: Int,
    y: Int,
    piece: Piece?,
    board: Board,
    isAvailableMove: Boolean = false,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        when {
            piece != null && piece == board.selectedPiece ->
                ActiveColor

            (x + y) % 2 == 0 ->
                DarkColor

            else ->
                LightColor
        }

    val textColor =
        when {
            piece != null && piece == board.selectedPiece ->
                Color.White

            /*(x + y) % 2 == 0 ->
                LightColor

            else ->
                DarkColor*/
            (x + y) % 2 == 0 -> Color(0xFFD0D0D0) // Light gray on dark squares
            else -> Color(0xFF303030) // Dark gray on light squares
        }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = backgroundColor
            )
    ) {
        if (x == BoardXCoordinates.first()) {
            // draw y text
            Text(
                text = y.toString(),
                color = textColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(3.dp)
            )
        }

        if (y == 1) {
            // draw x text
            Text(
                text = x.toChar().toString(),
                color = textColor,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(3.dp)
            )
        }

        piece?.let {
            Image(
                painter = painterResource(it.drawable),
                contentDescription = null,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        board.selectPiece(it)
                    }
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }

        if (isAvailableMove)
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                    ) {
                        board.moveSelectedPiece(x, y)
                    }
                    .drawBehind {
                        drawCircle(
                            color = ActiveColor,
                            radius = size.width / 6,
                            center = center,
                        )
                    }
            )
    }
}
