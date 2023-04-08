package ru.asmelnikov.inspirationpointtest.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.asmelnikov.inspirationpointtest.data.AppDatabase
import ru.asmelnikov.inspirationpointtest.data.ReceivedMessageRepositoryImpl
import ru.asmelnikov.inspirationpointtest.data.SentMessageRepositoryImpl
import ru.asmelnikov.inspirationpointtest.domain.repository.ReceivedMessagesRepository
import ru.asmelnikov.inspirationpointtest.domain.repository.SentMessageRepository
import ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages.GetAllReceivedMessagesUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages.InsertReceivedMessageUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.received_messages.ReceivedMessagesUseCases
import ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages.GetAllSentMessagesUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages.InsertSentMessageUseCase
import ru.asmelnikov.inspirationpointtest.domain.usecases.sent_messages.SentMessageUseCases
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
    fun provideReceivedRepository(db: AppDatabase): ReceivedMessagesRepository {
        return ReceivedMessageRepositoryImpl(db.receivedMessageDao())
    }

    @Provides
    @Singleton
    fun provideSentUseCases(repository: SentMessageRepository): SentMessageUseCases {
        return SentMessageUseCases(
            InsertSentMessageUseCase(repository),
            GetAllSentMessagesUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun providesReceivedUseCase(repository: ReceivedMessagesRepository): ReceivedMessagesUseCases {
        return ReceivedMessagesUseCases(
            InsertReceivedMessageUseCase(repository),
            GetAllReceivedMessagesUseCase(repository)
        )
    }
}