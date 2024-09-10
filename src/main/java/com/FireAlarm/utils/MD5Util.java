package com.FireAlarm.utils;

import lombok.var;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public final class MD5Util {
    public static String encrypt(String strSrc) {
        try {
            char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            byte[] bytes = strSrc.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte b = bytes[i];
                chars[k++] = hexChars[b >>> 4 & 0xf];
                chars[k++] = hexChars[b & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    public static String calculateMD5(MultipartFile file) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        try {
            byte[] buffer = new byte[1024];
            int read;
            // 使用getInputStream()方法读取文件数据
            var is = file.getInputStream();
            while ((read = is.read(buffer)) != -1) {
                md.update(buffer, 0, read);
            }
            is.close();
        } catch (Exception e) {
            throw new RuntimeException("Could not read file for MD5 calculation", e);
        }

        return byteToHex(md.digest());
    }
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}