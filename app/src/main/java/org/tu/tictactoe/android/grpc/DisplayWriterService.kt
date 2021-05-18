package org.tu.tictactoe.android.grpc

import android.graphics.Color
import android.view.View
import android.view.animation.Animation
import android.widget.PopupWindow
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

    private val tableMap: Map<Cell, AppCompatImageView> = mapOf(
            Pair(Cell(0, 0), view.section_top_start),
            Pair(Cell(0, 1), view.section_top_middle),
            Pair(Cell(0, 2), view.section_top_end),
            Pair(Cell(1, 0), view.section_middle_start),
            Pair(Cell(1, 1), view.section_middle_middle),
            Pair(Cell(1, 1), view.section_middle_end),
            Pair(Cell(2, 0), view.section_bottom_start),
            Pair(Cell(2, 1), view.section_bottom_middle),
            Pair(Cell(2, 2), view.section_bottom_end)
    )

    override suspend fun writeGrid(request: Empty): Empty {
        for (value in tableMap.values) {
            value.setImageDrawable(null)
        }
        return Empty.getDefaultInstance()
    }

    override suspend fun clearCellAt(request: CoordinateOuterClass.Coordinate): Empty {
        val cell = Cell(request.y, request.x)
        tableMap[cell]?.setImageDrawable(null)
        return Empty.getDefaultInstance()
    }

    override suspend fun writeSymbol(request: WriteIconMessage): Empty {
        val cell = Cell(request.coord.y, request.coord.x)
        val imageView = tableMap[cell]

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
        //This should be a no-op BUT
        // a long press event handler should be added to empty cells (and removed when they are
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
        Snackbar.make(view.rootView, request.value, Snackbar.LENGTH_LONG)
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
        Snackbar.make(view.rootView, request.value,
                    TimeUnit.SECONDS.toMillis(1).toInt())
                .show()
        return Empty.getDefaultInstance()
    }

    override suspend fun flashPlaceholder(request: WriteIconMessage): Empty {
        val cell = Cell(request.coord.y, request.coord.x)
        val imageView = tableMap[cell]
        imageView?.setBackgroundColor(Color.MAGENTA)
        delay(500)
        imageView?.setBackgroundColor(Color.WHITE)
        return Empty.getDefaultInstance()
    }
}