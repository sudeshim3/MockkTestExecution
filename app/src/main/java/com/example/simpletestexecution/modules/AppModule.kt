package com.example.simpletestexecution.modules

import com.example.simpletestexecution.AppRepository
import com.example.simpletestexecution.AppRepositoryImpl
import com.example.simpletestexecution.datasource.RemoteDataSource
import com.example.simpletestexecution.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun provideAppRepository(appRepositoryImpl: AppRepositoryImpl): AppRepository
}