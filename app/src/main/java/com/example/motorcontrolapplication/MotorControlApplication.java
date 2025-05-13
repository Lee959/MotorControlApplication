package com.example.motorcontrolapplication;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.example.motorcontrolapplication.owon.sdk.util.DeviceMessagesManager;
import com.example.motorcontrolapplication.owon.sdk.util.SocketMessageListener;


public class MotorControlApplication extends Application {

    private static final String TAG = "MotorControlApplication";

    private DeviceMessagesManager deviceMessagesManager;

    @Override
    public void onCreate() {
        super.onCreate();

        initSDK();
    }

    private void initSDK() {
        // 获取设备管理器实例
        deviceMessagesManager = DeviceMessagesManager.getInstance();

        // 初始化SDK配置
//        boolean initResult = deviceMessagesManager.registerMessageListener(this);
//        Log.d(TAG, "SDK初始化结果: " + initResult);

        if (true) {
            deviceMessagesManager.registerMessageListener(new SocketMessageListener() {
                @Override
                public void getMessage(int commandID, Object bean) {
                    // 处理全局消息
                    Log.d(TAG, "全局消息回调: " + commandID);
                }
            });
        } else {
            Log.e(TAG, "SDK初始化失败");
        }
    }

    /**
     * 获取设备管理器实例
     */
    public DeviceMessagesManager getDeviceMessagesManager() {
        return deviceMessagesManager;
    }

    @Override
    public void onTerminate() {
        // 断开连接并释放资源
        if (deviceMessagesManager != null) {
//            deviceMessagesManager.unregisterMessageListener(this);
        }

        super.onTerminate();
    }
}