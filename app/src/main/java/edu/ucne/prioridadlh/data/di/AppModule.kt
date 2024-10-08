package edu.ucne.prioridadlh.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prioridadlh.data.Remote.API.ClienteApi
import edu.ucne.prioridadlh.data.Remote.API.PrioridadesApi
import edu.ucne.prioridadlh.data.Remote.API.SistemasApi
import edu.ucne.prioridadlh.data.Remote.API.TicketsApi
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    const val BASE_URL = "https://aplicada2-api-h3bvdff3fxbqe2cv.eastus2-01.azurewebsites.net/"

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesTicket(moshi: Moshi): TicketsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TicketsApi::class.java)

    }

    @Provides
    @Singleton
    fun providesPrioridadesApi(moshi: Moshi): PrioridadesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadesApi::class.java)
    }

    @Provides
    @Singleton
    fun providesClientesApi(moshi: Moshi): ClienteApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ClienteApi::class.java)
    }

    @Provides
    @Singleton
    fun providesSistemaApi(moshi: Moshi): SistemasApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(SistemasApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PrioridadesDb {
        return Room.databaseBuilder(
            context,
            PrioridadesDb::class.java,
            "prioridades_database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePrioridadesDao(db: PrioridadesDb): PrioridadesDao {
        return db.prioridadesDao()
    }

    @Provides
    @Singleton
    fun provideTicketDao(prioridadDb: PrioridadesDb) = prioridadDb.ticketDao()
}
