package com.example.calculator

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), OnSelectedButtonListener {
    override fun onButtonSelected(buttonIndex: Int) {
        val fragmentManager = supportFragmentManager
            val fragment2 =
                fragmentManager.findFragmentById(R.id.fragmentContainerView3) as DataFragment?
            fragment2?.setDescription(buttonIndex)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}