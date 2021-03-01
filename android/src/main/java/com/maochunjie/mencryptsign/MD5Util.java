package com.maochunjie.mencryptsign;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * md5加密
     *
     * @param str 需要加密的数据
     * @return 加密结果
     */
    public static String getMD5String(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        messageDigest.update(str.getBytes());
        return byteArray2HexString(messageDigest.digest());
    }

    /**
     * MD5加密结果（由byte转换成String）
     *
     * @param bytes md5加密后得到的数组
     * @return md5加密结果
     */
    private static String byteArray2HexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }
}