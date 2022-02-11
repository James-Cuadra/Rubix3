package com.example.cameratest

import android.content.Context
import android.graphics.*
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.List as List1


class PictureAnalysis : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_analysis)
        var filePath = "/storage/emulated/0/Android/media/com.example.cameratest/Rubix³/photo.jpg"
        val cubeView: ImageView = findViewById(R.id.cubeView)
        val textView: TextView = findViewById(R.id.textView)
        Handler().postDelayed({
            var rawBitmap = BitmapFactory.decodeFile(filePath)
            val matrix = Matrix()
            matrix.postRotate(90.0F)
            var straightBitmap = Bitmap.createBitmap(
                rawBitmap, 0, 0, rawBitmap.width, rawBitmap.height, matrix, true
            )
            val length = 1800
            val cubeSide = Bitmap.createBitmap(straightBitmap, (straightBitmap.width-length)/2,(straightBitmap.height-length)/2,length, length)
            val bitmaps = getBitmaps(cubeSide)
            cubeView.setImageBitmap(cubeSide)
            val colours = getColours(bitmaps)
            textView.text = colours.toString()
                              }, 700)

    }
}
fun getBitmaps(bitmap: Bitmap): List1<Bitmap> {

    val bitmaps: MutableList<Bitmap> = ArrayList()

    for (i in 1..3) {
        for (j in 1..3){
            bitmaps.add(Bitmap.createBitmap(bitmap, (j-1)*600, (i-1)*600, 600, 600))
        }
    }

    return bitmaps

}

fun getColours(bitmaps: kotlin.collections.List<Bitmap>): kotlin.collections.List<String> {
    val colours: MutableList<String> = ArrayList()
    for (i in 1..9){
        val testPalette = Palette.from(bitmaps[i-1]).generate()
        val dominantCol = testPalette.getDominantColor(0)
        colours.add(red(dominantCol).toString() +","+ green(dominantCol).toString() +","+ blue(dominantCol).toString())
    }


    return colours

}