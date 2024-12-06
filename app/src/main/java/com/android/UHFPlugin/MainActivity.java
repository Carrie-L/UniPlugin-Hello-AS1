package com.android.UHFPlugin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.uhfplugins.UHFModule;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity-console1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        UHFModule uhfModule = new UHFModule();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", 0X36);
        jsonObject.put("data", 0XBC);
        jsonObject.put("data", 0X90);
        byte[] myArray = {3, 5, 2, 4, 1};
        UHFModule.Bytes2HexString(myArray, new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke Bytes2HexString: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {
                Log.i(TAG, "invokeAndKeepAlive: " + o.toString());
            }
        });

        uhfModule.setPower(30,80, new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke setPower: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {

            }
        });

         uhfModule.getMyTime(new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke getMyTime: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {

            }
        });

        uhfModule.getHardware(new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke getHardware: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {

            }
        });

        uhfModule.asyncStartReading(new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke asyncStartReading: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {

            }
        });

        UHFModule.uniteBytes(80, 100, new UniJSCallback() {
            @Override
            public void invoke(Object o) {
                Log.i(TAG, "invoke uniteBytes: " + o.toString());
            }

            @Override
            public void invokeAndKeepAlive(Object o) {
            }
        });

        // 启动小程序
        // 启动小程序并传入参数 "Hello uni microprogram"
//        try {
//            UniMPOpenConfiguration uniMPOpenConfiguration = new UniMPOpenConfiguration();
//            uniMPOpenConfiguration.extraData.put("MSG","Hello DCUniMPConfiguration");
////            mallMP = new SoftReference<>(DCUniMPSDK.getInstance().openUniMP(MainActivity.this, "__UNI__A922B72_minimall", uniMPOpenConfiguration));
//            IUniMP unimp = DCUniMPSDK.getInstance().openUniMP(this, "__UNI__9EB0A04", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        DCUniMPSDK.getInstance().openUniMP(this,"__UNI__F8AA247", "pages/index/index");

    }
}