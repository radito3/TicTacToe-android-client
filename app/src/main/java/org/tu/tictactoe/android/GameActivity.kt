package org.tu.tictactoe.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.game_screen.*
import org.tu.tictactoe.android.grpc.AndroidIOServer
import org.tu.tictactoe.android.grpc.DisplayWriterService
import org.tu.tictactoe.android.grpc.InputReaderService
import org.tu.tictactoe.android.util.GrpcServerProvider
import org.tu.tictactoe.android.util.Presenter

class GameActivity : AppCompatActivity() {

    private lateinit var server: AndroidIOServer
    private lateinit var displayWriter: DisplayWriterService
    private val inputReader = InputReaderService()
    private val presenter = Presenter(inputReader)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        server = GrpcServerProvider.getServer()
        displayWriter = DisplayWriterService(findViewById(R.id.GameScreen))

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

    private val onClick = View.OnClickListener { view ->
        when (view.id) {
            section_top_start.id -> {
                presenter.playerMove(0, 0)
            }
            section_top_middle.id -> {
                presenter.playerMove(0, 1)
            }
            section_top_end.id -> {
                presenter.playerMove(0, 2)
            }
            section_middle_start.id -> {
                presenter.playerMove(1, 0)
            }
            section_middle_middle.id -> {
                presenter.playerMove(1, 1)
            }
            section_middle_end.id -> {
                presenter.playerMove(1, 2)
            }
            section_bottom_start.id -> {
                presenter.playerMove(2, 0)
            }
            section_bottom_middle.id -> {
                presenter.playerMove(2, 1)
            }
            section_bottom_end.id -> {
                presenter.playerMove(2, 2)
            }
        }
    }

}