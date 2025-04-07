package com.example.myapp1

import android.app.Activity
import android.graphics.Bitmap
import android.media.Image
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp1.databinding.ActivityCoffeeSnapsBinding
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CoffeeSnapsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoffeeSnapsBinding
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraSelector: CameraSelector
    private var imageCapture: ImageCapture? = null
    private lateinit var imgCaptureExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_coffee_snaps)

        binding = ActivityCoffeeSnapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imgCaptureExecutor = Executors.newSingleThreadExecutor()


        val cameraProviderResult = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGranted ->
            if (permissionGranted) {
                //initialise the camera
                startCamera()
            } else {
                Toast.makeText(
                    this,
                    "Cannot take a photo without camera permissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        cameraProviderResult.launch(android.Manifest.permission.CAMERA)

        binding.photoFab.setOnClickListener() {
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(
                File(externalMediaDirs[0], "Coffee_${System.currentTimeMillis()}")
            ).build()
            imageCapture?.takePicture(
                outputFileOptions, imgCaptureExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exception: ImageCaptureException) {
                        //Toast.makeText(this, "Image could not be saved", Toast.LENGTH_SHORT).show()
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        Log.d(
                            "CoffeeSnapsActivity",
                            "Photo saved to ${outputFileResults.savedUri}"
                        )

                        this@CoffeeSnapsActivity.runOnUiThread {
                            binding.imgSavedPhoto.setImageURI(outputFileResults.savedUri)
                        }
                    }
                })
        }

    }


    private fun startCamera() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.imgCameraImage.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
            } catch (e: Exception) {
                Log.d("CoffeeSnapsActivity", "Use case binding failed")
            }
        }, ContextCompat.getMainExecutor(this))
    }
}

    /*override fun onImageSaved(outputFileResultContracts: ActivityResultContracts){
        Log.d("CoffeeSnapsActivity",
            "Photo saved to ${outputFileResults.savedUri}")
        this@CoffeeSnapsActivity.runOnUiThread(java.lang.Runnable {
            binding.imgSavedPhoto.setImageURI(outputFileResults.savedUri)
        })
    }
}*/