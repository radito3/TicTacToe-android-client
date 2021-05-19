package org.tu.tictactoe.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.squareup.okhttp.*
import kotlinx.coroutines.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoadingScreen : Fragment() {

    private val client = OkHttpClient()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        client.protocols = listOf(Protocol.HTTP_1_1)
        //TODO handle server errors
        client.newCall(Request.Builder()
                .post(createBody())
                .url("localhost/search")
                .build())
            .execute()

        return inflater.inflate(R.layout.loading_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch(Dispatchers.IO) {
            pollForGame()
        }

        //TODO render a loading animation
        // that will end when the polling has finished
        // after that, navigate to game screen
    }

    private fun createRequest(): Request = Request.Builder()
                                                .get()
                                                .url("localhost/search/search_id")
                                                .header("x-player-id", "player_id")
                                                .build()

    private fun createBody(): RequestBody = RequestBody.create(
        MediaType.parse("text/plain"), "player_id")

    private val responseCallback = object : Callback {
        override fun onFailure(request: Request?, e: IOException?) {
            println(e)
        }

        override fun onResponse(response: Response?) {
            if (response?.code() == 200) {
                findNavController().navigate(R.id.game_found)
            }
        }
    }

    private suspend fun pollForGame() = withTimeoutOrNull(TimeUnit.MINUTES.toMillis(1)) {
        while (true) {
            client.newCall(createRequest())
                    .enqueue(responseCallback)
            delay(TimeUnit.SECONDS.toMillis(1))
        }
    }

}