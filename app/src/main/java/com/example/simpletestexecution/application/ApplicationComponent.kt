package com.example.simpletestexecution.application

import android.content.Context
import com.example.moviedb.di.modules.NetworkModule
import com.example.simpletestexecution.AppRepositoryImpl
import com.example.simpletestexecution.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}