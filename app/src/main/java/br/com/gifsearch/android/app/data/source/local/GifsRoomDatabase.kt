package br.com.gifsearch.android.app.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.gifsearch.android.app.data.source.local.dao.GifDao
import br.com.gifsearch.android.app.data.source.local.entity.GifEntity

// Annotates class to be a Room Database with a table (entity) of the GifEntity class
@Database(entities = arrayOf(GifEntity::class), version = 1, exportSchema = false)
public abstract class GifsRoomDatabase : RoomDatabase() {

    abstract fun gifDao(): GifDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: GifsRoomDatabase? = null

        fun getDatabase(context: Context): GifsRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GifsRoomDatabase::class.java,
                    "gifs_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}