package com.hrizvi.multimodulenewsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [NewsArticleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}