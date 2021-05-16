package org.tu.tictactoe.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.squareup.okhttp.*
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

class LoadingScreen : Fragment() {

    lateinit var client: OkHttpClient
    lateinit var pollingCoroutine: Job

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        client = OkHttpClient()
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
        pollingCoroutine = GlobalScope.launch {
            pollForGame()
        }

        //TODO render a loading animation
        // that will end when the polling has finished
        // after that, navigate to game screen
    }

    private fun createRequest(): Request = Request.Builder()
                                                .get()
                                                .url("localhost/search/123")
                                                .header("x-player-id", "abc")
                                                .build()

    private fun createBody(): RequestBody = RequestBody.create(
        MediaType.parse("text/plain"), "abc")

    private suspend fun pollForGame() {
        withTimeoutOrNull(TimeUnit.MINUTES.toMillis(1)) {
            while (true) {
                val response = client.newCall(createRequest()).execute()
                if (response.code() == 200) {
                    findNavController().navigate(R.id.game_found)
                    break
                }
                delay(TimeUnit.SECONDS.toMillis(3))
            }
        }
    }

}