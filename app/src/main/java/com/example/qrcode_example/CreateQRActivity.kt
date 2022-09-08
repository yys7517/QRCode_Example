package com.example.qrcode_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.zxing.BarcodeFormat

import android.view.View
import android.widget.ImageView

import com.journeyapps.barcodescanner.BarcodeEncoder
import java.lang.Exception

class CreateQRActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qr)

        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap("https://www.naver.com/", BarcodeFormat.QR_CODE, 400, 400)
            val imageViewQrCode: ImageView = findViewById<View>(R.id.imageViewQrCode) as ImageView
            imageViewQrCode.setImageBitmap(bitmap)
        } catch (e: Exception) {
        }

    }
}