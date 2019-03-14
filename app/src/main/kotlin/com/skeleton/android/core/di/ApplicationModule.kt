package com.skeleton.android.core.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.skeleton.android.AndroidApplication
import com.skeleton.android.BuildConfig
import com.skeleton.android.features.chat.ChatsRepository
import com.skeleton.android.features.user.AuthenticationRepository
import com.skeleton.android.features.user.UserRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .client(createClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideFirebaseStore(): FirebaseFirestore {
        val firebaseDatabase = FirebaseFirestore.getInstance()
        return firebaseDatabase
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserRepository.Network): UserRepository= dataSource

    @Provides
    @Singleton
    fun provideAuthenticationRepository(dataSource: AuthenticationRepository.Network): AuthenticationRepository = dataSource

    @Provides
    @Singleton
    fun provideChatsRepository(dataSource: ChatsRepository.Network): ChatsRepository = dataSource
}
