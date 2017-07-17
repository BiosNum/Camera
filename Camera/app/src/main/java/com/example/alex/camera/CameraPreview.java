package com.example.alex.camera;


import java.io.IOException;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "CameraPreview";
    private SurfaceHolder sh;
    private Camera camera;

    public CameraPreview(Context context, Camera cm) {
        super(context);
        camera = cm;
        sh = getHolder();
        sh.addCallback(this);
        sh.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Ошибка создания превью: " + e.getMessage());
            e.getStackTrace();
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if (sh.getSurface() == null) {
            // нет surface preview!
            return;
        }

        // остановка превью перед изменением.
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // если не существовало, ты выбросится ошибка,
            //здесь можно как-нибудь обработать
        }

        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            camera.setDisplayOrientation(90);
        }

        try {
            camera.setPreviewDisplay(sh);
            camera.startPreview();
        } catch (Exception e) {
            Log.e(TAG, "Ошибка при перезапуске превью: " + e.getMessage());
            e.getStackTrace();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
