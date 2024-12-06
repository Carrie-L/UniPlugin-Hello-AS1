package io.dcloud.uhfplugins;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.os.Environment;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class PermissionModule extends UniModule {
    private static final String TAG = "Permission-console";
    private static final int PERMISSION_REQUEST_CODE = 1010;

    private UniJSCallback mCallback;

    public PermissionModule() {
        UHFProxy.setPermissionModule(this);
    }

    // 检查权限
    @UniJSMethod(uiThread = true)
    public void checkPermission(JSONObject options, UniJSCallback callback) {
        try {
            String permission = options.getString("permission");
            boolean hasPermission = checkSelfPermission(permission);
            JSONObject result = new JSONObject();
            result.put("code", PERMISSION_REQUEST_CODE);
            result.put("hasPermission", hasPermission);
            result.put("msg", hasPermission);
            callback.invoke(result);

        } catch (Exception e) {
            Log.e(TAG, "checkPermission error", e);
            callbackFail(callback, "检查权限失败");

            JSONObject result = new JSONObject();
            result.put("code", PERMISSION_REQUEST_CODE);
            result.put("hasPermission", false);
            result.put("msg", e.getMessage());
            callback.invoke(result);
        }
    }

    // 请求权限
    @UniJSMethod(uiThread = true)
    public void requestPermission(JSONObject options, UniJSCallback callback) {
        try {
            String permission = options.getString("permission");
            this.mCallback = callback;

            // 首先检查是否已有权限
            if (checkSelfPermission(permission)) {
                JSONObject result = new JSONObject();
                result.put("code", PERMISSION_REQUEST_CODE);
                result.put("hasPermission", true);  //
                callback.invokeAndKeepAlive(result);
                return;
            }else{
                JSONObject result = new JSONObject();
                result.put("code", -1);
                result.put("hasPermission", false);
                result.put("msg", "没有权限，请求权限: "+permission);
                callback.invokeAndKeepAlive(result);
            }

            // 没有权限才请求
            if (isStoragePermission(permission)) {
                requestStoragePermission();
            } else {
                String[] permissions = new String[]{permission};
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }

        } catch (Exception e) {
            Log.e(TAG, "requestPermission error", e);
            callbackFail(callback, "请求权限失败");
        }
    }

    // 检查是否有指定权限
    private boolean checkSelfPermission(String permission) {
        if (isStoragePermission(permission) && (Build.VERSION.SDK_INT >= 30)) {
                return Environment.isExternalStorageEmulated();

        }
        return ContextCompat.checkSelfPermission(mUniSDKInstance.getContext(), permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    // 请求权限
    private void requestPermissions(String[] permissions, int requestCode) {
        if (mUniSDKInstance.getContext() instanceof Activity) {
            Activity activity = (Activity) mUniSDKInstance.getContext();
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
            Log.i(TAG, "requestPermissions: 请求权限："+Arrays.toString(permissions));
        }
    }

    @UniJSMethod(uiThread = true)
    public void requestStoragePermission() {
        Activity activity = (Activity) mUniSDKInstance.getContext();
        if (Build.VERSION.SDK_INT >= 30) {
            try {
                // 对于 Android 11 及以上，应该使用 MANAGE_EXTERNAL_STORAGE 权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            } catch (Exception e) {
                // 如果上面的意图失败，尝试打开所有应用的文件访问设置
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
                activity.startActivity(intent);
            }
        } else {
            // Android 10 及以下使用传统权限请求
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // 用户之前拒绝过，应该显示解释
                showPermissionExplanation();
            } else {
                String[] permissions = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };
                requestPermissions(permissions, PERMISSION_REQUEST_CODE);
            }
        }

    }

    // 添加权限解释对话框
    private void showPermissionExplanation() {
        Activity activity = (Activity) mUniSDKInstance.getContext();
        // 显示对话框解释为什么需要这个权限
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle("需要存储权限")
                .setMessage("应用需要存储权限来读写文件。")
                .setPositiveButton("授权", (dialog, which) -> {
                    String[] permissions = new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    };
                    requestPermissions(permissions, PERMISSION_REQUEST_CODE);
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 判断是否是存储权限
    private boolean isStoragePermission(String permission) {
        return Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission)
                || Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission);
    }

    // 处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "onRequestPermissionsResult: "+ Arrays.toString(grantResults) );
        if (requestCode == PERMISSION_REQUEST_CODE && mCallback != null) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            JSONObject result = new JSONObject();
            try {
                result.put("code", allGranted ? PERMISSION_REQUEST_CODE : -1);
                result.put("hasPermission", allGranted);
                result.put("msg", allGranted+", 权限请求被拒绝");
                mCallback.invokeAndKeepAlive (result);
            } catch (JSONException e) {
                Log.e(TAG, "onRequestPermissionsResult error", e);
                callbackFail(mCallback, Arrays.toString(grantResults));
            }

            mCallback = null;
        }else{
            Log.i(TAG, "onRequestPermissionsResult: 无");
            callbackFail(mCallback, "权限请求结果处理失败");
        }
    }

    private void callbackFail(UniJSCallback callback, String message) {
        if (callback != null) {
            JSONObject result = new JSONObject();
            try {
                result.put("code", -1);
                result.put("hasPermission", false);
                result.put("msg", message);
                callback.invoke(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }





}

