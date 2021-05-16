package org.tu.tictactoe.android.util

import org.tu.tictactoe.android.grpc.AndroidIOServer

class GrpcServerProvider {

    companion object {
        private val server: AndroidIOServer = AndroidIOServer(8980)
        init {
            server.start()
        }

        fun getServer(): AndroidIOServer {
            return server
        }
    }
}