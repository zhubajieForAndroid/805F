package com.cheanda.a8805.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.InvalidParameterException;

import android_serialport_api.SerialPort;

public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    private ReadThread mReadThread;
    private InputStream mInputStream;
    public OutputStream mOutputStream;
    public SerialPort mSerialPort;
    private MyApplication mApplication;
    //异或和的临时值
    private byte count;
    // 定义一个包的最大长度
    private int maxLength = 40;
    private byte[] buffer = new byte[maxLength];
    // 每次收到实际长度
    private int available = 0;
    // 当前已经收到包的总长度
    private int currentLength = 0;
    // 协议头长度1个字节（开始符1）
    private int headerLength =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class ReadThread extends Thread {
        @Override
        public void run() {
            while (!isInterrupted()) {
                count = 0;
                try {
                    available = mInputStream.available();
                    if (available > 0) {
                        // 防止超出数组最大长度导致溢出
                        if (available > maxLength - currentLength) {
                            available = maxLength - currentLength;
                        }
                        mInputStream.read(buffer, currentLength, available);
                        currentLength += available;
                    }

                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                int cursor = 0;
                // 如果当前收到包大于头的长度，则解析当前包
                while (currentLength >= headerLength) {
                    // 取到头部第一个字节,不是以2a开头的 丢弃
                    if (buffer[cursor] != 0x2a) {
                        currentLength = 0;
                        ++cursor;
                        //从新初始化数组
                        buffer = null;
                        buffer = new byte[maxLength];
                        continue;
                    }

                    //得到包的长度位
                    int dataLenght = parseLen(buffer);
                    // 如果当前获取到长度小于整个包的长度，则跳出循环等待继续接收数据
                    if (currentLength < dataLenght) {
                        break;
                    }

                    //数据接受完成
                    if (dataLenght == currentLength){
                        // 如果内容包的长度大于最大内容长度或者小于等于0，则说明这个包有问题，丢弃
                        if (dataLenght>0) {
                            if (dataLenght <= 0 || dataLenght > maxLength) {
                                currentLength = 0;
                                //从新初始化数组
                                buffer = null;
                                buffer = new byte[maxLength];
                                break;
                            }
                        }

                        if (buffer[currentLength-1] != 0x23){
                            //结束符油问题,不是完整的包,丢弃
                            currentLength = 0;
                            //从新初始化数组
                            buffer = null;
                            buffer = new byte[maxLength];
                            break;
                        }

                        for (int i = 0; i < currentLength-2; i++) {
                            count ^= buffer[i];
                        }
                        //检查校验位
                        if (count != buffer[currentLength-2]){
                            //校验位不成功,丢弃
                            currentLength = 0;
                            //从新初始化数组
                            buffer = null;
                            buffer = new byte[maxLength];
                            break;
                        }

                        // 一个完整包即产生
                        onDataReceived(buffer, currentLength);
                        currentLength = 0;
                    }else {
                        break;
                    }
                }
                // 残留字节移到缓冲区首
                if (currentLength > 0 && cursor > 0) {
                    System.arraycopy(buffer, cursor, buffer, 0, currentLength);
                }
            }
        }
    }


    /**
     * 获取协议内容长度
     * @param
     * @return
     */
    public int parseLen(byte buffer[]) {
        //获取协议长度位
        byte a = buffer[1];
        String s = String.valueOf(a);
        int result = Integer.parseInt(s, 10);
        return result;
    }

    protected abstract void onDataReceived(final byte[] buffer, final int index);

    @Override
    protected void onResume() {
        super.onResume();
        //初始化串口,打开串口
        mApplication = (MyApplication) getApplication();
        try {
            mSerialPort = mApplication.getSerialPort();
            mInputStream = mSerialPort.getInputStream();
            mOutputStream = mSerialPort.getOutputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        } catch (SecurityException e) {
            Toast.makeText(this, "没有串口权限", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "无法打开一个未知的串口", Toast.LENGTH_SHORT).show();
        } catch (InvalidParameterException e) {
            Toast.makeText(this, "请先配置串口", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReadThread != null)
            mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;
        Log.d(TAG, "onPause: 销毁");
    }
   /* @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReadThread != null)
            mReadThread.interrupt();
        mApplication.closeSerialPort();
        mSerialPort = null;
        Log.d(TAG, "onDestroy: 销毁");
    }*/
}
