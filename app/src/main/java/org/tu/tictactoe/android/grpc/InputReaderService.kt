package org.tu.tictactoe.android.grpc

import com.google.protobuf.Empty
import org.tu.tictactoe.android.io.*
import org.tu.tictactoe.android.io.CoordinateOuterClass.Coordinate

class InputReaderService : InputReaderGrpcKt.InputReaderCoroutineImplBase()  {

    override suspend fun read(request: Empty): Input {
        return Input.newBuilder().apply {
            set = true
            coord = Coordinate.newBuilder().apply {
                x = 0
                y = 0
            }.build()
            moveDir = MoveDirection.MD_INVALID
            type = InputType.SET
        }.build()
    }
}