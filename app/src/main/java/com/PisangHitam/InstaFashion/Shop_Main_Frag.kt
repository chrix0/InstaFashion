package com.PisangHitam.InstaFashion

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.PisangHitam.InstaFashion.classProduk
import io.github.yavski.fabspeeddial.FabSpeedDial
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter
import kotlinx.android.synthetic.main.fragment_shop__main_.*

import com.synnapps.carouselview.CarouselView
import android.widget.ImageView
import android.widget.Toast
import com.PisangHitam.InstaFashion.Room.daoOutfitList
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.fondesa.kpermissions.allDenied
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.extension.send
import com.squareup.picasso.Picasso

import com.synnapps.carouselview.ImageListener
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.support.v4.runOnUiThread
import org.jetbrains.anko.support.v4.supportFragmentUiThread
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Shop_Main_Frag : Fragment() {

    private lateinit var adapter : recycler_products_adapter

    //Library CarouselView
    //https://github.com/sayyam/carouselview
    var carouselView: CarouselView? = null
    var sampleImages = arrayOf(
        "https://i.pinimg.com/originals/fc/de/94/fcde946d572f8968cde14b688527021b.png",
        "https://purepng.com/public/uploads/large/landscape-prf.png",
        "https://cdn.pixabay.com/photo/2020/02/05/15/47/natural-4821583_960_720.png",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_shop__main_, container, false)
        return doSomething(v)
    }

    private fun doSomething(v: View): View {

        var helper : roomHelper = singletonData.getRoomHelper(requireContext())
        var dbOutfit : daoOutfitList? = helper.daoOutfitList()
        //RECYCLER VIEW
        val itemList = v.findViewById<RecyclerView>(R.id.itemList)

//        for(i in singletonData.outfitList){
//            dbOutfit!!.addOutfit(i)
//        }
        dbOutfit!!.addAllOutfit(singletonData.outfitList)

        var first5item : List<classProduk> = dbOutfit!!.getAllOutfit()

        //val first5item : List<classProduk> = singletonData.outfitList.take(5)
        Toast.makeText(context, first5item.size.toString(), Toast.LENGTH_SHORT)

        itemList.adapter = getAdapter(first5item)
        itemList.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.HORIZONTAL, false)

        //MORE BUTTON
        val more= v.findViewById<Button>(R.id.more)

        more.setOnClickListener {
            val more = Intent(requireContext(), shop_productList::class.java)
            more.putExtra(EXTRA_PRODUCT,singletonData.outfitList as ArrayList<classProduk>)
            more.putExtra(CHANGE_TITLE, "Product list")
            startActivity(more)
        }

        val speedDial : FabSpeedDial = v.findViewById(R.id.speedDial)

        speedDial.setMenuListener(object : SimpleMenuListenerAdapter() {
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.basket -> {
                        var keBasket = Intent(requireContext(), shop_basket::class.java)
                        startActivity(keBasket)
                    }
                    R.id.history -> {
                        var keHistory = Intent(requireContext(), shop_tracker::class.java)
                        startActivity(keHistory)
                    }
                }
                return false
            }
        })

        val analyzer = v.findViewById<Button>(R.id.analyzer)
        analyzer.setOnClickListener {
            permissionsBuilder(Manifest.permission.CAMERA).build().send() {
                    result ->
                if (result.allDenied()){
                    Toast.makeText(requireContext(), "Permission denied. Unable to continue", Toast.LENGTH_SHORT).show()
                }
                else if (result.allGranted()){
                    Toast.makeText(requireContext(), "Permission granted.", Toast.LENGTH_SHORT).show()
                    var intent = Intent(requireContext(), oa_container::class.java)
                    startActivity(intent)
                }
            }
        }

        //Code untuk CarouselView
        var carouselView = v.findViewById<CarouselView>(R.id.carouselView)
        carouselView.setPageCount(sampleImages.size);

        var imageListener =
            ImageListener { position, imageView ->
                Picasso.get().load(sampleImages[position]).fit().centerCrop().into(imageView)
            }

        carouselView.setImageListener(imageListener);


        //Category Buttons
        val catShirt = v.findViewById<Button>(R.id.catShirt)
        val catTrousers = v.findViewById<Button>(R.id.catTrousers)
        val catShoes = v.findViewById<Button>(R.id.catShoes)
        val catAccess = v.findViewById<Button>(R.id.catAccess)
        val catJacket = v.findViewById<Button>(R.id.catJacket)

        catShirt.setOnClickListener {
            adapter = getAdapter(dbOutfit!!.getOutfitLimited("shirt", 5))
            itemList.adapter = adapter
        }

        catTrousers.setOnClickListener {
            adapter = getAdapter(dbOutfit!!.getOutfitLimited("trousers", 5))
            itemList.adapter = adapter
        }

        catShoes.setOnClickListener {
            adapter = getAdapter(dbOutfit!!.getOutfitLimited("shoes", 5))
            itemList.adapter = adapter
        }

        catAccess.setOnClickListener {
            adapter = getAdapter(dbOutfit!!.getOutfitLimited("access", 5))
            itemList.adapter = adapter
        }

        catJacket.setOnClickListener {
            adapter = getAdapter(dbOutfit!!.getOutfitLimited("jacket", 5))
            itemList.adapter = adapter
        }

        return v
    }

    private fun getAdapter(list : List<classProduk>) : recycler_products_adapter{
        adapter = recycler_products_adapter(list){
            val info = Intent(requireContext(), shop_infoProduk::class.java)
            info.putExtra(SHOW_PRODUCT_INFO, it)
            info.putExtra(CHANGE_TITLE,"Product Info")
            startActivity(info)
        }
        return adapter
    }

    companion object {

    }
}