package org.tu.tictactoe.android.grpc

import android.view.View
import io.grpc.ServerBuilder

class AndroidIOServer(private val port: Int, drawableView: View) {
    private val inputReader = InputReaderService()
    private val server = ServerBuilder.forPort(port)
                                    .addService(DisplayWriterService(drawableView))
                                    .addService(inputReader)
                                    .build()

    fun start() {
        server.start();
        println("Server started, listening on $port")
    }

    suspend fun playerClick(row: Int, col: Int) {
        inputReader.submitClickEvent(row, col)
    }

    fun stop() {
        server.shutdown()
        server.awaitTermination()
        println("Server shut down")
    }

}