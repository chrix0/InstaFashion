package com.PisangHitam.InstaFashion.Room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classProduk

@Database(
    entities = [classAccount::class, classProduk::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration (from = 2, to = 3)
    ])
@TypeConverters(value = [converterCart::class, converterAddress::class, converterTrans::class])
abstract class roomHelper : RoomDatabase(){
    abstract fun daoAccount() : daoAccount
    abstract fun daoOutfitList() : daoOutfitList
}