package com.PisangHitam.InstaFashion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_shop_product_list.*

class Wishlist_Main_Frag : Fragment() {

    private lateinit var adapter : recycler_products_adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_wishlist__main_, container, false)
        return doSomething(v)
    }

    private fun doSomething(v: View): View {
        //RECYCLER VIEW
        val wishList = v.findViewById<RecyclerView>(R.id.wishList)
        val wishList_List : List<classProduk> = singletonData.petOutfitList.take(5)

        adapter = recycler_products_adapter(wishList_List){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,"Product Info")
            startActivity(info)
        }

        wishList.layoutManager = GridLayoutManager(requireContext(), 2)
        wishList.adapter = adapter

        return v
    }


}