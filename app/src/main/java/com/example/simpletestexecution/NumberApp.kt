package com.example.simpletestexecution

import android.app.Application
import com.example.simpletestexecution.application.DaggerApplicationComponent

class NumberApp: Application() {

    val applicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}