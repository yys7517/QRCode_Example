package com.example.qrcode_example

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    private lateinit var getUrl: String
    private lateinit var webView: WebView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        Initialize()

        // 웹 사이트 URL 주소 가져오기
        getUrl = intent.getStringExtra("url").toString()

        // 웹 뷰 설정
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.loadUrl(getUrl)
    }

    private fun Initialize() {
        webView = findViewById(R.id.webView)

    }
}