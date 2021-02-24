package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private var progressRight = 0f

    private val textPosition = PointF(0.0f, 0.0f)

    private var beforeLoadBackground = 0
    private var afterLoadBackground = 0
    private var textColor = 0

    init {
        isClickable = true

        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
            beforeLoadBackground = getColor(R.styleable.LoadingButton_beforeLoadBackground, 0)
            afterLoadBackground = getColor(R.styleable.LoadingButton_afterLoadBackground, 0)
            textColor = getColor(R.styleable.LoadingButton_textColor, 0)
        }
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textAlign = Paint.Align.CENTER
        textSize = resources.getDimensionPixelSize(R.dimen.default_text_size).toFloat()
        color = textColor
    }

    private val progressPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = afterLoadBackground
    }

    private val valueAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable(ButtonState.Completed) { p, old: ButtonState, new: ButtonState ->
        when (new) {
            ButtonState.Loading -> {
                showLoading()
            }
            ButtonState.Completed -> {
                hideLoading()
            }
            ButtonState.Clicked -> {
            }
        }
    }

    private fun showLoading() {
        valueAnimator.apply {
            setFloatValues(widthSize.toFloat())
            duration = 5000
            addUpdateListener {
                progressRight = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun hideLoading() {
        valueAnimator.end()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(beforeLoadBackground)
        canvas.drawRect(0f, 0f, progressRight, heightSize.toFloat(), progressPaint)
        val text = resources.getString(R.string.download)
        canvas.drawText(text, textPosition.x, textPosition.y, textPaint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)

        textPosition.x = (widthSize / 2).toFloat()
        textPosition.y = heightSize / 2 - (textPaint.descent() + textPaint.ascent()) / 2
    }

}