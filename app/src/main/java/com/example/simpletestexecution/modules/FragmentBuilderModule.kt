package com.example.simpletestexecution.modules

import com.example.simpletestexecution.ui.HomeFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {


    @ContributesAndroidInjector
    abstract fun provideHomeFragment(): HomeFragment
}