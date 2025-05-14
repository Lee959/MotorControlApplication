package com.example.motorcontrolapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a collection of EPListBean devices.
 */
public class DeviceListBean {
    // The list of EPListBean devices
    private final List<EPListBean> deviceList;

    /**
     * Constructor that takes a list of EPListBean devices.
     *
     * @param deviceList The list of EPListBean devices
     */
    public DeviceListBean(List<EPListBean> deviceList) {
        this.deviceList = deviceList;
    }

    /**
     * Default constructor initializes an empty list of devices.
     */
    public DeviceListBean() {
        this.deviceList = new ArrayList<>();
    }

    /**
     * Returns the list of EPListBean devices.
     *
     * @return The list of EPListBean devices
     */
    public List<EPListBean> getDevices() {
        return deviceList;
    }

    /**
     * Sets the list of EPListBean devices.
     *
     * @param deviceList The list of EPListBean devices to set
     */
    public void setDevices(List<EPListBean> deviceList) {
        this.deviceList.clear();
        if (deviceList != null) {
            this.deviceList.addAll(deviceList);
        }
    }

    /**
     * Adds a new EPListBean device to the list.
     *
     * @param device The EPListBean device to add
     */
    public void addDevice(EPListBean device) {
        if (device != null) {
            this.deviceList.add(device);
        }
    }
}