package hr.ferit.matijasokol.taskie.model

import androidx.annotation.ColorRes
import hr.ferit.matijasokol.taskie.R

enum class BackendPriorityTask(@ColorRes private val colorRes: Int, private val num: Int) {
    LOW(R.color.colorLow, 1),
    MEDIUM(R.color.colorMedium, 2),
    HIGH(R.color.colorHigh, 3);

    fun getColor(): Int = colorRes

    fun getValue(): Int = num
}