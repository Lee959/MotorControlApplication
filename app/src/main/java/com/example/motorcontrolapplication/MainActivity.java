package com.example.motorcontrolapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motorcontrolapplication.owon.sdk.util.Constants;
import com.example.motorcontrolapplication.owon.sdk.util.DeviceMessagesManager;
import com.example.motorcontrolapplication.owon.sdk.util.SocketMessageListener;
import com.example.motorcontrolapplication.DeviceListBean;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SocketMessageListener {

    private Button btnForward, btnBackward, btnStop;
    private TextView tvStatus, resultTextView;
    private DeviceMessagesManager deviceManager;
    private EPListBean selectedDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnForward = findViewById(R.id.btn_forward);
        btnBackward = findViewById(R.id.btn_backward);
        btnStop = findViewById(R.id.btn_stop);
        tvStatus = findViewById(R.id.tv_status);
        resultTextView = findViewById(R.id.result_textView);

        btnStop.setText("停止");

        deviceManager = DeviceMessagesManager.getInstance();
        deviceManager.registerMessageListener(this);

        btnForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDevice != null) {
                    moveCurtain(1); // Forward rotation (open)
                } else {
                    showNoDeviceError();
                }
            }
        });

        btnBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDevice != null) {
                    moveCurtain(0); // Backward rotation (close)
                } else {
                    showNoDeviceError();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDevice != null) {
                    stopCurtain();
                } else {
                    showNoDeviceError();
                }
            }
        });

        deviceManager.GetEpList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deviceManager.unregisterMessageListener(this);
    }

    private void moveCurtain(int direction) {
        tvStatus.setText(direction == 1 ? "正在打开窗帘..." : "正在关闭窗帘...");
        deviceManager.SmartCurtainMove(selectedDevice, direction);
    }

    private void stopCurtain() {
        tvStatus.setText("停止窗帘...");
        deviceManager.SmartCurtainStop(selectedDevice);
    }

    private void showNoDeviceError() {
        Toast.makeText(this, "未找到窗帘电机设备，请检查设备连接", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getMessage(int commandID, Object bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (commandID) {
                    case Constants.ZigBeeGetEPList:
                        handleDeviceList(bean);
                        break;
                    default:
                        updateResultText("收到未处理消息: " + commandID);
                        break;
                }
            }
        });
    }

    private void handleDeviceList(Object bean) {
        if (bean instanceof DeviceListBean) {
            DeviceListBean deviceListBean = (DeviceListBean) bean;

            List<EPListBean> devices = deviceListBean.getDevices();

            if (devices == null || devices.isEmpty()) {
                updateStatus("未发现设备");
                return;
            }

            for (EPListBean device : devices) {
                if (device.getDeviceType() == DeviceTypeCode.WARN_MOTOR) {
                    selectedDevice = device;
                    updateStatus("找到窗帘电机: " + device.getName() +
                            (device.isLinkStatus() ? " (在线)" : " (离线)"));
                    return;
                }
            }

            updateStatus("未找到窗帘电机设备");
        } else {
            updateStatus("接收到未知设备列表类型");
        }
    }

    private void updateStatus(String message) {
        tvStatus.setText(message);
    }

    private void updateResultText(String message) {
        resultTextView.setText(message);
    }
}