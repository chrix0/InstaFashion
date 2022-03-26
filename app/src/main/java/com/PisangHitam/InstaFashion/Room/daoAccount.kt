package com.PisangHitam.InstaFashion.Room

import androidx.room.*
import com.PisangHitam.InstaFashion.classAccount

@Dao
interface daoAccount {
    //SELECT
    @Query("Select * from classAccount")
    fun getAllAcc() : List<classAccount>

    @Query("Select * from classAccount where COLUMN_USERNAME = :username and COLUMN_PASS = :password")
    fun getAccVerify(username : String, password : String) : classAccount

    @Query("Select * from classAccount where COLUMN_USERNAME = :username and COLUMN_PASS = :password")
    fun getAcc(username : String, password : String) : classAccount

    //INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun newAcc(acc : classAccount)

    //UPDATE (UPDATE PAS NAMBAH CART, TRANSACTION, UBAH ALAMAT, DLL. ME AGAK LUPA)
    @Update
    fun updateAcc(acc : classAccount) //Updatenya berdasarkan primary key di objek acc itu

}