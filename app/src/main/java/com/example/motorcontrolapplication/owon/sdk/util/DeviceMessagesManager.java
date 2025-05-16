package com.example.motorcontrolapplication.owon.sdk.util;

import com.example.motorcontrolapplication.DeviceListBean;
import com.example.motorcontrolapplication.DeviceTypeCode;
import com.example.motorcontrolapplication.EPListBean;

import java.util.ArrayList;
import java.util.List;


public class DeviceMessagesManager {

    private static DeviceMessagesManager instance;
    private List<SocketMessageListener> listeners;

    private DeviceMessagesManager() {
        listeners = new ArrayList<>();
    }

    public static synchronized DeviceMessagesManager getInstance() {
        if (instance == null) {
            instance = new DeviceMessagesManager();
        }
        return instance;
    }

    public void registerMessageListener(SocketMessageListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unregisterMessageListener(SocketMessageListener listener) {
        listeners.remove(listener);
    }

    public void GetEpList() {
        // TODO: Simulation Here
        simulateDeviceListResponse();
    }


    public void SmartCurtainMove(EPListBean device, int direction) {
        // TODO: Simulate Here
        simulateCurtainMoveResponse(device, direction);
    }

    public void SmartCurtainStop(EPListBean device) {
        // TODO: Simulate Here
        simulateCurtainStopResponse(device);
    }

    /*              Simulation methods for testing              */ 


    private void simulateDeviceListResponse() {
        // Create a list of simulated devices
        List<EPListBean> devices = new ArrayList<>();

        // Add a curtain motor device
        devices.add(new EPListBean("窗帘电机", "dev001", DeviceTypeCode.WARN_MOTOR, true));

        // Add some other device types for testing
        devices.add(new EPListBean("Light A", "dev002", Constants.LIGHT_601, true));
        devices.add(new EPListBean("Temperature Sensor", "dev003", DeviceTypeCode.TH_SENSOR, true));

        // Create a new DeviceListBean with the list of devices
        DeviceListBean deviceListBean = new DeviceListBean(devices);

        // Notify all listeners
        for (SocketMessageListener listener : listeners) {
            listener.getMessage(Constants.ZigBeeGetEPList, deviceListBean);
        }
    }

    /**
     * Simulate a curtain movement response
     */
    private void simulateCurtainMoveResponse(EPListBean device, int direction) {
        for (SocketMessageListener listener : listeners) {
            String message = "窗帘电机 " + (direction == 1 ? "正转(打开)" : "反转(关闭)") + " 命令已发送";
            listener.getMessage(2000, message); // Using a custom command ID
        }
    }

    /**
     * Simulate a curtain stop response
     */
    private void simulateCurtainStopResponse(EPListBean device) {
        for (SocketMessageListener listener : listeners) {
            String message = "窗帘电机停止命令已发送";
            listener.getMessage(2001, message); // Using a custom command ID
        }
    }
}