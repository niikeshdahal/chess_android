package com.example.playchesswithfriends.board

import com.example.playchesswithfriends.R
import com.example.playchesswithfriends.pieces.Bishop
import com.example.playchesswithfriends.pieces.King
import com.example.playchesswithfriends.pieces.Knight
import com.example.playchesswithfriends.pieces.Pawn
import com.example.playchesswithfriends.pieces.Piece
import com.example.playchesswithfriends.pieces.Queen
import com.example.playchesswithfriends.pieces.Rook

// Data class to hold the drawable resource for a captured piece.
data class CapturedPieceWhite(
    val drawableRes: Int
)

// Helper function that maps a Piece to its captured drawable.
fun getDrawableForCapturedPieceWhite(piece: Piece): Int {
    return when (piece) {
        is Bishop ->  R.drawable.piece_bishop__side_white
        is Knight -> R.drawable.piece_knight__side_white
        is Rook -> R.drawable.piece_rook__side_white
        is Queen ->  R.drawable.piece_queen__side_white
        is Pawn -> R.drawable.piece_pawn__side_white
        else      -> 0 // Fallback resource.
    }
}

// Data class to hold the drawable resource for a captured piece.
data class CapturedPieceBlack(
    val drawableRes: Int
)

// Helper function that maps a Piece to its captured drawable.
fun getDrawableForCapturedPieceBlack(piece: Piece): Int {
    return when (piece) {
        is Bishop ->R.drawable.piece_bishop__side_black
        is Knight ->R.drawable.piece_knight__side_black
        is Rook ->  R.drawable.piece_rook__side_black
        is Queen -> R.drawable.piece_queen__side_black
        is Pawn ->  R.drawable.piece_pawn__side_black
        else      -> 0 // Fallback resource.
    }
}
