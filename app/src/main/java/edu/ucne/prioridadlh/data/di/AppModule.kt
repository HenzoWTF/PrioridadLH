package edu.ucne.prioridadlh.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.prioridadlh.data.local.database.PrioridadesDb
import edu.ucne.prioridadlh.data.local.dao.PrioridadesDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PrioridadesDb {
        return Room.databaseBuilder(
            context,
            PrioridadesDb::class.java,
            "prioridades_database"
        ).build()
    }

    @Provides
    fun providePrioridadesDao(db: PrioridadesDb): PrioridadesDao {
        return db.prioridadesDao()
    }
}
