package com.PisangHitam.InstaFashion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_shop_info_produk.*
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

class shop_infoProduk : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_info_produk)

        val intentData = intent

        //Membuat action bar secara manual dengan tombol up
        val actionbar = supportActionBar
        actionbar!!.title = intentData.getStringExtra(CHANGE_TITLE)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var db = singletonData.getRoomHelper(this)
        var user = singletonData.getCurUserObj(this)
        val produk : classProduk = intentData.getParcelableExtra<classProduk>(SHOW_PRODUCT_INFO) as classProduk

        Picasso.get().load(produk.urlGambarProduk).into(productImg)
        name.text = produk.namaProduk
        price.text = "Rp." + singletonData.formatHarga(produk.hargaProduk)
        infoText.text = produk.description
        Quantity.text = "1"

        addToWish.isChecked = checkWished(produk) != -1

        addToCart.setOnClickListener {
            if(user != null){
                val info = Intent(this, shop_basket::class.java)
                var itemBasket = classItemBasket(
                    produk.idProduk,
                    produk.namaProduk,
                    produk.hargaProduk,
                    produk.urlGambarProduk,
                    produk.description,
                    Quantity.text.toString().toInt()
                )
                info.putExtra(ADD_TO_CART, itemBasket)
                startActivity(info)
            }
            else{
                Toast.makeText(this, "Please login.", Toast.LENGTH_SHORT).show()
                var toLogin = Intent(this, LoginActivity::class.java)
                startActivity(toLogin)
            }
        }

        buttonInc.setOnClickListener{
            val before = Quantity.text.toString().toInt()
            var after = before + 1
            Quantity.setText(after.toString())
        }

        buttonDec.setOnClickListener{
            val before = Quantity.text.toString().toInt()
            if(before > 1) {
                var after = before - 1
                Quantity.setText(after.toString())
            }
        }

        addToWish.setOnCheckedChangeListener { compoundButton, checked ->
            if(checked){
                user!!.wishlist.add(produk)
                db.daoAccount().updateAcc(user!!)
                user = singletonData.getCurUserObj(this)
            }
            else{
                user!!.wishlist.removeAt(checkWished(produk))
                db.daoAccount().updateAcc(user!!)
                user = singletonData.getCurUserObj(this)
            }
        }
    }
    //Back ketika menekan tombol up
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun checkWished(produk : classProduk): Int{
        var user = singletonData.getCurUserObj(this)
        var wishlist = user!!.wishlist

        for(i in 0 until wishlist.size){
             if (wishlist[i].idProduk == produk.idProduk)
                 return i
        }
        return -1
    }
}