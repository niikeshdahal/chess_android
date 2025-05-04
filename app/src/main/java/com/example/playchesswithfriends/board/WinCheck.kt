package com.example.playchesswithfriends.board

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.pieces.King
import com.example.playchesswithfriends.pieces.Piece

fun isTheKingInThreat(
    pieces: List<Piece>,
    piece: Piece,
    x: Int,
    y: Int,
): Boolean {
    // Simulate the move by copying the piece to the target position
    val simulatedPiece = piece.copy(position = IntOffset(x, y))

    // Simulate the board state by replacing the moved piece and removing any piece at the target position
    val simulatedPieces = pieces.map { if (it == piece) simulatedPiece else it }
        .filter { it.position != simulatedPiece.position || it == simulatedPiece }

    // Find the king of the same color
    val king = simulatedPieces.firstOrNull { it is King && it.color == piece.color } ?: return false

    // Get all enemy pieces
    val enemyPieces = simulatedPieces.filter { it.color != king.color }

    // Check if any enemy piece can attack the king's position
    return enemyPieces.any { enemyPiece ->
        val enemyMoves = enemyPiece.getAvailableMoves(simulatedPieces)
        enemyMoves.contains(king.position)
    }
}

fun isCheckmate(
    pieces: List<Piece>,
    playerTurn: Piece.Color,
): Boolean {
    // Find the current player's king
    val king = pieces.firstOrNull { it is King && it.color == playerTurn } ?: return false

    // Get all potential moves for the king, including staying in its current position
    val kingMoves = king.getAvailableMoves(pieces).filter { move ->
        !isTheKingInThreat(pieces, king, move.x, move.y)
    }


    // If the king has at least one safe move, it's not checkmate
    if (kingMoves.isNotEmpty()) return false

    // Check if any other piece can protect the king
    val playerPieces = pieces.filter { it.color == playerTurn && it != king }
    val canProtectKing = playerPieces.any { piece ->
        piece.getAvailableMoves(pieces).any { move ->
            val updatedPieces = pieces.map {
                if (it == piece) piece.copy(position = move) else it
            }
            !isTheKingInThreat(updatedPieces, king, king.position.x, king.position.y)
        }
    }

    // If no safe king moves and no piece can protect the king, it's checkmate
    return !canProtectKing
}