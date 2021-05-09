package com.example.simpletestexecution

import com.example.simpletestexecution.application.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class NumberApp: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val component = DaggerApplicationComponent
            .builder()
            .provideContext(this).build()
        component.inject(this)
        return component
    }
}