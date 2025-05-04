package com.example.playchesswithfriends.board

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import com.example.playchesswithfriends.pieces.King
import com.example.playchesswithfriends.pieces.Piece
import com.example.playchesswithfriends.pieces.Rook

@Composable
fun rememberBoard(
    initialMinutes: Int,
    initialIncrement: Int,
    encodedPieces: String,
): Board = remember {
    Board(initialMinutes, initialIncrement, encodedPieces)
}

@Immutable
class Board(
    val initialMinutes: Int,
    val initialIncrement: Int,
    // The initial board state encoding.
    val encodedPieces: String,
) {
    // Internal pieces list (mutable state).
    private val _pieces = mutableStateListOf<Piece>()
    val pieces get() = _pieces.toList()

    // --- Move history ---
    // Each Move now stores an encoded board state.
    val moveHistory = mutableStateListOf<Move>()

    // When browsing history, currentDisplayedMoveIndex is not -1.
    // -1 indicates the board is showing the latest state.
    var currentDisplayedMoveIndex by mutableStateOf(-1)
        private set

    // Flag to indicate whether we are currently viewing a historical state.
    private var _isViewingHistory by mutableStateOf(false)
    val isViewingHistory: Boolean get() = _isViewingHistory

    // Expose a version number for UI recomposition when the board state changes.
    private var _boardStateVersion by mutableStateOf(0)
    val boardStateVersion: Int get() = _boardStateVersion

    // Other game state properties:
    var selectedPiece by mutableStateOf<Piece?>(null)
        private set
    var selectedPieceMoves by mutableStateOf(emptySet<IntOffset>())
        private set
    var moveIncrement by mutableIntStateOf(0)
        private set
    var playerTurn by mutableStateOf(Piece.Color.White)
        private set
    var isGameOver by mutableStateOf(false)
    var winner by mutableStateOf<Piece.Color?>(null)
    var remainingTimeWhite = mutableStateOf(initialMinutes * 60)
    var remainingTimeBlack = mutableStateOf(initialMinutes * 60)
    val capturedPiecesWhite = mutableStateListOf<CapturedPieceWhite>()
    val capturedPiecesBlack = mutableStateListOf<CapturedPieceBlack>()

    // Define isEditable to disable moves while browsing history or when the game is over.
    val isEditable: Boolean
        get() = (!_isViewingHistory && !isGameOver)

    init {
        // Initialize the board from the encoded initial state.
        _pieces.addAll(decodePieces(encodedPieces = encodedPieces))
        // Record the initial state as moveHistory[0]
        moveHistory.add(Move(encodedPieces))
    }

    /**
     * Encodes the current board state by concatenating each piece’s encoding (with no separator).
     */
    fun encodeBoardState(): String {
        return pieces.joinToString(separator = "") { it.encode() }
    }

    /**
     * Rebuilds the board state from a saved encoded state.
     *
     * If targetIndex == -1, we use the latest state (i.e. moveHistory.last()).
     * Otherwise, we decode the board state stored at moveHistory[targetIndex].
     */
    private fun rebuildBoardState(targetIndex: Int) {
        println("Rebuilding state for index: $targetIndex") // Debug log
        _pieces.clear()
        val stateToDecode = when {
            targetIndex == -1 -> moveHistory.last().boardState
            targetIndex in moveHistory.indices -> moveHistory[targetIndex].boardState
            else -> encodedPieces
        }
        println("Using encoded state: $stateToDecode") // Debug log
        _pieces.addAll(decodePieces(encodedPieces = stateToDecode))
    }

    /**
     * Records the current board state.
     */
    fun recordCurrentState() {
        val state = encodeBoardState()
        moveHistory.add(Move(state))
        println("Move recorded; total moves: ${moveHistory.size}") // Debug log
        // Reset browsing so that we are at the latest state.
        currentDisplayedMoveIndex = -1
        _isViewingHistory = false
    }

    /**
     * User makes a move.
     */
    fun selectPiece(piece: Piece) {
        if (piece.color != playerTurn || isGameOver) return
        if (!isEditable) return  // Do not allow selection when browsing history

        if (piece == selectedPiece) {
            clearSelection()
        } else {
            selectedPiece = piece
            selectedPieceMoves = piece.getAvailableMoves(pieces).filter { move ->
                !isTheKingInThreat(pieces = pieces, piece = piece, x = move.x, y = move.y)
            }.toSet()
        }
    }

    fun moveSelectedPiece(x: Int, y: Int) {
        if (_isViewingHistory) return  // Disallow moves while browsing history.
        if (!isEditable) return
        selectedPiece?.let { piece ->
            if (!isAvailableMove(x, y) || isGameOver) return
            if (piece.color != playerTurn) return

            val destination = IntOffset(x, y)
            movePiece(piece, destination)
            clearSelection()
            // Record state after the move.
            recordCurrentState()

            // Check for checkmate (simplified).
            val opponent = if (playerTurn == Piece.Color.White) Piece.Color.Black else Piece.Color.White
            if (isCheckmate(pieces, opponent)) {
                isGameOver = true
                winner = playerTurn
            } else {
                switchPlayerTurn()
                if (piece.color.isWhite) {
                    remainingTimeWhite.value += initialIncrement
                } else {
                    remainingTimeBlack.value += initialIncrement
                }
            }
            moveIncrement++
        }
    }

    fun getPiece(x: Int, y: Int): Piece? =
        _pieces.find { it.position.x == x && it.position.y == y }

    fun isAvailableMove(x: Int, y: Int): Boolean =
        selectedPieceMoves.any { it.x == x && it.y == y }

    // --- History Navigation Methods ---

    fun goToPreviousMove() {
        println("Previous move clicked. History size: ${moveHistory.size}, current index: $currentDisplayedMoveIndex")
        if (moveHistory.size <= 1) return  // No history to browse beyond initial state.
        if (currentDisplayedMoveIndex == -1) {
            // First click: set index to one before the latest state.
            currentDisplayedMoveIndex = moveHistory.lastIndex - 1
            _isViewingHistory = true
        } else if (currentDisplayedMoveIndex > 0) {
            currentDisplayedMoveIndex--
        }
        println("Navigating to history index: $currentDisplayedMoveIndex")
        rebuildBoardState(currentDisplayedMoveIndex)
        _boardStateVersion++
    }

    fun goToNextMove() {
        println("Next move clicked. History size: ${moveHistory.size}, current index: $currentDisplayedMoveIndex")
        if (!_isViewingHistory) return  // Already at latest state.
        if (currentDisplayedMoveIndex < moveHistory.lastIndex) {
            currentDisplayedMoveIndex++
            if (currentDisplayedMoveIndex == moveHistory.lastIndex) {
                // Back to the latest state.
                _isViewingHistory = false
                currentDisplayedMoveIndex = -1
            }
        } else {
            // If already at the latest recorded move, exit history browsing.
            currentDisplayedMoveIndex = -1
            _isViewingHistory = false
        }
        println("Navigating to history index: $currentDisplayedMoveIndex")
        rebuildBoardState(currentDisplayedMoveIndex)
        _boardStateVersion++
    }

    // --- Private helper methods ---

    private fun movePiece(piece: Piece, position: IntOffset) {
        // (Include castling logic if needed.)
        if (piece is King) {
            val rank = if (piece.color == Piece.Color.White) 1 else 8
            if (piece.position == IntOffset('E'.code, rank)) {
                if (position == IntOffset('G'.code, rank)) {
                    val rook = pieces.find { it is Rook && it.position == IntOffset('H'.code, rank) }
                    rook?.position = IntOffset('F'.code, rank)
                } else if (position == IntOffset('C'.code, rank)) {
                    val rook = pieces.find { it is Rook && it.position == IntOffset('A'.code, rank) }
                    rook?.position = IntOffset('D'.code, rank)
                }
            }
            // If your Piece interface does not support hasMoved, omit this.
            // piece.hasMoved = true
        }
        if (piece is Rook) {
            // piece.hasMoved = true
        }
        val targetPiece = pieces.find { it.position == position }
        if (targetPiece != null) {
            removePiece(targetPiece)
        }
        piece.position = position
    }

    private fun removePiece(piece: Piece) {
        if (piece.color == Piece.Color.White) {
            // White piece is captured (i.e. captured by Black)
            // Call your helper that returns the drawable for a captured white piece.
            val drawableRes = getDrawableForCapturedPieceWhite(piece)
            capturedPiecesWhite.add(CapturedPieceWhite(drawableRes))
        } else {
            // Black piece is captured (i.e. captured by White)
            val drawableRes = getDrawableForCapturedPieceBlack(piece)
            capturedPiecesBlack.add(CapturedPieceBlack(drawableRes))
        }
        _pieces.remove(piece)
    }

    private fun clearSelection() {
        selectedPiece = null
        selectedPieceMoves = emptySet()
    }

    private fun switchPlayerTurn() {
        playerTurn = if (playerTurn == Piece.Color.White) Piece.Color.Black else Piece.Color.White
    }
}

// --- Extension functions for UI helpers ---

@Composable
fun Board.rememberPieceAt(x: Int, y: Int): Piece? =
    remember(x, y, moveIncrement, boardStateVersion) {
        getPiece(x, y)
    }

@Composable
fun Board.rememberIsAvailableMove(x: Int, y: Int): Boolean =
    remember(x, y, selectedPieceMoves) {
        isAvailableMove(x, y)
    }
