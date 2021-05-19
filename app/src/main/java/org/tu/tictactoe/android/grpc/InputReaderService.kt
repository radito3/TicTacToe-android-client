package org.tu.tictactoe.android.grpc

import com.google.protobuf.Empty
import kotlinx.coroutines.channels.Channel
import org.tu.tictactoe.android.io.*
import org.tu.tictactoe.android.io.CoordinateOuterClass.Coordinate
import org.tu.tictactoe.android.model.Cell

class InputReaderService : InputReaderGrpcKt.InputReaderCoroutineImplBase()  {

    private sealed class Click {
        class CellClick(val cell: Cell): Click()
        object SetClick: Click()
    }

    private var clickChan: Channel<Click> = Channel(1)

    suspend fun submitClickEvent(row: Int, col: Int) {
        clickChan.send(Click.CellClick(Cell(row, col)))
    }

    override suspend fun read(request: Empty): Input {
        when (val click = clickChan.receive()) {
            is Click.SetClick -> return Input.newBuilder().apply {
                set = true
                type = InputType.SET
            }.build()

            is Click.CellClick -> {
                clickChan.send(Click.SetClick)
                return Input.newBuilder().apply {
                    coord = Coordinate.newBuilder().apply {
                        x = click.cell.col
                        y = click.cell.row
                    }.build()
                    type = InputType.POSITIONAL
                }.build()
            }
        }
    }
}