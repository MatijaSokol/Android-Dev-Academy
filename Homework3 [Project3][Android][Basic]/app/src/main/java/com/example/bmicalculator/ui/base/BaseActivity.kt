package com.example.bmicalculator.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResourceID())
        setUpUI()
    }

    abstract fun getLayoutResourceID(): Int
    abstract fun setUpUI()
}