package com.maochunjie.mencryptsign;

import com.facebook.react.bridge.*;

import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class RNReactNativeMencryptSignModule extends ReactContextBaseJavaModule {

    static {
        System.loadLibrary("mmbKey");
    }

    private final ReactApplicationContext reactContext;

    public RNReactNativeMencryptSignModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNReactNativeMencryptSign";
    }

    @ReactMethod
    public void makeSign(final ReadableMap data, final Promise p) {
        Map<String, Object> params = data.getMap("params").toHashMap();
        WritableMap map = Arguments.createMap();
        try {
            Map<String, Object> sortedMap = new TreeMap<String, Object>(params);
            String prestr = "";
            for (Map.Entry<String, Object> entry : sortedMap.entrySet()) {
                String keyStr = entry.getKey();
                String valueStr = (String) entry.getValue();
                if (!keyStr.equals("") && !valueStr.equals("")) {
                    prestr = prestr + keyStr + valueStr;
                }
            }
            String paramStr = prestr.toUpperCase();
            String tokenRes = getToken(paramStr);

            map.putString("paramStr", paramStr);
            map.putString("sign", tokenRes);
            map.putString("errName", "");
            map.putString("errMessage", "");
            map.putString("code", "1");
            p.resolve(map);
        } catch (Exception e) {
            map.putString("paramStr", "");
            map.putString("sign", "");
            map.putString("errName", "JavaError");
            map.putString("errMessage", e.getMessage());
            map.putString("code", "0");
            p.resolve(map);
        }
    }

    @ReactMethod
    public void makeDeviceInfo(final ReadableMap data, final Promise p) {
        WritableMap map = Arguments.createMap();
        try {
            String sessionId = "";
            String deviceInfo = "";
            String errName = "";
            String errMessage = "";
            String dataString = RNUtils.readableMapToJsonString(data);
            String result = getDeviceInfo(dataString);
            JSONObject jsonObject = new JSONObject(result);
            sessionId = jsonObject.getString("sessionId");
            deviceInfo = jsonObject.getString("deviceInfo");
            errName = jsonObject.getString("errName");
            errMessage = jsonObject.getString("errMessage");
            if (!errName.isEmpty()) {
                map.putString("code", "0");
            } else {
                map.putString("code", "1");
            }
            map.putString("sessionId", sessionId);
            map.putString("deviceInfo", deviceInfo);
            map.putString("errName", errName);
            map.putString("errMessage", errMessage);
            p.resolve(map);
        } catch (Exception e) {
            map.putString("code", "0");
            map.putString("sessionId", "");
            map.putString("deviceInfo", "");
            map.putString("errName", "JavaError");
            map.putString("errMessage", e.getMessage());
            p.resolve(map);
        }
    }

    @ReactMethod
    public void makeFixId(final ReadableMap data, final Promise p) {
        WritableMap map = Arguments.createMap();
        try {
            String systemDevId = "";
            String fixDevId = "";
            String errName = "";
            String errMessage = "";
            String dataString = RNUtils.readableMapToJsonString(data);
            String result = getFixId(dataString);

            JSONObject jsonObject = new JSONObject(result);
            systemDevId = jsonObject.getString("systemDevId");
            fixDevId = jsonObject.getString("fixDevId");
            errName = jsonObject.getString("errName");
            errMessage = jsonObject.getString("errMessage");

            map.putString("systemDevId", systemDevId);
            map.putString("fixDevId", fixDevId);
            map.putString("errName", errName);
            map.putString("errMessage", errMessage);
            p.resolve(map);
        } catch (Exception e) {
            map.putString("systemDevId", "");
            map.putString("fixDevId", "");
            map.putString("errName", "JavaError");
            map.putString("errMessage", e.getMessage());
            p.resolve(map);
        }
    }

    public native String getToken(String param);

    public native String getDeviceInfo(String param);

    public native String getFixId(String param);
}
