package com.example.motorcontrolapplication;

public class SmartCurtainBean extends bean {
    private int direction;      // 1 正传 0反转


    // Constructor with Default Direction (1)
    public SmartCurtainBean(String name, String id, int deviceType, boolean linkStatus) {
        super(name, id, deviceType, linkStatus);
        this.direction = 1;
    }

    // Constructor without Default Direction
    public SmartCurtainBean(String name, String id, int deviceType, boolean linkStatus, int direction) {
        super(name, id, deviceType, linkStatus);
        this.direction = direction;
    }

    public int getDirection() { return this.direction; }
    public void setDirection(int direction) { this.direction = direction; }


}

/*
infobean	class	设备实体
direction	int	    1 正传 0反转

 */
