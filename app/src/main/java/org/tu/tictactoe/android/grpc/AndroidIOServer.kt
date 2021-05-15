package org.tu.tictactoe.android.grpc

class AndroidIOServer {
    private val server: Server = ServerBuilder.forPort(5123)
                            .addService(DisplayWriterService())
                            .addService(InputReaderService())
                            .build();

    fun start() {
        server.start();
    }

}