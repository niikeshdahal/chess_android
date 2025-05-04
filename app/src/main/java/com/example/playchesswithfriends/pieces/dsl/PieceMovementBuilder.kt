package com.example.playchesswithfriends.pieces.dsl

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.pieces.DiagonalMoves
import com.example.playchesswithfriends.pieces.Piece
import com.example.playchesswithfriends.pieces.StraightMoves
import com.example.playchesswithfriends.pieces.getDiagonalMoves
import com.example.playchesswithfriends.pieces.getLMoves
import com.example.playchesswithfriends.pieces.getStraightMoves

fun Piece.getPieceMoves(
    pieces: List<Piece>,
    block: PieceMovementBuilder.() -> Unit
): Set<IntOffset> {
    val builder = PieceMovementBuilder(
        piece = this,
        pieces = pieces
    )
    builder.block()
    return builder.build()
}

class PieceMovementBuilder(
    private val piece: Piece,
    private val pieces: List<Piece>,
) {
    private val moves = mutableSetOf<IntOffset>()

    fun straightMoves(
        maxMovement: Int = 7,
        canCapture: Boolean = true,
        captureOnly: Boolean = false,
    ) {
        StraightMoves.entries.forEach { movement ->
            straightMoves(
                movement = movement,
                maxMovement = maxMovement,
                canCapture = canCapture,
                captureOnly = captureOnly,
            )
        }
    }

    fun straightMoves(
        movement: StraightMoves,
        maxMovement: Int = 7,
        canCapture: Boolean = true,
        captureOnly: Boolean = false,
    ) {
        moves.addAll(
            piece.getStraightMoves(
                pieces = pieces,
                movement = movement,
                maxMovement = maxMovement,
                canCapture = canCapture,
                captureOnly = captureOnly,
            )
        )
    }

    fun diagonalMoves(
        maxMovement: Int = 7,
        canCapture: Boolean = true,
        captureOnly: Boolean = false,
    ) {
        DiagonalMoves.entries.forEach { movement ->
            diagonalMoves(
                movement = movement,
                maxMovement = maxMovement,
                canCapture = canCapture,
                captureOnly = captureOnly,
            )
        }
    }

    fun diagonalMoves(
        movement: DiagonalMoves,
        maxMovement: Int = 7,
        canCapture: Boolean = true,
        captureOnly: Boolean = false,
    ) {
        moves.addAll(
            piece.getDiagonalMoves(
                pieces = pieces,
                movement = movement,
                maxMovement = maxMovement,
                canCapture = canCapture,
                captureOnly = captureOnly,
            )
        )
    }

    fun getLMoves() {
        moves.addAll(
            piece.getLMoves(
                pieces = pieces,
            )
        )
    }

    fun build(): Set<IntOffset> = moves.toSet()
}