package com.PisangHitam.InstaFashion.Room

import androidx.room.*
import com.PisangHitam.InstaFashion.classAccount

@Dao
interface daoAccount {
    //SELECT
    @Query("Select * from classAccount")
    fun getAllAcc() : List<classAccount>

    @Query("Select * from classAccount where COLUMN_USERNAME = :username and COLUMN_PASS = :password")
    fun getAcc(username : String, password : String) : List<classAccount>

    @Query("Select * from classAccount where COLUMN_USERNAME = :username")
    fun getAccUserCheck(username : String) : List<classAccount>

    @Query("Select * from classAccount where id = :id")
    fun getAccById(id: Int) : List<classAccount>

    //INSERT
    @Insert
    fun newAcc(acc : classAccount)

    //UPDATE (UPDATE PAS NAMBAH CART, TRANSACTION, UBAH ALAMAT, DLL. ME AGAK LUPA)
    @Update
    fun updateAcc(acc : classAccount) //Updatenya berdasarkan primary key di objek acc itu

}