package com.maochunjie.mencryptsign;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RNUtils {
    /**
     * ReadableMap 转 JSON 字符串
     *
     * @param readableMap
     * @return
     */
    public static String readableMapToJsonString(ReadableMap readableMap) {
        try {
            JSONObject jsonObject = new JSONObject();
            ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                switch (readableMap.getType(key)) {
                    case Null:
                        jsonObject.put(key, JSONObject.NULL);
                        break;
                    case Boolean:
                        jsonObject.put(key, readableMap.getBoolean(key));
                        break;
                    case Number:
                        jsonObject.put(key, readableMap.getDouble(key));
                        break;
                    case String:
                        jsonObject.put(key, readableMap.getString(key));
                        break;
                    case Map:
                        jsonObject.put(key, readableMapToJsonString(readableMap.getMap(key)));
                        break;
                    case Array:
                        jsonObject.put(key, readableArrayToJsonArray(readableMap.getArray(key)));
                        break;
                }
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONArray readableArrayToJsonArray(ReadableArray readableArray) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < readableArray.size(); i++) {
            switch (readableArray.getType(i)) {
                case Null:
                    jsonArray.put(JSONObject.NULL);
                    break;
                case Boolean:
                    jsonArray.put(readableArray.getBoolean(i));
                    break;
                case Number:
                    jsonArray.put(readableArray.getDouble(i));
                    break;
                case String:
                    jsonArray.put(readableArray.getString(i));
                    break;
                case Map:
                    jsonArray.put(readableMapToJsonString(readableArray.getMap(i)));
                    break;
                case Array:
                    jsonArray.put(readableArrayToJsonArray(readableArray.getArray(i)));
                    break;
            }
        }
        return jsonArray;
    }
}
