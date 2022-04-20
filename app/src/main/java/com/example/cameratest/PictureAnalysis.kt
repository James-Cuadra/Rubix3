package com.example.cameratest

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.Color.*
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
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
import androidx.core.graphics.ColorUtils
import com.example.cameratest.databinding.ActivityMainBinding
import com.example.cameratest.Constants.TAG



class PictureAnalysis : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_analysis)
        var count = 0
        val colourList = Array(6) {Array(9) {String} }
        var filePath = "/storage/emulated/0/Android/media/com.example.cameratest/Rubix³/photo.jpg"
        val cubeView: ImageView = findViewById(R.id.cubeView)
        val textView: TextView = findViewById(R.id.textView)
        val editText: EditText = findViewById(R.id.editText)
        if (count == 5) {
            val intent = Intent(this, SolveCube::class.java);
            startActivity(intent)
        }
        Handler().postDelayed({
            var rawBitmap = BitmapFactory.decodeFile(filePath)
            val bitmaps = getBitmaps(rawBitmap)
            cubeView.setImageBitmap(rawBitmap)
            val colours = getColours(bitmaps)
            var cols = colours.toString()
            textView.text = colours.toString()
            editText.setText(cols)
            val button = findViewById<Button>(R.id.button)
            button.setOnClickListener{
                colourList[count] = ArrayList(textView.text)
                count += 1
                Log.d(TAG, '[R, ]')
                val intent = Intent(this, MainActivity::class.java);
                startActivity(intent)
            }
                              }, 700)

    }
}

fun getBitmaps(bitmap: Bitmap): List1<Bitmap> {
    val bitmaps: MutableList<Bitmap> = ArrayList()
    val matrix = Matrix()
    matrix.postRotate(90.0F)
    var straightBitmap = Bitmap.createBitmap(
        bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
    )
    val length = 1800
    val cubeSide = Bitmap.createBitmap(straightBitmap, (straightBitmap.width-length)/2,(straightBitmap.height-length)/2,length, length)
    for (i in 1..3) {
        for (j in 1..3){
            bitmaps.add(Bitmap.createBitmap(cubeSide, (j-1)*600, (i-1)*600, 600, 600))
        }
    }

    return bitmaps

}

fun getColours(bitmaps: kotlin.collections.List<Bitmap>): kotlin.collections.List<String> {
    val colours: MutableList<String> = ArrayList()
    var guessedCol : String
    val hsls = FloatArray(3)
    for (i in 1..9){
        val testPalette = Palette.from(bitmaps[i-1]).generate()
        val dominantCol = testPalette.getDominantColor(0)
        ColorUtils.RGBToHSL(red(dominantCol), green(dominantCol), blue(dominantCol), hsls)
        guessedCol = when ((hsls[2]*100).toInt()){
            in 70..100 -> "W"
            else -> when (hsls[0].toInt()) {
                in 0..10 -> "R"
                in 15..35 -> "O"
                in 35..60 -> "Y"
                in 70..150 -> "G"
                in 200..260 -> "B"
                in 300..360 -> "R"
                else -> "Error"
            }
        }
        colours.add(guessedCol)
        //colours.add((hsls[0]).toString())
    }


    return colours

}