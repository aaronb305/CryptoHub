package com.example.cryptohub.di

import com.example.cryptohub.rest.CoinApiRepository
import com.example.cryptohub.rest.CoinApiRepositoryImpl
import com.example.cryptohub.rest.CoinGeckoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import kotlin.math.log

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun providesLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun providesOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) : OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

    @Provides
    fun providesCoinGeckoApi(okHttpClient: OkHttpClient) : CoinGeckoApi =
        Retrofit.Builder()
            .baseUrl(CoinGeckoApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CoinGeckoApi::class.java)

    @Provides
    fun providesCoinApiRepository(coinGeckoApi: CoinGeckoApi) : CoinApiRepository =
        CoinApiRepositoryImpl(coinGeckoApi)

    @Provides
    fun providesDispatcher() : CoroutineDispatcher = Dispatchers.IO
}