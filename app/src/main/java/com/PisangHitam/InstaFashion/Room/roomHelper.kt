package com.PisangHitam.InstaFashion.Room

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.PisangHitam.InstaFashion.classAccount
import com.PisangHitam.InstaFashion.classProduk

@Database(
    entities = [classAccount::class, classProduk::class],
    version = 5)
@TypeConverters(value = [converterCart::class, converterAddress::class, converterTrans::class, converterProduk::class])
abstract class roomHelper : RoomDatabase(){
    abstract fun daoAccount() : daoAccount
    abstract fun daoOutfitList() : daoOutfitList

}