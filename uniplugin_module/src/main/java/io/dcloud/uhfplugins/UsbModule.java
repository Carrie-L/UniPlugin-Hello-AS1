package io.dcloud.uhfplugins;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniDestroyableModule;
import io.dcloud.feature.uniapp.common.UniModule;

/**
 * 请求USB权限桥接
 */
public class UsbModule extends UniDestroyableModule {
    private static final String TAG = "UsbModule";
    private static final String ACTION_USB_PERMISSION = "com.uhf.USB_PERMISSION";
    private static final int PERMISSION_REQUEST_CODE = 1020;

    private UsbManager usbManager;
    private UniJSCallback mCallback;
    private UsbDevice mDevice;

    // 广播接收器处理USB权限
    private final BroadcastReceiver usbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null && mCallback != null) {
                            // 权限获取成功
                            JSONObject result = new JSONObject();
                            result.put("code", PERMISSION_REQUEST_CODE);
                            result.put("msg", "USB权限已获取");
                            result.put("hasPermission", true);
                            result.put("deviceName", device.getDeviceName());
                            result.put("vendorId", device.getVendorId());
                            result.put("productId", device.getProductId());
                            mCallback.invoke(result);
                        }
                    } else {
                        // 权限被拒绝
                        if (mCallback != null) {
                            callbackFail(mCallback, "USB权限被拒绝");
                        }
                    }
                    // 清理回调
                    mCallback = null;
                }
            }
        }
    };

    // 检查USB设备是否已有权限
    @UniJSMethod(uiThread = true)
    public void hasUsbPermission(JSONObject options, UniJSCallback callback) {
        try {
            if (usbManager == null) {
                usbManager = (UsbManager) mUniSDKInstance.getContext()
                        .getSystemService(Context.USB_SERVICE);
            }

            // 获取传入的vendorId和productId
            int vendorId = options.getIntValue("vendorId");
            int productId = options.getIntValue("productId");

            // 查找指定的USB设备
            boolean found = false;
            boolean hasPermission = false;

            for (UsbDevice device : usbManager.getDeviceList().values()) {
                if (device.getVendorId() == vendorId && device.getProductId() == productId) {
                    found = true;
                    hasPermission = usbManager.hasPermission(device);

                    JSONObject result = new JSONObject();
                    result.put("code", PERMISSION_REQUEST_CODE);
                    result.put("hasPermission", hasPermission);
                    result.put("deviceName", device.getDeviceName());
                    result.put("vendorId", device.getVendorId());
                    result.put("productId", device.getProductId());
                    callback.invoke(result);
                    break;
                }
            }

            if (!found) {
                callbackFail(callback, "检查USB权限失败, 没有找到设备");
            }
        } catch (Exception e) {
            Log.e(TAG, "hasUsbPermission error", e);
            callbackFail(callback, "检查USB权限失败：" + e.getMessage());
        }
    }

    // 请求USB设备权限
    @UniJSMethod(uiThread = true)
    public void requestUsbPermission(JSONObject options, UniJSCallback callback) {
        try {
            if (usbManager == null) {
                usbManager = (UsbManager) mUniSDKInstance.getContext()
                        .getSystemService(Context.USB_SERVICE);
            }

            // 获取传入的vendorId和productId
            int vendorId = options.getIntValue("vendorId");
            int productId = options.getIntValue("productId");

            // 查找指定的USB设备
            UsbDevice targetDevice = null;
            for (UsbDevice device : usbManager.getDeviceList().values()) {
                if (device.getVendorId() == vendorId && device.getProductId() == productId) {
                    targetDevice = device;
                    break;
                }
            }

            if (targetDevice == null) {
                callbackFail(callback, "未找到指定的USB设备");
                return;
            }

            // 如果已经有权限，直接返回成功
            if (usbManager.hasPermission(targetDevice)) {
                JSONObject result = new JSONObject();
                result.put("code", PERMISSION_REQUEST_CODE);
                result.put("msg", "已有USB权限");
                result.put("hasPermission", true);
                result.put("deviceName", targetDevice.getDeviceName());
                result.put("vendorId", targetDevice.getVendorId());
                result.put("productId", targetDevice.getProductId());
                callback.invoke(result);
                return;
            }

            // 保存回调和设备引用
            mCallback = callback;
            mDevice = targetDevice;

            // 注册广播接收器
            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
            mUniSDKInstance.getContext().registerReceiver(usbReceiver, filter);

            // 请求权限
            PendingIntent permissionIntent = PendingIntent.getBroadcast(
                    mUniSDKInstance.getContext(),
                    PERMISSION_REQUEST_CODE,
                    new Intent(ACTION_USB_PERMISSION),
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            usbManager.requestPermission(targetDevice, permissionIntent);

        } catch (Exception e) {
            Log.e(TAG, "requestUsbPermission error", e);
            callbackFail(callback, "请求USB权限失败：" + e.getMessage());
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

    @Override
    public void onActivityDestroy() {
        super.onActivityDestroy();
        unregisterReceiver();
    }

    /**
     * 注销广播接收器
     */
    private void unregisterReceiver() {
        try {
            mUniSDKInstance.getContext().unregisterReceiver(usbReceiver);
        } catch (Exception e) {
            Log.e(TAG, "unregisterReceiver error", e);
        }
    }

    @Override
    public void destroy() {
        unregisterReceiver();
    }
}