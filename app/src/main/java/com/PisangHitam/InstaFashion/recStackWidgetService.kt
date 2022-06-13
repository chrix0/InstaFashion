package com.PisangHitam.InstaFashion

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.PisangHitam.InstaFashion.Room.roomHelper
import com.PisangHitam.InstaFashion.SharedPref.dbSharedPref


class recStackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return StackRemoteViewsFactory(this.applicationContext, intent)
    }
}

class StackRemoteViewsFactory(
    private val context: Context,  intent: Intent ) : RemoteViewsService.RemoteViewsFactory {
    var produk: List<classProduk> = listOf()
    var db : roomHelper? = null
    // See the RemoteViewsFactory API reference for the full list of methods to
// implement.
    val appWidgetId: Int = intent.getIntExtra(
        AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID
    )
    override fun onCreate() {
        var sharedPref = dbSharedPref(context)
        db = sharedPref.db

        produk = db!!.daoOutfitList().getOutfitLimited("shirt", 5)
        Log.e("SIKE",produk[0].namaProduk)
    }

    override fun onDataSetChanged() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = produk.size

    override fun getViewAt(position: Int): RemoteViews {
        return RemoteViews(context.packageName, R.layout.recommendation_widget_item).apply {
            setTextViewText(R.id.name, produk[position].namaProduk)
        }
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return true
    }

}