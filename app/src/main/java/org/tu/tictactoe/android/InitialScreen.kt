package org.tu.tictactoe.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

//maybe a different name?
class InitialScreen : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.initial_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            //TODO before navigating to loading screen, check if both fields (name & symbol)
            // are filled out
            //TODO change button to "Search for Game"
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}