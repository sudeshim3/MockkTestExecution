package com.example.simpletestexecution.application

import android.content.Context
import com.example.moviedb.di.modules.NetworkModule
import com.example.simpletestexecution.NumberApp
import com.example.simpletestexecution.ViewModelFactoryModule
import com.example.simpletestexecution.modules.ActivityBuilderModule
import com.example.simpletestexecution.modules.AppModule
import com.example.simpletestexecution.modules.FragmentBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AppModule::class,
        ViewModelFactoryModule::class,
        FragmentBuilderModule::class,
        AndroidInjectionModule::class,
        ActivityBuilderModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<NumberApp> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun provideContext(context: Context): Builder

        fun build(): ApplicationComponent
    }
}
