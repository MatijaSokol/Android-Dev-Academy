package com.example.bmicalculator.ui.main

import android.view.View
import com.example.bmicalculator.helper.BMICalculator
import com.example.bmicalculator.exception.BMIException
import com.example.bmicalculator.ui.base.BaseActivity
import com.example.bmicalculator.R
import com.example.bmicalculator.common.displayToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), View.OnClickListener {
    override fun getLayoutResourceID(): Int = R.layout.activity_main

    override fun setUpUI() {
        btnCalculate.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            btnCalculate.id -> {
                try {
                    if (etHeight.text.toString().isNotBlank() && etWidth.text.toString().isNotBlank()){
                        val height = etHeight.text.toString().toDouble()
                        val weight = etWidth.text.toString().toDouble()
                        val bmi = BMICalculator.getBMI(weight, height, applicationContext)
                        BMICalculator.showInformationByBMI(bmi, ivImage, tvDescription, applicationContext)
                    }
                    else    displayToast(getString(R.string.enter_values))
                }
                catch (ex: BMIException){
                    displayToast(ex.message.toString())
                }
            }
        }
    }

}
