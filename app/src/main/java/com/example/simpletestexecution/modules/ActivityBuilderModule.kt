package com.example.simpletestexecution.modules

import com.example.simpletestexecution.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun provideHomeActivity(): MainActivity
}
