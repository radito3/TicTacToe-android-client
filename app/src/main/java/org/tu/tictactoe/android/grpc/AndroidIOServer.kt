package org.tu.tictactoe.android.grpc

import io.grpc.Server
import io.grpc.ServerBuilder

class AndroidIOServer(private val port: Int) {
    private val server: Server = ServerBuilder.forPort(port)
                            .addService(DisplayWriterService())
                            .addService(InputReaderService())
                            .build();

    fun start() {
        server.start();
        println("Server started, listening on $port")
    }

    fun stop() {
        server.shutdown()
        server.awaitTermination()
        println("Server shut down")
    }

}