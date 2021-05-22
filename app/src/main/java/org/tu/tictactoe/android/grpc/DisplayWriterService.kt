package org.tu.tictactoe.android.grpc

import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.protobuf.Empty
import kotlinx.android.synthetic.main.game_screen.view.*
import kotlinx.coroutines.delay
import org.tu.tictactoe.android.R
import org.tu.tictactoe.android.io.*
import org.tu.tictactoe.android.model.Cell
import org.tu.tictactoe.android.util.Animations
import java.util.concurrent.TimeUnit

class DisplayWriterService(private val view: View) : DisplayWriterGrpcKt.DisplayWriterCoroutineImplBase() {

    private val boardViews: Map<Cell, AppCompatImageView> = mapOf(
            Cell(0, 0) to view.section_top_start,
            Cell(0, 1) to view.section_top_middle,
            Cell(0, 2) to view.section_top_end,
            Cell(1, 0) to view.section_middle_start,
            Cell(1, 1) to view.section_middle_middle,
            Cell(1, 1) to view.section_middle_end,
            Cell(2, 0) to view.section_bottom_start,
            Cell(2, 1) to view.section_bottom_middle,
            Cell(2, 2) to view.section_bottom_end
    )

    override suspend fun writeGrid(request: Empty): Empty {
        for (value in boardViews.values) {
            value.setImageDrawable(null)
        }
        return Empty.getDefaultInstance()
    }

    override suspend fun clearCellAt(request: CoordinateOuterClass.Coordinate): Empty {
        val cell = Cell(request.y, request.x)
        boardViews[cell]?.setImageDrawable(null)
        return Empty.getDefaultInstance()
    }

    override suspend fun writeSymbol(request: WriteIconMessage): Empty {
        view.player_turn_text_view.text = "Your opponent's turn"

        val cell = Cell(request.coord.y, request.coord.x)
        val imageView = boardViews[cell]

        imageView?.setImageDrawable(when (request.symbol) {
            Symbol.CROSS -> ContextCompat.getDrawable(view.context, R.drawable.ic_cross)
            else -> ContextCompat.getDrawable(view.context, R.drawable.ic_circle)
        })
        imageView?.isEnabled = false
        imageView?.animation = Animations.fadeIn(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                imageView?.visibility = View.VISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        return Empty.getDefaultInstance()
    }

    override suspend fun writePlaceholderFor(request: WriteIconMessage): Empty {
        view.player_turn_text_view.text = "Your turn"
        //a long press event handler should be added to empty cells (and removed when they are
        // filled) that shades the cell
        return Empty.getDefaultInstance()
    }

    override suspend fun writeStroke(request: WriteStrokeMessage): Empty {
        //TODO[optional] draw a line using a Canvas
        return Empty.getDefaultInstance()
    }

    override suspend fun writeMsg(request: TextMessage): Empty {
        //temporary
        //TODO test whether this is easily dismissible by the user
        Snackbar.make(view, request.value, Snackbar.LENGTH_LONG)
                .show()
        //TODO create a popup view
//        val popupView = LayoutInflater.from(view.context).inflate(R.layout.support_simple_spinner_dropdown_item, null)
//        popupView.findViewById<TextView>(R.id.name_field_text_view).text = request.value
//        val popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
//        popupWindow.showAsDropDown(popupView, 0, 0)

        return Empty.getDefaultInstance()
    }

    override suspend fun writeTempMsg(request: TextMessage): Empty {
        //TODO change to a popup window
        Snackbar.make(view, request.value,
                    TimeUnit.SECONDS.toMillis(1).toInt())
                .show()
        return Empty.getDefaultInstance()
    }

    override suspend fun flashPlaceholder(request: WriteIconMessage): Empty {
        val cell = Cell(request.coord.y, request.coord.x)
        val imageView = boardViews[cell]
        imageView?.setBackgroundColor(Color.MAGENTA)
        delay(500)
        imageView?.setBackgroundColor(Color.WHITE)
        return Empty.getDefaultInstance()
    }
}