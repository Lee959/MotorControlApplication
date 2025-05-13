package com.example.motorcontrolapplication.owon.sdk.util;

import android.util.Log;

import com.example.motorcontrolapplication.DeviceListBean;
import com.example.motorcontrolapplication.DeviceTypeCode;
import com.example.motorcontrolapplication.SmartCurtainBean;
import com.example.motorcontrolapplication.bean;

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
        // GatewayApplication
        simulateSensorListResponse();
    }

    public void getDeviceState(bean device, int cache) {
        //  GatewayApplication
        simulateDeviceStateResponse(device, cache);
    }

    public void SmartCurtainMove(SmartCurtainBean curtainMotor, int i) {
        // TODO: MotorControlApplication
    }

    public void SmartCurtainStop(SmartCurtainBean curtainMotor) {
        //TODO: MotorControlApplication
    }



    /*          Testing Purpose.  */
    private void simulateSensorListResponse() {

        Log.d("DMM", "Function Running : simulateSensorListResponse()");


        List<bean> illumList = new ArrayList<>();
        List<bean> tempHumList = new ArrayList<>();

        // Add Light Sensors - FIX: Use correct device type codes

        // Send to listeners with correct command ID
//        DeviceListBean illumBean = new SmartCurtainBean();

        for (SocketMessageListener listener : listeners) {
            listener.getMessage(Constants.ILLUM_UPDATE, "");    //TODO: Need to add bean type
        }
    }

    private void simulateDeviceStateResponse(bean device, int cache) {
        int commandID;
        Object bean;

        switch (device.getDeviceType()) {
            case Constants.LIGHT_601:
            case Constants.LIGHT_EXTEND_LO_COLOR_TEMP_GOODVB:
                commandID = Constants.UpdateLight;
                bean = createLightStateBean(device);
                break;

            case DeviceTypeCode.TH_SENSOR:
                commandID = Constants.THI_UPDATE;
                bean = createTempHumidityStateBean(device);
                break;

            case DeviceTypeCode.LX_SENSOR:
                commandID = Constants.ILLUM_UPDATE;
                bean = createLightSensorStateBean(device);
                break;

            case DeviceTypeCode.MOTION_SENSOR_ZONE:
                commandID = Constants.MotionSensorUpdate;
                bean = createMotionSensorStateBean(device);
                break;

            case DeviceTypeCode.SMOKE_SENSOR_ZONE:
                commandID = Constants.WarningSensor;
                bean = createSmokeDetectorStateBean(device);
                break;

            default:
                commandID = 0;
                bean = null;
                break;
        }

        if (bean != null) {
            for (SocketMessageListener listener : listeners) {
                listener.getMessage(commandID, bean);
            }
        }
    }

    /* Helper Function: Create device state beans for demonstration purposes */
    private Object createLightStateBean(bean device) {
        return new Object();
    }

    private Object createMotionSensorStateBean(bean device) {
        return new Object();
    }

    private Object createSmokeDetectorStateBean(bean device) {
        return new Object();
    }

    private Object createTempHumidityStateBean(bean device) { return new Object(); }

    private Object createLightSensorStateBean(bean device) { return new Object(); }

}