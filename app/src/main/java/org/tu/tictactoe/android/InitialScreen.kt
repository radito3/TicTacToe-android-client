package org.tu.tictactoe.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

class InitialScreen : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.initial_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.play_solo_button).setOnClickListener {
            Snackbar.make(view, "Feature not implemented yet", Snackbar.LENGTH_LONG)
                    .show()
        }

        view.findViewById<Button>(R.id.find_game_button).setOnClickListener {
            val playerId = view.findViewById<EditText>(R.id.name_field).text.toString()
            if (playerId.isEmpty()) {
                Snackbar.make(view, "Player name is empty", Snackbar.LENGTH_SHORT)
                        .show()
                return@setOnClickListener
            }
            val args = bundleOf("player-id" to playerId)
            findNavController().navigate(R.id.start_search, args)
        }
    }
}