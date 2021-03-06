package com.cheanda.a8805.base;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.cheanda.a8805.ui.ImageDownLoader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android_serialport_api.SerialPort;
import okhttp3.OkHttpClient;

/**
 * Created by dell on 2016/12/26.
 */

public class MyApplication extends Application{
    private static Context mContext;
    private static Handler mHandler;
    private SerialPort mSerialPort = null;


    /**
     * 获取全局上下文
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    public static Handler getmHandler() {
        return mHandler;
    }

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
        if (mSerialPort == null) {
            mSerialPort = new SerialPort(new File("/dev/ttyS2"), 19200, 0);
        }
        return mSerialPort;
    }
    public void closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }
    /**
     * 程序入口,初始化上下文
     */
    @Override
    public void onCreate() {
        super.onCreate();

        mHandler = new Handler();
        mContext = getApplicationContext();
        Picasso.setSingletonInstance(new Picasso.Builder(mContext).
                downloader(new ImageDownLoader(getUnsafeOkHttpClient()))
                .build());

    }




    public static OkHttpClient getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        X509Certificate[] chain,
                        String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();
            OkHttpClient okHttpClient = new OkHttpClient.Builder().sslSocketFactory(sslSocketFactory, new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }).hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


}
