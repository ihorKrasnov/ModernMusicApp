import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Створення нової таблиці для коментарів з зовнішнім ключем
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS `comments` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `albumId` INTEGER NOT NULL,
                `name` TEXT NOT NULL,
                `text` TEXT NOT NULL,
                FOREIGN KEY(`albumId`) REFERENCES `albums`(`id`) ON DELETE CASCADE
            )
        """)
    }
}