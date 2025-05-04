package com.example.playchesswithfriends.pieces

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.pieces.dsl.getPieceMoves

class Bishop(
    override val color: Color,
    override var position: IntOffset
): Piece() {

    override val type: Char = Type

    override val drawable: Int =
        if(color.isWhite)
            R.drawable.piece_bishop__side_white
        else
            R.drawable.piece_bishop__side_black

    override fun copy(position: IntOffset): Bishop = Bishop(color = color, position = position)

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        return getPieceMoves(pieces) {
            diagonalMoves()

        }

    }

    companion object{
        const val Type = 'B'
    }

}