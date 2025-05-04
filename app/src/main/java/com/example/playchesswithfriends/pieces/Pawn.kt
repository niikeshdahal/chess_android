package com.example.playchesswithfriends.pieces

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.pieces.dsl.getPieceMoves

class Pawn(
    override val color: Color,
    override var position: IntOffset
): Piece() {

    override val type: Char = Type

    override val drawable: Int =
        if(color.isWhite)
            R.drawable.piece_pawn__side_white
        else
            R.drawable.piece_pawn__side_black

    override fun copy(position: IntOffset): Pawn = Pawn(color = color, position = position)



    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        val isFirstMove =color.isWhite && position.y == 2 ||
                         color.isBlack && position.y == 7


        val regularMoves = getPieceMoves(pieces) {
            straightMoves(
                movement = if (color.isWhite) StraightMoves.Up else StraightMoves.Down,
                maxMovement = if (isFirstMove) 2 else 1,
                canCapture = false,
            )

            diagonalMoves(
                movement = if (color.isWhite) DiagonalMoves.TopRight else DiagonalMoves.BottomRight,
                maxMovement = 1,
                captureOnly = true,
            )

            diagonalMoves(
                movement = if (color.isWhite) DiagonalMoves.TopLeft else DiagonalMoves.BottomLeft,
                maxMovement = 1,
                captureOnly = true,
            )
        }
        return regularMoves

    }

    companion object{
        const val Type = 'P'
    }
}