package eu.kanade.tachiyomi.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import eu.kanade.tachiyomi.R
import eu.kanade.tachiyomi.databinding.CommonViewEmptyBinding
import kotlin.random.Random

class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    RelativeLayout(context, attrs) {

    private val binding: CommonViewEmptyBinding =
        CommonViewEmptyBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Hide the information view
     */
    fun hide() {
        this.isVisible = false
    }

    /**
     * Show the information view
     * @param textResource text of information view
     */
    fun show(@StringRes textResource: Int, actions: List<Action>? = null) {
        show(context.getString(textResource), actions)
    }

    fun show(message: String, actions: List<Action>? = null) {
        binding.textFace.text = getRandomErrorFace()
        binding.textLabel.text = message

        binding.actionsContainer.removeAllViews()
        actions?.forEach {
            val button = MaterialButton(ContextThemeWrapper(context, R.style.Theme_Widget_Button_Action)).apply {
                layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f / actions.size
                )

                backgroundTintList = ColorStateList.valueOf(Color.TRANSPARENT)
                stateListAnimator = null
                elevation = 0f

                setIconResource(it.iconResId)
                setText(it.stringResId)

                setOnClickListener(it.listener)
            }

            binding.actionsContainer.addView(button)
        }

        this.isVisible = true
    }

    companion object {
        private val ERROR_FACES = listOf(
            "(???o???;)",
            "??(???_???)",
            "???_???",
            "(?????_?????)",
            "(???????????)",
            "(???????????"
        )

        fun getRandomErrorFace(): String {
            return ERROR_FACES[Random.nextInt(ERROR_FACES.size)]
        }
    }

    data class Action(
        @StringRes val stringResId: Int,
        @DrawableRes val iconResId: Int,
        val listener: OnClickListener
    )
}
