package org.tu.tictactoe.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoadingScreen : Fragment() {

    //TODO keep state of search

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //TODO initiate a coroutine to poll for a game

        return inflater.inflate(R.layout.loading_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO render a loading animation
        // that will end when the polling has finished
        // after that, navigate to game screen
//        findNavController().navigate(R.id.action_LoadingScreen_to_GameScreen)
    }

}