package com.example.psycareapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.psycareapp.R

class NoBorderEditText: AppCompatEditText {
    private lateinit var backgroundEditText: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        backgroundEditText =
            ContextCompat.getDrawable(context, R.drawable.bg_edittext_noborder) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, change: Int) {
                if (text.toString().isNotEmpty() && text!!.length > 500) {
                    error = "max 500 Characters"
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = context.getString(R.string.discussion_hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        background = backgroundEditText
    }
}