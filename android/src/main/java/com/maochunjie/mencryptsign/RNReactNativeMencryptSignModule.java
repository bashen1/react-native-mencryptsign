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
            map.putString("code", "1");
            p.resolve(map);
        } catch (Exception e) {
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
            String dataString = RNUtils.readableMapToJsonString(data);
            String result = getDeviceInfo(dataString);
            JSONObject jsonObject = new JSONObject(result);
            sessionId = jsonObject.getString("sessionId");
            deviceInfo = jsonObject.getString("deviceInfo");
            map.putString("sessionId", sessionId);
            map.putString("deviceInfo", deviceInfo);
            map.putString("code", "1");
            p.resolve(map);
        } catch (Exception e) {
            map.putString("code", "0");
            p.resolve(map);
        }
    }

    public native String getToken(String param);

    public native String getDeviceInfo(String param);
}
