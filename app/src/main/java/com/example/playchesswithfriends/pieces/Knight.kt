package com.example.playchesswithfriends.pieces

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.pieces.Rook.Companion
import com.example.playchesswithfriends.pieces.dsl.getPieceMoves

class Knight(
    override val color: Color,
    override var position: IntOffset
): Piece() {

    override val type: Char = Type

    override val drawable: Int =
        if(color.isWhite)
            R.drawable.piece_knight__side_white
        else
            R.drawable.piece_knight__side_black

    override fun copy(position: IntOffset): Knight = Knight(color = color, position = position)

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        return getPieceMoves(pieces) {
            getLMoves()
        }

    }


    companion object{
        const val Type = 'N'
    }

}