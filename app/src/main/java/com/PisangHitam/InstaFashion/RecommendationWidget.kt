package com.PisangHitam.InstaFashion

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.icu.number.NumberFormatter.with
import android.icu.number.NumberRangeFormatter.with
import android.net.Uri
import android.widget.RemoteViews
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.SharedPref.dbSharedPref
import com.squareup.picasso.Picasso

/**
 * Implementation of App Widget functionality.
 */
class RecommendationWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
    var produk: List<classProduk> = listOf(classProduk(
        1,
        "Original levis shirt grid pattern",
        188000,
        "https://media.karousell.com/media/photos/products/2018/12/21/levis_original_for_women__not_uniqlo_second_kemeja_second_lecis_second_zara_second_gucci_second_foss_1545358303_c94dcaae.jpg",
        "shirt",
        "Original levis shirt with grid pattern, used once or twice for attending bussiness. \nColor : Dark blue \nsize : XL"
    ),
        classProduk(
            2,
            "Original supreme T-shirts Futura Logo Black Tee - M",
            300000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2020/12/18/7be9e0a1-b70e-4f97-b06a-0d92111671f4.jpg",
            "shirt",
            "Original supreme T-shirts with Futura Logo Black Tee. \nColor : Black \nSize : M."
        ),
        classProduk(
            3,
            "Premium jeans slim fit blue original",
            150000,
            "https://images.tokopedia.net/img/cache/900/product-1/2020/2/21/2288057/2288057_ec16ca5b-3a3d-46ba-9f31-2afda5ba5e11_600_600",
            "trousers",
            "Premium jeans slim fit blue original. \nColor : Blue \nsize : 29 (74x78x97)cm"
        ),
        classProduk(
            4,
            "Adidas Response Run Men's Running Shoes - Black - Black, UK 9",
            400000,
            "https://images.tokopedia.net/img/cache/900/hDjmkQ/2021/9/13/95fab2a9-4104-4d99-9d52-f245002abd18.jpg",
            "shoes",
            "Adidas Response Run Men's Running Shoes. \nColor : Black \nsize : UK - 9 (27.5cm)"
        ),
        classProduk(
            5,
            "Topi RVCA Flexfit Original Shane Flexfit Cap Stone - S",
            170000,
            "https://images.tokopedia.net/img/cache/900/VqbcmM/2022/3/7/26bf641e-d78b-4ef6-a6a7-6af1fc645170.jpg",
            "access",
            "RVCA Flexfit Original Shane Flexfit Cap Stone. \nMaterial: 70% Polyester; 25% Viscose; 5% Elastane. \nColor : Gray, size : S(54cm)"
        ))

    val views = RemoteViews(context.packageName, R.layout.recommendation_widget).apply {
        Picasso.get().load(produk[0].urlGambarProduk).into(this,R.id.photo, IntArray(appWidgetId))
        setTextViewText(R.id.name, produk[0].namaProduk)
        setTextViewText(R.id.description1, produk[0].description)
        setTextViewText(R.id.price1, (produk[0].hargaProduk).toString())
        Picasso.get().load(produk[1].urlGambarProduk).into(this,R.id.photo2, IntArray(appWidgetId))
        setTextViewText(R.id.name2, produk[1].namaProduk)
        setTextViewText(R.id.description2, produk[1].description)
        setTextViewText(R.id.price2, (produk[1].hargaProduk).toString())
        Picasso.get().load(produk[2].urlGambarProduk).into(this,R.id.photo3, IntArray(appWidgetId))
        setTextViewText(R.id.name3, produk[2].namaProduk)
        setTextViewText(R.id.description3, produk[2].description)
        setTextViewText(R.id.price3, (produk[2].hargaProduk).toString())
        Picasso.get().load(produk[3].urlGambarProduk).into(this,R.id.photo4, IntArray(appWidgetId))
        setTextViewText(R.id.name4, produk[3].namaProduk)
        setTextViewText(R.id.description4, produk[3].description)
        setTextViewText(R.id.price4, (produk[3].hargaProduk).toString())
        Picasso.get().load(produk[4].urlGambarProduk).into(this,R.id.photo5, IntArray(appWidgetId))
        setTextViewText(R.id.name5, produk[4].namaProduk)
        setTextViewText(R.id.description5, produk[4].description)
        setTextViewText(R.id.price5, (produk[4].hargaProduk).toString())
    }


    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}