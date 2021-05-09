package com.example.simpletestexecution

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(movieVMFactory: MovieVMFactory): ViewModelProvider.Factory
}
