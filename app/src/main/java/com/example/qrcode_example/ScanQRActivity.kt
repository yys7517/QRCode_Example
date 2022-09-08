package com.example.qrcode_example

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import android.content.Intent
import android.graphics.Camera
import androidx.camera.core.CameraInfo
import com.google.zxing.client.android.Intents


class ScanQRActivity : AppCompatActivity() {

    private lateinit var txtResult : TextView

    // 스캐너 설정
    private val barcodeLauncher = registerForActivityResult(
        ScanContract()
    ) { result: ScanIntentResult ->
        // result : 스캔된 결과

        val resultContents = result.contents

        // 내용이 없다면
        if (resultContents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        }
        else { // 내용이 있다면

            // 1. Toast 메시지 출력.
            Toast.makeText(
                this,
                "Scanned: $resultContents" ,
                Toast.LENGTH_LONG
            ).show()

            // 2. 결과 값 TextView에 출력.
            txtResult.text = resultContents.toString()

            // 3. 웹 사이트 주소이면 ? 웹 뷰로 연결하자
            if ( resultContents.toString().startsWith("http") ) {
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("url", resultContents.toString())
                startActivity(intent)
            }
        }
    }

    // SCAN - onClick
    private fun onScanButtonClicked() {
        // Launch ( SCAN 실행 )
        // barcodeLauncher.launch(ScanOptions())    // Orientation Lock 을 해제하지 않은 상태로 실행. (default : 세로)

        // Changing the Orientation
        val options = ScanOptions()
        options.setOrientationLocked(false)     // ScanOption 의 Orientation Lock 을 해제하기.
        barcodeLauncher.launch(options)         // Orientation Lock 을 해제한 상태로 실행. ( 세로, 가로 스캔 화면 모두 가능. )
    }

    // 커스텀 스캐너 실행하기
    // Custom SCAN - onClick
    private fun onCustomScanButtonClicked() {

        // Advanced Option Scans Test...
        // scanBarcodeInverted()
        // scanMixedBarcodes()
        // scanPDF417()
        // scanBarcodeFrontCamera()
        // scanWithTimeout()

        // Custom Scan Layout -> Activity

        // Intent ? -> 맞는 방법일까 ?
        // val intent = Intent( this, CustomBarcodeScannerActivity::class.java)
        // startActivity(intent)

        // ScanOptions + captureActivity(CustomScannerActivity)
        val options = ScanOptions()
        options.setOrientationLocked(false)
        // options.setCameraId(1)          // 0 : 후면(default), 1 : 전면,
        options.setBeepEnabled(true)
        // options.setTorchEnabled(true)      // true : 실행되자마자 플래시가 켜진다.
        options.setPrompt("커스텀 QR 스캐너 창")
        options.setDesiredBarcodeFormats( ScanOptions.QR_CODE )
        options.captureActivity = CustomBarcodeScannerActivity::class.java

        barcodeLauncher.launch(options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr)

        val btnScan : Button = findViewById(R.id.btnScan)
        txtResult = findViewById(R.id.txtResult)

        // 가로 SCAN 버튼 클릭
        btnScan.setOnClickListener {
            onScanButtonClicked()
        }

        val btnCustomScan : Button = findViewById(R.id.btnCustomScan)
        // Custom Scan 버튼 클릭
        btnCustomScan.setOnClickListener {
            onCustomScanButtonClicked()
        }

    }

    // The scan should be inverted. White becomes black, black becomes white.
    fun scanBarcodeInverted() {
        val options = ScanOptions()
        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.INVERTED_SCAN)
        barcodeLauncher.launch(options)
    }

    // Scan alternating inverted and normal barcodes.
    fun scanMixedBarcodes() {
        val options = ScanOptions()
        options.addExtra(Intents.Scan.SCAN_TYPE, Intents.Scan.MIXED_SCAN)
        barcodeLauncher.launch(options)
    }

    // PDF417 타입의 바코드를 SCAN
    fun scanPDF417() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.PDF_417)
        options.setCameraId(0)
        options.setPrompt("Scan something")     // setPrompt - 하단 문구
        options.setOrientationLocked(false)     // setOrientationLocked - 가로, 세로 방향 전환
        options.setBeepEnabled(false)           // setBeepEnabled - 바코드 포착 시, Beep 소리 발생 여부 지정.
        barcodeLauncher.launch(options)
    }


    // 전면 카메라를 사용한 SCAN
    private fun scanBarcodeFrontCamera() {
        val options = ScanOptions()
        // options.setCameraId(android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT)  // Deprecated..
        options.setCameraId(0)          // 0 : 후면, 1 : 전면
        barcodeLauncher.launch(options)
    }


    // Timeout 을 지정하여 8000ms 후 종료되는 SCAN
    private fun scanWithTimeout() {
        val options = ScanOptions()
        options.setTimeout(8000)
        barcodeLauncher.launch(options)
    }
}