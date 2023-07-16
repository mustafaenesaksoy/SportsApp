package com.enesaksoy.sportsapp.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.enesaksoy.sportsapp.R
import com.enesaksoy.sportsapp.service.RetrofitApi
import com.enesaksoy.sportsapp.repo.SportsRepository
import com.enesaksoy.sportsapp.repo.SportsRepositoryImpl
import com.enesaksoy.sportsapp.service.NewsDao
import com.enesaksoy.sportsapp.service.NewsDatabase
import com.enesaksoy.sportsapp.util.BASE_URL
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun injectAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun injectRetrofit() : RetrofitApi{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitApi::class.java)
    }

    @Provides
    @Singleton
    fun injectRepo(retrofitApi: RetrofitApi, newsDao: NewsDao,auth: FirebaseAuth) : SportsRepository{
        return SportsRepositoryImpl(retrofitApi,newsDao,auth)
    }

    @Provides
    @Singleton
    fun injectDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(
        context,
        NewsDatabase::class.java,
        "RoomDB")
        .build()

    @Provides
    @Singleton
    fun injectDao(database : NewsDatabase) = database.newsDao()

    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
        )
}