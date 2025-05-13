package com.example.motorcontrolapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.motorcontrolapplication.owon.sdk.util.Constants;
import com.example.motorcontrolapplication.owon.sdk.util.DeviceMessagesManager;
import com.example.motorcontrolapplication.owon.sdk.util.SocketMessageListener;

import java.util.List;


public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MotorControlActivity";


    private DeviceMessagesManager deviceMessagesManager;
    private SmartCurtainBean curtainMotor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnForward;
        Button btnBackward;
        Button btnStop;
        TextView result;

        btnForward = findViewById(R.id.btn_forward);
        btnBackward = findViewById(R.id.btn_backward);
        btnStop = findViewById(R.id.btn_stop);
        result = findViewById(R.id.result_textView);

        deviceMessagesManager = DeviceMessagesManager.getInstance();
//        deviceMessagesManager.registerMessageListener();

    }


    private void handleDeviceList(Object bean) {
        if (bean instanceof List<?>) {
            List<?> deviceList = (List<?>) bean;

            for (Object device : deviceList) {
                if (device instanceof SmartCurtainBean) {
                    bean deviceBean = (SmartCurtainBean) device;

                    // 找到窗帘电机设备
                    if (deviceBean.getDeviceType() == DeviceTypeCode.WARN_MOTOR) {
                        Log.d(TAG, "Found curtain motor: " + curtainMotor.getName());

                        // 在UI线程上更新UI
                        runOnUiThread(() -> {
//                            Toast.makeText(MotorControlActivity.this,
//                                    "找到窗帘电机: " + curtainMotor.getName(),
//                                    Toast.LENGTH_SHORT).show();

                        });

                        break;
                    }
                }
            }

            if (curtainMotor == null) {
                // 未找到窗帘电机
                runOnUiThread(() -> {
//                    Toast.makeText(MotorControlActivity.this,
//                            "未找到窗帘电机设备",
//                            Toast.LENGTH_SHORT).show();
                });
            }
        }
    }

    public void onClick(View v) {
        if (curtainMotor == null) {
            Toast.makeText(this, "窗帘电机未连接", Toast.LENGTH_SHORT).show();
            return;
        }

        int id = v.getId();

        if (id == R.id.btn_forward) {
            // 控制窗帘电机正转
            deviceMessagesManager.SmartCurtainMove(curtainMotor, 1);
            Toast.makeText(this, "窗帘正在打开", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_backward) {
            // 控制窗帘电机反转
            deviceMessagesManager.SmartCurtainMove(curtainMotor, 0);
            Toast.makeText(this, "窗帘正在关闭", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.btn_stop) {
            // 控制窗帘电机停止
            deviceMessagesManager.SmartCurtainStop(curtainMotor);
            Toast.makeText(this, "窗帘已停止", Toast.LENGTH_SHORT).show();
        }
    }
}