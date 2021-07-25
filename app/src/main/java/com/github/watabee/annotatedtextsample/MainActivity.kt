package com.github.watabee.annotatedtextsample

import android.os.Bundle
import android.text.Annotation
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannedString
import android.text.style.ImageSpan
import android.text.style.TextAppearanceSpan
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.getSpans
import androidx.databinding.BindingAdapter
import com.github.watabee.annotatedtextsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.annotatedTextResId = R.string.annotated_text
        setContentView(binding.root)
    }

}

@BindingAdapter("annotatedText")
fun setAnnotatedText(textView: TextView, @StringRes textResId: Int) {
    val context = textView.context
    val annotatedText = context.getText(textResId) as SpannedString
    val annotations = annotatedText.getSpans<Annotation>(0, annotatedText.length)
    val spannableString = SpannableString(annotatedText)
    annotations.forEach {
        when (it.key) {
            "image" -> {
                val resId = context.resources.getIdentifier(it.value, "drawable", context.packageName)
                if (resId != 0) {
                    spannableString.setSpan(
                        ImageSpan(context, resId),
                        annotatedText.getSpanStart(it),
                        annotatedText.getSpanEnd(it),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            "text-appearance" -> {
                val resId = context.resources.getIdentifier(it.value, "style", context.packageName)
                if (resId != 0) {
                    spannableString.setSpan(
                        TextAppearanceSpan(context, resId),
                        annotatedText.getSpanStart(it),
                        annotatedText.getSpanEnd(it),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

    textView.text = spannableString
}
