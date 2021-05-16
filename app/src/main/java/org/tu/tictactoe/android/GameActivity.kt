package org.tu.tictactoe.android

import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
import android.view.View
//import android.view.ViewGroup
import android.view.animation.Animation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.game_screen.*
import org.tu.tictactoe.android.grpc.AndroidIOServer
import org.tu.tictactoe.android.util.Animations
import org.tu.tictactoe.android.util.GrpcServerProvider

class GameActivity : AppCompatActivity() {

    lateinit var server: AndroidIOServer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_screen)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        server = GrpcServerProvider.getServer()
    }

    enum class Seed {
        EMPTY, CROSS, NOUGHT
    }

    class Cell(val row: Int, val col: Int) {
        var content: Seed = Seed.EMPTY
        fun clear() {
            content = Seed.EMPTY
        }
    }

    fun reloadBoard(cell: Cell?) {
        val unwrapCell = cell ?: Cell(-1, -1)

        val sectionView: View? = when {
            unwrapCell.row == 0 && unwrapCell.col == 0 -> {
                section_top_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_start
            }

            unwrapCell.row == 0 && unwrapCell.col == 1 -> {
                section_top_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_middle
            }

            unwrapCell.row == 0 && unwrapCell.col == 2 -> {
                section_top_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_top_end
            }

            unwrapCell.row == 1 && unwrapCell.col == 0 -> {
                section_middle_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_start
            }

            unwrapCell.row == 1 && unwrapCell.col == 1 -> {
                section_middle_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_middle
            }

            unwrapCell.row == 1 && unwrapCell.col == 2 -> {
                section_middle_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_middle_end
            }

            unwrapCell.row == 2 && unwrapCell.col == 0 -> {
                section_bottom_start.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_start
            }

            unwrapCell.row == 2 && unwrapCell.col == 1 -> {
                section_bottom_middle.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_middle
            }

            unwrapCell.row == 2 && unwrapCell.col == 2 -> {
                section_bottom_end.setImageDrawable(when (unwrapCell.content) {
                    Seed.CROSS -> ContextCompat.getDrawable(this, R.drawable.ic_cross)
                    else -> ContextCompat.getDrawable(this, R.drawable.ic_nougth)
                })
                section_bottom_end
            }

            else -> {
                return
            }
        }

        sectionView?.isEnabled = false
        sectionView?.animation = Animations.fadeIn(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                sectionView?.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
        })
    }

}