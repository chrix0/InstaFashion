package com.PisangHitam.InstaFashion.MusicPlayer

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import com.PisangHitam.InstaFashion.*
import com.PisangHitam.InstaFashion.singletonData.myMediaPlayer
import com.PisangHitam.InstaFashion.singletonData.playerPaused

class musicService : Service(),
    MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnErrorListener{
    override fun onBind(intent: Intent): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null){
            var actionIntent = intent.action
            when(actionIntent){
                //myMediaPlayer berasal dari object singletonData
                ACTION_CREATE -> {
                    if (myMediaPlayer == null){
                        myMediaPlayer = MediaPlayer()
                        //Code di fungsi yang udah diimplementasi class
                        myMediaPlayer?.setOnPreparedListener(this)
                        myMediaPlayer?.setOnCompletionListener(this)
                        myMediaPlayer?.setOnErrorListener(this)
                    }
                }
                ACTION_PLAY ->{
                    if(!myMediaPlayer!!.isPlaying){
                        if(playerPaused){
                            myMediaPlayer!!.start()
                        }
                        else{
                            //masukkan lagu ke dalam file descriptor
                            val assetFileDescriptor = this.resources.openRawResourceFd(R.raw.music_1)

                            //Load lagunya..
                            myMediaPlayer?.run{
                                this.reset() //Pastikan tidak ada lagu lain yang sedang dimainkan
                                //dengan file descriptor tadi..
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    this.setDataSource(
                                        assetFileDescriptor.fileDescriptor,
                                        assetFileDescriptor.startOffset,
                                        assetFileDescriptor.declaredLength
                                    )
                                }
                                else{
                                    Toast.makeText(applicationContext, "Sorry. Your Android version does not support this feature.", Toast.LENGTH_LONG)
                                }
                                prepareAsync() //Proses bisa lama, jadi dilakukan secara async sehingga tidak akan dijalankan pada UI thread.
                                // Secara otomatis akan menjalankan method onPrepared ketika proses prepare selesai
                            }
                        }
                        playerPaused = false
                    }
                }

                ACTION_STOP -> {
                    myMediaPlayer?.stop()
                    playerPaused = false
                }

                ACTION_PAUSE -> {
                    myMediaPlayer?.pause()
                    playerPaused = true
                }
            }
        }
        return flags
    }
    override fun onPrepared(p0: MediaPlayer?) {
        //Ketika file audio sudah diload
        myMediaPlayer?.start() //Mulaikan musiknya
    }
    override fun onCompletion(p0: MediaPlayer?) {
        //Ketika file audio sudah selesai dibaca
    }
    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        //Ketika ada masalah dalam membaca file audio
        Toast.makeText(this, "Failed reading audio file..", Toast.LENGTH_SHORT).show()
        return false
    }
    override fun onDestroy(){
        super.onDestroy()
        myMediaPlayer?.release() //Tutup media player ketika tidak digunakan
    }
}
