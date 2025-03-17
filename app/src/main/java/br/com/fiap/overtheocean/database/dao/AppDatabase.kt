package br.com.fiap.overtheocean.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.fiap.overtheocean.model.Relatorio
import br.com.fiap.overtheocean.model.TipoRelatorio
import java.util.Date

@Database(entities = [Relatorio::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun relatorioDao(): RelatorioDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// Converters para tipos complexos
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromTipoRelatorio(tipo: TipoRelatorio): String {
        return tipo.name
    }

    @TypeConverter
    fun toTipoRelatorio(value: String): TipoRelatorio {
        return TipoRelatorio.valueOf(value)
    }
}