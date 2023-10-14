package com.example.todoo.module

import android.app.Application
import androidx.room.Room
import com.example.todoo.db.ToDooDataBase
import com.example.todoo.repository.ToDoRepositoryImpl
import com.example.todoo.repository.ToDooRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideToDoDatabase(app: Application): ToDooDataBase {
        return Room.databaseBuilder(
            app, ToDooDataBase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideToDoRepository(db: ToDooDataBase): ToDooRepository {
        return ToDoRepositoryImpl(db.dao)
    }
}