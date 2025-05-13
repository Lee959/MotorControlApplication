package com.example.motorcontrolapplication.owon.sdk.util;

public interface SocketMessageListener {

    void getMessage(int commandID, Object bean);
}