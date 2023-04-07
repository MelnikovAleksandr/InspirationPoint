package ru.asmelnikov.inspirationpointtest.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.asmelnikov.inspirationpointtest.data.AppDatabase
import ru.asmelnikov.inspirationpointtest.data.SentMessageRepositoryImpl
import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import ru.asmelnikov.inspirationpointtest.domain.usecases.GetAllSentMessagesUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.InsertSentMessageUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.SentMessageUseCases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSentRepository(db: AppDatabase): SentMessageRepository {
        return SentMessageRepositoryImpl(db.sentMessageDao())
    }

    @Provides
    @Singleton
    fun provideSentUseCases(repository: SentMessageRepository): SentMessageUseCases {
        return SentMessageUseCases(
            InsertSentMessageUseCase(repository),
            GetAllSentMessagesUseCase(repository)
        )
    }

}