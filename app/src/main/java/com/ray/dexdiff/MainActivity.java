package com.ray.dexdiff;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //取消严格模式 否则可能会安装失败
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }

    }

    public void patch(View view) {
        //目标APK
        File newFile = new File(getExternalFilesDir("apk"), "app.apk");
        //增量包
        File patchFile = new File(getExternalFilesDir("apk"), "patch.apk");
        if (patchFile.exists()){
            int result = BsPatchUtils.patch(getApplicationInfo().sourceDir, newFile.getAbsolutePath(),
                    patchFile.getAbsolutePath());
            if (result == 0) {
                install(newFile);
            }
        }
    }

    private void install(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri apkUri = Uri.parse("file://" + file.toString());
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }
}
