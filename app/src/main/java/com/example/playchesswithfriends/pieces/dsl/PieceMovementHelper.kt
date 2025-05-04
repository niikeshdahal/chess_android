package com.example.playchesswithfriends.pieces

import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.board.BoardXCoordinates
import com.example.playchesswithfriends.board.BoardYCoordinates

enum class StraightMoves{
    Up,
    Down,
    Left,
    Right
}

enum class DiagonalMoves {
    TopRight,
    TopLeft,
    BottomRight,
    BottomLeft
}

fun Piece.getDiagonalMoves(
    pieces: List<Piece>,
    maxMovement: Int = 7,
    canCapture: Boolean,
    movement: DiagonalMoves,
    captureOnly: Boolean = false

): Set<IntOffset>{
    return getMoves(
        pieces = pieces,
        maxMovement = maxMovement,
        getPosition = {
            when (movement){
                DiagonalMoves.TopLeft ->
                    IntOffset(
                        x = position.x - it,
                        y = position.y + it
                    )

                DiagonalMoves.TopRight ->
                    IntOffset(
                        x = position.x + it,
                        y = position.y + it
                    )

                DiagonalMoves.BottomLeft ->
                    IntOffset(
                        x = position.x - it,
                        y = position.y - it
                    )

                DiagonalMoves.BottomRight ->
                    IntOffset(
                        x = position.x + it,
                        y = position.y - it
                    )
            }


        },
        canCapture = canCapture,
        captureOnly = captureOnly,
    )

}

fun Piece.getStraightMoves(
    pieces: List<Piece>,
    maxMovement: Int = 7,
    canCapture: Boolean ,
    movement: StraightMoves,
    captureOnly: Boolean = false
): Set<IntOffset>{

    return getMoves(pieces = pieces,
        maxMovement = maxMovement,
        getPosition = {
            when(movement){
                StraightMoves.Up ->
                    IntOffset(
                        x = position.x,
                        y = position.y + it
                    )

                StraightMoves.Down ->
                    IntOffset(
                        x = position.x,
                        y = position.y - it
                    )

                StraightMoves.Left ->
                    IntOffset(
                        x = position.x - it,
                        y = position.y
                    )

                StraightMoves.Right ->
                    IntOffset(
                        x = position.x + it,
                        y = position.y
                    )

            }

        },
        canCapture = canCapture,
        captureOnly = captureOnly,

    )
}

fun Piece.getMoves(
    pieces: List<Piece>,
    maxMovement: Int = 7,
    getPosition:(Int) -> IntOffset,
    canCapture: Boolean = true,
    captureOnly: Boolean,
): Set<IntOffset> {
    val moves = mutableSetOf<IntOffset>()

    for(i in 1..maxMovement){
        val targetPosition = getPosition(i)

        if(targetPosition.x !in BoardXCoordinates || targetPosition.y !in BoardYCoordinates)

            break

        val targetPiece = pieces.find{it.position == targetPosition}
        if(targetPiece != null) {
            if (targetPiece.color != this.color && canCapture)
                moves.add(targetPosition)
            break
        }else if (captureOnly ){
           break
        }

        else{
            moves.add(targetPosition)
        }
    }


    return  moves

}

fun Piece.getLMoves(
    pieces: List<Piece>,
): MutableSet<IntOffset> {
    val moves = mutableSetOf<IntOffset>()

    val offsets = listOf(
        IntOffset(-1, -2),
        IntOffset(1, -2),
        IntOffset(-2, -1),
        IntOffset(2, -1),
        IntOffset(-2, 1),
        IntOffset(2, 1),
        IntOffset(-1, 2),
        IntOffset(1, 2),
    )

    for (offset in offsets) {
        val targetPosition = position + offset

        if (targetPosition.x !in BoardXCoordinates || targetPosition.y !in BoardYCoordinates)
            continue

        val targetPiece = pieces.find { it.position == targetPosition }
        if (targetPiece == null || targetPiece.color != this.color)
            moves.add(targetPosition)
    }

    return moves
}

