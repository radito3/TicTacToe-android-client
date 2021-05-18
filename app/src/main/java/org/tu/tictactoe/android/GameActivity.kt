package org.tu.tictactoe.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.game_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tu.tictactoe.android.grpc.AndroidIOServer

class GameActivity : AppCompatActivity() {

    private lateinit var server: AndroidIOServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)
//        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //FIXME this isn't the way to get the view
        server = AndroidIOServer(8980, findViewById(R.id.GameScreen))
        server.start()

        section_top_start.setOnClickListener(onClick)
        section_top_middle.setOnClickListener(onClick)
        section_top_end.setOnClickListener(onClick)
        section_middle_start.setOnClickListener(onClick)
        section_middle_middle.setOnClickListener(onClick)
        section_middle_end.setOnClickListener(onClick)
        section_bottom_start.setOnClickListener(onClick)
        section_bottom_middle.setOnClickListener(onClick)
        section_bottom_end.setOnClickListener(onClick)
    }

    override fun onDestroy() {
        server.stop()
        super.onDestroy()
    }

    private val onClick = View.OnClickListener { view ->
        lifecycleScope.launch(Dispatchers.IO) {
            when (view.id) {
                section_top_start.id -> {
                    server.playerClick(0, 0)
                }
                section_top_middle.id -> {
                    server.playerClick(0, 1)
                }
                section_top_end.id -> {
                    server.playerClick(0, 2)
                }
                section_middle_start.id -> {
                    server.playerClick(1, 0)
                }
                section_middle_middle.id -> {
                    server.playerClick(1, 1)
                }
                section_middle_end.id -> {
                    server.playerClick(1, 2)
                }
                section_bottom_start.id -> {
                    server.playerClick(2, 0)
                }
                section_bottom_middle.id -> {
                    server.playerClick(2, 1)
                }
                section_bottom_end.id -> {
                    server.playerClick(2, 2)
                }
            }
        }
    }

}