package io.dcloud.uhfplugins;

import android.app.Application;

import io.dcloud.feature.uniapp.UniAppHookProxy;
import io.dcloud.feature.uniapp.UniSDKEngine;

public class UHFProxy implements UniAppHookProxy {
    private static PermissionModule permissionModule;

    @Override
    public void onSubProcessCreate(Application application) {
        UniSDKEngine.registerModule("UHFModule", UHFModule.class);
        UniSDKEngine.registerModule("PermissionModule", PermissionModule.class);
    }

    @Override
    public void onCreate(Application application) {

    }



    public static void setPermissionModule(PermissionModule module) {
        permissionModule = module;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissionModule != null) {
            permissionModule.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
