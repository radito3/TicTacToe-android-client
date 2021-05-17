package org.tu.tictactoe.android.grpc

import com.google.protobuf.Empty
import org.tu.tictactoe.android.io.*
import org.tu.tictactoe.android.io.CoordinateOuterClass.Coordinate
import org.tu.tictactoe.android.model.Cell
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.BlockingQueue

class InputReaderService : InputReaderGrpcKt.InputReaderCoroutineImplBase()  {

    private sealed class Click {
        class CellClick(val cell: Cell): Click()
        object SetClick: Click()
    }

    private var clickQueue: BlockingQueue<Click> = ArrayBlockingQueue(1, true)

    fun submitClickEvent(row: Int, col: Int) {
        clickQueue.put(Click.CellClick(Cell(row, col)))
    }

    override suspend fun read(request: Empty): Input {
        when (val click = clickQueue.take()) {
            is Click.SetClick -> return Input.newBuilder().apply {
                set = true
                type = InputType.SET
            }.build()

            is Click.CellClick -> {
                clickQueue.put(Click.SetClick)
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