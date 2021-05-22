package org.tu.tictactoe.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.squareup.okhttp.*
import kotlinx.coroutines.*
import java.io.IOException
import java.util.concurrent.TimeUnit

class LoadingScreen : Fragment() {

    private val client = OkHttpClient()
    private val serverUrl = System.getenv("server-url") ?: "localhost"
    private var searchId: String = ""
    private var playerId: String = ""

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        client.protocols = listOf(Protocol.HTTP_1_1)
        val response = client.newCall(Request.Builder()
                    .post(createBody())
                    .url("$serverUrl/search")
                    .build())
                .execute()
        if (response.isSuccessful) {
            searchId = response.body().string()
        }
        return inflater.inflate(R.layout.loading_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (searchId.isEmpty()) {
            Snackbar.make(view, "Couldn't reach server", Snackbar.LENGTH_LONG)
                    .show()
            findNavController().navigate(R.id.end_game)
            return
        }
        playerId = arguments?.getString("player-id") ?: ""

        lifecycleScope.launch(Dispatchers.IO) {
            pollForGame()
        }
        //TODO render a loading animation
        // that will end when the polling has finished
        // after that, navigate to game screen
    }

    private fun createRequest(): Request = Request.Builder()
                                                .get()
                                                .url("$serverUrl/search/$searchId")
                                                .header("x-player-id", playerId)
                                                .build()

    private fun createBody(): RequestBody = RequestBody.create(
        MediaType.parse("text/plain"), playerId)

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