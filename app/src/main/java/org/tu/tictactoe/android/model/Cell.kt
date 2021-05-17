package org.tu.tictactoe.android.model

class Cell(val row: Int, val col: Int) {
    var state: CellState = CellState.EMPTY

    fun clear() {
        state = CellState.EMPTY
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Cell

        if (row != other.row) return false
        if (col != other.col) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        result = 31 * result + state.hashCode()
        return result
    }
}
