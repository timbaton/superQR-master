package info.superego.qrreader.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.example.timur.myqrreader.R;

public class MainActivity extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {

    private static final int CAMERA_PERMISSION_REQUEST = 0;

    private QRCodeReaderView qrCodeReaderView;
    private View rootView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        rootView = findViewById(R.id.root);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initQRCodeReaderView();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.startCamera();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (qrCodeReaderView != null) {
            qrCodeReaderView.stopCamera();
        }
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        qrCodeReaderView.stopCamera();

        Intent intent = new Intent(this, CodeInformationActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        startActivity(intent);
    }

    private void initQRCodeReaderView() {
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setVisibility(View.VISIBLE);
        qrCodeReaderView.setAutofocusInterval(3000L);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.startCamera();
    }

    private void requestCameraPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle(R.string.camera_access)
                    .setMessage(R.string.request_camera_perm)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    CAMERA_PERMISSION_REQUEST);
                        }
                    })
                    .show();

        } else {
            Snackbar.make(rootView, R.string.new_attaemp_camera_perm, Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != CAMERA_PERMISSION_REQUEST) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(rootView, R.string.access_granted, Snackbar.LENGTH_SHORT).show();
            initQRCodeReaderView();
        } else {
            Snackbar.make(rootView, R.string.access_denied_stop_working, Snackbar.LENGTH_SHORT).show();
        }
    }
}

