package com.example.jamb.ui.main

import android.view.View
import android.widget.ImageView
import com.example.jamb.R
import com.example.jamb.ui.base.BaseActivity
import com.example.jamb.common.displayToast
import com.example.jamb.helper.ImageEditor
import com.example.jamb.model.DiceRoller
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    private var listOfClickedImages = mutableListOf(false, false, false, false, false, false)
    private var listOfValues = mutableListOf(0, 0, 0, 0, 0, 0)
    private var listOfImages = mutableListOf<ImageView>()
    private var startListOfImages = mutableListOf<ImageView>()
    private var counter = 0
    private val diceRoller = DiceRoller()

    override fun getLayoutResourceID(): Int = R.layout.activity_main

    override fun setUpUI() {
        ivImage1.setOnClickListener(this)
        ivImage2.setOnClickListener(this)
        ivImage3.setOnClickListener(this)
        ivImage4.setOnClickListener(this)
        ivImage5.setOnClickListener(this)
        ivImage6.setOnClickListener(this)
        btnShuffle.setOnClickListener(this)
        listOfImages.add(ivImage1)
        listOfImages.add(ivImage2)
        listOfImages.add(ivImage3)
        listOfImages.add(ivImage4)
        listOfImages.add(ivImage5)
        listOfImages.add(ivImage6)
        listOfImages.forEach { imageView: ImageView -> startListOfImages.add(imageView) }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            ivImage1.id -> {
                if (counter != 0){
                    listOfClickedImages[0] = !listOfClickedImages[0]
                    if (listOfClickedImages[0]){
                        ImageEditor.scaleImageToClicked(ivImage1)
                        listOfImages.remove(ivImage1)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage1)
                        listOfImages.add(ivImage1)
                    }
                    ImageEditor.sortImages(listOfImages)
                }
            }
            ivImage2.id -> {
                if (counter != 0){
                    listOfClickedImages[1] = !listOfClickedImages[1]
                    if (listOfClickedImages[1]){
                        ImageEditor.scaleImageToClicked(ivImage2)
                        listOfImages.remove(ivImage2)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage2)
                        listOfImages.add(ivImage2)
                    }
                    ImageEditor.sortImages(listOfImages)
                }

            }
            ivImage3.id -> {
                if (counter != 0){
                    listOfClickedImages[2] = !listOfClickedImages[2]
                    if (listOfClickedImages[2]){
                        ImageEditor.scaleImageToClicked(ivImage3)
                        listOfImages.remove(ivImage3)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage3)
                        listOfImages.add(ivImage3)
                    }
                    ImageEditor.sortImages(listOfImages)
                }

            }
            ivImage4.id -> {
                if (counter != 0){
                    listOfClickedImages[3] = !listOfClickedImages[3]
                    if (listOfClickedImages[3]){
                        ImageEditor.scaleImageToClicked(ivImage4)
                        listOfImages.remove(ivImage4)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage4)
                        listOfImages.add(ivImage4)
                    }
                    ImageEditor.sortImages(listOfImages)
                }

            }
            ivImage5.id -> {
                if (counter != 0){
                    listOfClickedImages[4] = !listOfClickedImages[4]
                    if (listOfClickedImages[4]){
                        ImageEditor.scaleImageToClicked(ivImage5)
                        listOfImages.remove(ivImage5)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage5)
                        listOfImages.add(ivImage5)
                    }
                    ImageEditor.sortImages(listOfImages)
                }

            }
            ivImage6.id -> {
                if (counter != 0){
                    listOfClickedImages[5] = !listOfClickedImages[5]
                    if (listOfClickedImages[5]){
                        ImageEditor.scaleImageToClicked(ivImage6)
                        listOfImages.remove(ivImage6)
                    }
                    else{
                        ImageEditor.scaleImageToNormal(ivImage6)
                        listOfImages.add(ivImage6)
                    }
                    ImageEditor.sortImages(listOfImages)
                }
            }
            btnShuffle.id -> {
                if (counter == 0){
                    listOfValues = diceRoller.getRollResults()
                    ImageEditor.changeAllImages(applicationContext, listOfImages, listOfValues)
                    counter++
                }

                if (counter == 1)   counter++

                else {
                    ImageEditor.changeImagesAfterSelection(applicationContext, listOfImages)
                    ImageEditor.scaleListOfImagesToNormal(startListOfImages)
                    ImageEditor.disableClickingOnImages(startListOfImages)
                    btnShuffle.isClickable = false
                    displayToast(getString(R.string.end))
                    displayToast(getString(R.string.restart))
                }
            }
        }
    }
}
