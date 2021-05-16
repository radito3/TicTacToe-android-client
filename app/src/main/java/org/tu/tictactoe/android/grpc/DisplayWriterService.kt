package org.tu.tictactoe.android.grpc

import com.google.protobuf.Empty
import org.tu.tictactoe.android.io.*

class DisplayWriterService : DisplayWriterGrpcKt.DisplayWriterCoroutineImplBase() {

    override suspend fun writeGrid(request: Empty): Empty {
        //NO-OP
        return Empty.getDefaultInstance();
    }

    override suspend fun clearCellAt(request: CoordinateOuterClass.Coordinate): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun writeSymbol(request: WriteIconMessage): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun writePlaceholderFor(request: WriteIconMessage): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun writeStroke(request: WriteStrokeMessage): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun writeMsg(request: TextMessage): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun writeTempMsg(request: TextMessage): Empty {

        return Empty.getDefaultInstance();
    }

    override suspend fun flashPlaceholder(request: WriteIconMessage): Empty {

        return Empty.getDefaultInstance();
    }
}