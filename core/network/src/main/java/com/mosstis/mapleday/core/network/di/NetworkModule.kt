package com.mosstis.mapleday.core.network.di

import com.mosstis.mapleday.core.network.BuildConfig
import com.mosstis.mapleday.core.network.nas.AppTokenInterceptor
import com.mosstis.mapleday.core.network.nexon.NexonDirectApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Qualifier
import javax.inject.Singleton

/** 넥슨 직접 호출용 (사용자 키). */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NexonClient

/** NAS 프록시 호출용 (앱 토큰). */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NasClient

/**
 * 넥슨 직접 / NAS는 OkHttp 인스턴스를 따로 둔다 (SPEC 5.2).
 * 로그에는 키 헤더를 남기지 않는다.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        redactHeader(NexonDirectApi.API_KEY_HEADER)
        redactHeader(AppTokenInterceptor.HEADER)
    }

    @Provides
    @Singleton
    @NexonClient
    fun provideNexonOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    @NexonClient
    fun provideNexonRetrofit(@NexonClient client: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(NexonDirectApi.BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(JSON_MEDIA_TYPE.toMediaType()))
            .build()

    @Provides
    @Singleton
    fun provideNexonDirectApi(@NexonClient retrofit: Retrofit): NexonDirectApi = retrofit.create()

    @Provides
    @Singleton
    @NasClient
    fun provideNasOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AppTokenInterceptor(BuildConfig.APP_TOKEN))
            .addInterceptor(logging)
            .build()

    @Provides
    @Singleton
    @NasClient
    fun provideNasRetrofit(@NasClient client: OkHttpClient, json: Json): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.NAS_BASE_URL)
            .client(client)
            .addConverterFactory(json.asConverterFactory(JSON_MEDIA_TYPE.toMediaType()))
            .build()

    private const val JSON_MEDIA_TYPE = "application/json"
}
