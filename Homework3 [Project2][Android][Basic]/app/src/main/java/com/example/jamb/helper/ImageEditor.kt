package com.example.jamb.helper

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.example.jamb.R

class ImageEditor {

    companion object{
        fun scaleImageToNormal(image: ImageView){
            image.scaleX = 1F
            image.scaleY = 1F
        }

        fun scaleImageToClicked(image: ImageView){
            image.scaleX = 0.75F
            image.scaleY = 0.75F
        }

        fun scaleListOfImagesToNormal(listOfImages: List<ImageView>){
            listOfImages.forEach {
                it.scaleX = 1F
                it.scaleY = 1F
            }
        }

        fun sortImages(listOfImages: List<ImageView>){
            listOfImages.sortedBy { imageView -> imageView.id }
        }

        fun changeAllImages(context: Context, listOfImages: List<ImageView>, listOfValues: List<Int>){
            for(i in 0..5){
                when(listOfValues[i]){
                    1 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_1
                    ))
                    2 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_2
                    ))
                    3 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_3
                    ))
                    4 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_4
                    ))
                    5 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_5
                    ))
                    6 -> listOfImages[i].setImageDrawable(ContextCompat.getDrawable(context,
                        R.mipmap.die_6
                    ))
                }
            }
        }

        fun disableClickingOnImages(listOfImages: List<ImageView>){
            listOfImages.forEach { it.isClickable = false }
        }

        fun changeImagesAfterSelection(context: Context, listOfImages: List<ImageView>){
            listOfImages.forEach {
                when((1..6).random()){
                    1 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_1))
                    2 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_2))
                    3 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_3))
                    4 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_4))
                    5 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_5))
                    6 -> it.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.die_6))
                }
            }
        }
    }
}