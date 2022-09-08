package com.example.qrcode_example

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreate : Button
    private lateinit var btnScan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreate = findViewById(R.id.btnCreate)
        btnScan = findViewById(R.id.btnScan)

        btnCreate.setOnClickListener {
            val intent = Intent( this, CreateQRActivity::class.java )
            startActivity(intent)
        }

        btnScan.setOnClickListener {
            val intent = Intent( this, ScanQRActivity::class.java )
            startActivity(intent)
        }
    }
}