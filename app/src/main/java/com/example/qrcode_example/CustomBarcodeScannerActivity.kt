package com.example.qrcode_example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class CustomBarcodeScannerActivity : AppCompatActivity() {

    private lateinit var captureManager: CaptureManager
    private lateinit var decoratedBarcodeView: DecoratedBarcodeView
    private lateinit var btnFlash : Button

    private var isFlash : Boolean = false   // 플래시가 켜져있는지 확인하기 위한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_barcode_scanner)

        decoratedBarcodeView = findViewById(R.id.decoratedBarcodeView)  // 커스텀 바코드 뷰
        btnFlash = findViewById(R.id.btnFlash)          // 플래시 버튼

        captureManager = CaptureManager( this, decoratedBarcodeView)
        captureManager.initializeFromIntent( intent, savedInstanceState )
        captureManager.setShowMissingCameraPermissionDialog(true,"카메라 권한 요청")   // 권한요청 다이얼로그 보이게 할 지 말 지
        captureManager.decode()     // decoding 시작

        // 플래시 버튼 클릭 ?
        btnFlash.setOnClickListener {
            if( !isFlash ) {
                // 플래시가 현재 꺼져있을 때 버튼이 눌렸다면 플래시를 켠다.
                btnFlash.text = "Flash OFF"
                isFlash = true
                decoratedBarcodeView.setTorchOn()
            }
            else {
                // 플래시가 현재 켜져있을 때 버튼이 눌렸다면 플래시를 끈다.
                btnFlash.text = "Flash ON"
                isFlash = false
                decoratedBarcodeView.setTorchOff()
            }
        }
    }

    // LifeCycle 에 따라 CaptureManager 또한 처리해주어야 한다.

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    // onSaveInstanceState ? 또한 처리해주어야 한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }

    // 카메라 권한을 요청할 수 있기 때문에
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}


