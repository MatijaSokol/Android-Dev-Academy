package com.example.bmicalculator.helper

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.widget.TextView
import com.example.bmicalculator.exception.BMIException
import com.example.bmicalculator.R

class BMICalculator {

    companion object{
        private const val BMI = "BMI: "

        fun getBMI(weight: Double, height: Double, context: Context): Double{
            if (weight < 0 || height < 0)
                throw BMIException(context.getString(R.string.incorrect_entry))
            if (weight > 350)
                throw BMIException(context.getString(R.string.weight_big))
            if (height > 2.5)
                throw BMIException(context.getString(R.string.height_big))
            return weight/(height*height)
        }

        fun showInformationByBMI(bmi: Double, image: ImageView, textView: TextView, context: Context){
            val shortBMI = Math.round(bmi * 100.0) / 100.0
            if (bmi > 0 && bmi <= 18.5){
                image.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.thin))
                val text = "$BMI$shortBMI - ${context.getString(
                    R.string.Thin
                )}"
                textView.text = text
            }
            if (bmi > 18.5 && bmi < 25){
                image.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.normal))
                val text = "$BMI$shortBMI - ${context.getString(
                    R.string.Normal
                )}"
                textView.text = text
            }
            if (bmi >= 25 && bmi < 30){
                image.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.fat))
                val text = "$BMI$shortBMI - ${context.getString(R.string.Fat)}"
                textView.text = text
            }
            if (bmi >= 30.0){
                image.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.obese))
                val text = "$BMI$shortBMI - ${context.getString(
                    R.string.Obese
                )}"
                textView.text = text
            }
        }
    }
}