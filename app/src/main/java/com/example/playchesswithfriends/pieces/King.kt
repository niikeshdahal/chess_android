package com.example.playchesswithfriends.pieces

import android.util.Log
import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.board.isTheKingInThreat
import com.example.playchesswithfriends.pieces.dsl.getPieceMoves

class King(
    override val color: Color,
    override var position: IntOffset
): Piece() {

    override val type: Char = Type

    override val drawable: Int =
        if(color.isWhite)
            R.drawable.piece_king__side_white
        else
            R.drawable.king_black

    override fun copy(position: IntOffset): King = King(color = color, position = position)

    var hasMoved: Boolean = false


    private fun getCastlingMoves(pieces: List<Piece>): Set<IntOffset> {
        if (hasMoved) {
            return emptySet()
        }

        val castlingMoves = mutableSetOf<IntOffset>()
        val rank = if (color == Color.White) 1 else 8

        // Check kingside castling
        val kingsideRook = pieces.find {
            it is Rook  &&
                    it.color == color &&
                    it.position == IntOffset('H'.code, rank)
        }
        if (kingsideRook != null && !isPathBlockedOrAttacked(pieces, true)) {
            castlingMoves.add(IntOffset('G'.code, rank))
        }

        // Check queenside castling
        val queensideRook = pieces.find {
            it is Rook &&
                    it.color == color &&
                    it.position == IntOffset('A'.code, rank)
        }
        if (queensideRook != null && !isPathBlockedOrAttacked(pieces, false)) {
            castlingMoves.add(IntOffset('C'.code, rank))
        }

        return castlingMoves
    }

    private fun isPathBlockedOrAttacked(pieces: List<Piece>, kingSide: Boolean): Boolean {
        val rank = if (color == Color.White) 1 else 8
        val squares = if (kingSide) {
            listOf(IntOffset('F'.code, rank), IntOffset('G'.code, rank))
        } else {
            listOf(IntOffset('D'.code, rank), IntOffset('C'.code, rank), IntOffset('B'.code, rank))
        }

        // Check if path is blocked
        if (pieces.any { it.position in squares }) {
            return true
        }

        // Check if path is attacked
        return squares.any { square ->
            pieces.filter { it.color != color }.any { piece ->
                piece.getAvailableMoves(pieces).contains(square)
            }
        }
    }




    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        val regularMoves = getPieceMoves(pieces) {
            straightMoves(maxMovement = 1)
            diagonalMoves(maxMovement = 1)
        }
        val castingMoves = getCastlingMoves(pieces)


        return regularMoves+castingMoves
    }





    companion object{
        const val Type = 'K'
    }
}
