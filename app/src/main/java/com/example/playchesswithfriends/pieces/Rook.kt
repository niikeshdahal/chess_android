package com.example.playchesswithfriends.pieces

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.pieces.dsl.getPieceMoves

class Rook(
    override val color: Color,
    override var position: IntOffset
): Piece() {

    override val type: Char = Type

    override val drawable: Int =
        if(color.isWhite)
            R.drawable.piece_rook__side_white
        else
            R.drawable.piece_rook__side_black

    override fun copy(position: IntOffset): Rook = Rook(color = color, position = position)

    var hasMoved = false

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        return getPieceMoves(pieces) {
            straightMoves()

        }

    }

    companion object{
        const val Type = 'R'
    }




}