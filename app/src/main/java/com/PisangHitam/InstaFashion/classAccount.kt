package com.PisangHitam.InstaFashion

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.PisangHitam.InstaFashion.Room.converterAddress
import com.PisangHitam.InstaFashion.Room.converterCart
import com.PisangHitam.InstaFashion.Room.converterTrans
import kotlinx.android.parcel.Parcelize

//Composite key
@Entity(primaryKeys = ["COLUMN_ID", "COLUMN_USERNAME"]) @Parcelize
data class classAccount(
    @ColumnInfo(name = "COLUMN_ID") @NonNull
    var id : Int,
    @ColumnInfo(name = "COLUMN_USERNAME") @NonNull
    var userName : String = "",
    @ColumnInfo(name = "COLUMN_PASS") var password : String = "",
    @ColumnInfo(name = "COLUMN_FULLNAME") var fullName : String = "",
    @ColumnInfo(name = "COLUMN_EMAIL") var email : String = "",
    @ColumnInfo(name = "COLUMN_CART") @TypeConverters(converterCart::class)
    var cartContent : MutableList<classItemBasket> = mutableListOf(),
    @ColumnInfo(name = "COLUMN_ADDRESS") @TypeConverters(converterAddress::class)
    var shippingAddress : MutableList<String> = mutableListOf(),
    @ColumnInfo(name = "COLUMN_PHONE") var phoneNumber: String  = "",
    @ColumnInfo(name = "COLUMN_TRANSACTION") @TypeConverters(converterTrans::class)
    var transactionHistory : MutableList<classTransaction> = mutableListOf()
) : Parcelable