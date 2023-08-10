package com.redapps.phonepolice.helpers;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.util.Log;
import android.widget.Toast;

import com.redapps.phonepolice.serviceHandler.CameraService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CamManager implements Camera.PictureCallback, Camera.ErrorCallback, Camera.PreviewCallback, Camera.AutoFocusCallback {
    private final Context mContext;
    private Camera mCamera;
    private SurfaceTexture mSurface;

    public CamManager(Context context) {
        this.mContext = context;
    }


    public void takePhoto() {
        if (isFrontCameraAvailable()) {
            initCamera();
        }
    }

    private boolean isFrontCameraAvailable() {
        Context context = this.mContext;
        if (context == null || !context.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            return false;
        }
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 1) {
                return true;
            }
        }
        return false;
    }

    private void initCamera() {
        try {
            this.mCamera = Camera.open(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (this.mCamera != null) {
                SurfaceTexture surfaceTexture = new SurfaceTexture(123);
                this.mSurface = surfaceTexture;
                this.mCamera.setPreviewTexture(surfaceTexture);
                Camera.Parameters parameters = this.mCamera.getParameters();
                parameters.setRotation(270);
                if (autoFocusSupported(this.mCamera)) {
                    parameters.setFocusMode("auto");
                } else {
                    Log.w("asdaxxx", "Autofocus is not supported");
                }
                this.mCamera.setParameters(parameters);
                this.mCamera.setPreviewCallback(this);
                this.mCamera.setErrorCallback(this);
                this.mCamera.startPreview();
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            releaseCamera();
        }
    }

    private boolean autoFocusSupported(Camera camera) {
        if (camera != null) {
            return camera.getParameters().getSupportedFocusModes().contains("auto");
        }
        return false;
    }

    private void releaseCamera() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.stopPreview();
            this.mCamera.release();
            this.mSurface.release();
            this.mCamera = null;
            this.mSurface = null;
        }
        if (Constants.isMyServiceRunning(this.mContext, CameraService.class)) {
            this.mContext.stopService(new Intent(this.mContext, CameraService.class));
        }
    }

    @Override
    public void onError(int i, Camera camera) {
        if (i == 1) {
            Log.e("ContentValues", "Camera error: Unknown");
        } else if (i == 2) {
            Log.e("ContentValues", "Camera error: Camera was disconnected due to use by higher priority user");
        } else if (i != 100) {
            Log.e("ContentValues", "Camera error: no such error id (" + i + ")");
        } else {
            Log.e("ContentValues", "Camera error: Media server died");
        }
    }

    @Override
    public void onPreviewFrame(byte[] bArr, Camera camera) {
        try {
            if (autoFocusSupported(camera)) {
                camera.setPreviewCallback(null);
                camera.takePicture(null, null, this);
            } else {
                camera.setPreviewCallback(null);
                camera.takePicture(null, null, this);
            }
        } catch (Exception e) {
            Log.e("ContentValues", "Camera error while taking picture");
            e.printStackTrace();
            releaseCamera();
        }
    }

    @Override
    public void onAutoFocus(boolean z, Camera camera) {
        if (camera != null) {
            try {
                camera.takePicture(null, null, this);
                this.mCamera.autoFocus(null);
            } catch (Exception e) {
                e.printStackTrace();
                releaseCamera();
            }
        }
    }

    @Override
    public void onPictureTaken(byte[] bArr, Camera camera) {
        savePicture(bArr);
        releaseCamera();
    }

    private void savePicture(byte[] bArr) {
        try {
            File file = new File(this.mContext.getExternalFilesDir(null) + "/Anti Theft");
            if (bArr == null) {
                Toast.makeText(this.mContext, "cant save image", Toast.LENGTH_LONG).show();
                Log.e("asdaxxx", "Can't save image - no data");
                return;
            }
            if (!file.exists() && file.mkdirs()) {
                Log.d("msgdirector", "Directory is Created");
            }
            if (!file.exists()) {
                Toast.makeText(this.mContext, "Can't create directory to save image", Toast.LENGTH_LONG).show();
                Log.e("asdaxxx", "Can't create directory to save image.");
                return;
            }
            String str = "intruderselfie_" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".jpg";
            String str2 = file.getPath() + File.separator + str;
            Log.d("asdaxxx", str2);
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str2));
            fileOutputStream.write(bArr);
            fileOutputStream.close();
            Log.d("asdaxxx", "New image was saved" + str);
        } catch (Exception e) {
            Log.e("asdaxxx", e.toString());
            e.printStackTrace();
        }
    }
}
