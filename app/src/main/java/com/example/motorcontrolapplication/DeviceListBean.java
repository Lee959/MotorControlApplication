package com.example.motorcontrolapplication;

import java.util.ArrayList;
import java.util.List;


public class DeviceListBean {
    private final List<EPListBean> deviceList;


    public DeviceListBean(List<EPListBean> deviceList) {
        this.deviceList = deviceList;
    }

    public DeviceListBean() {
        this.deviceList = new ArrayList<>();
    }

    public List<EPListBean> getDevices() {
        return deviceList;
    }

    public void setDevices(List<EPListBean> deviceList) {
        this.deviceList.clear();
        if (deviceList != null) {
            this.deviceList.addAll(deviceList);
        }
    }

    public void addDevice(EPListBean device) {
        if (device != null) {
            this.deviceList.add(device);
        }
    }
}