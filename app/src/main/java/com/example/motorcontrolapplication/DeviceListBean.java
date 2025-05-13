package com.example.motorcontrolapplication;

import java.util.List;

public class DeviceListBean {
    private List<bean> devices;
    public DeviceListBean(List<bean> devices) {
        this.devices = devices;
    }
    public List<bean> getDevices() {
        return devices;
    }
    public void setDevices(List<bean> devices) {
        this.devices = devices;
    }
}
