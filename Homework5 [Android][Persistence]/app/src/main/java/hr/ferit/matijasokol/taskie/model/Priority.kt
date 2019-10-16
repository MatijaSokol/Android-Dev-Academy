package hr.ferit.matijasokol.taskie.model

import androidx.annotation.ColorRes
import hr.ferit.matijasokol.taskie.R

enum class Priority(@ColorRes private val colorRes: Int, private val value: Int) {
    LOW(R.color.colorLow, 2),
    MEDIUM(R.color.colorMedium, 1),
    HIGH(R.color.colorHigh, 0);

    fun getColor(): Int = colorRes

    fun getValue(): Int = value
}