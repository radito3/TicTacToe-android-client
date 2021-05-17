package org.tu.tictactoe.android.util

import org.tu.tictactoe.android.grpc.InputReaderService

class Presenter(private val inputReader: InputReaderService) {

    fun playerMove(row: Int, col: Int) {
        inputReader.submitClickEvent(row, col)
    }
}