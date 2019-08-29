//package com.intellisyscorp.fitzme_android.security;
//
//import org.apache.commons.codec.binary.Base64;
//
//import java.util.Arrays;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//
///**
// * AES256 암호화 & 복호화
// * @author 유민호
// * @since 2019.04.23
// */
//public class AES256 {
//
//    /**
//     * Security AES256 암호화 Key / Iv
//     **************************************************************************************************************************************/
//    private static final String AESKEY = "DXQr7Ut>Bx49mT;z";                                                         // AES256 KEY
//    private static final String AESIV = "R}e6P;yhX9";                                                                // AES256 IV
//
//    /**
//     * Static String variable
//     **************************************************************************************************************************************/
//    private static final String UTF8 = "utf-8";
//    private static final String AES = "AES";
//    private static final String INSTANCE = "AES/CBC/PKCS5PADDING";
//
//    /**
//     * 인코딩
//     **************************************************************************************************************************************/
//    public String encrypt(String clearText) {
//        try {
//            byte[] bytePass = AESKEY.getBytes(UTF8);
//            byte[] byteV = AESIV.getBytes(UTF8);
//
//            byte[] byteKey = Arrays.copyOf(bytePass, 32);
//            byte[] byteIV = Arrays.copyOf(byteV, 16);
//
//            SecretKeySpec skeySpec = new SecretKeySpec(byteKey, AES);
//            IvParameterSpec ivSpec = new IvParameterSpec(byteIV);
//
//            Cipher cipher = Cipher.getInstance(INSTANCE);
//            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
//
//            byte[] byteText = clearText.getBytes(UTF8);
//            byte[] buf = cipher.doFinal(byteText);
//
//            byte[] byteBase64 = Base64.encodeBase64(buf);
//
//            return new String(byteBase64);
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }
//
//    /**
//     * 디코딩
//     **************************************************************************************************************************************/
//    public String decrypt(String data) {
//        try {
//            byte[] byteData = Base64.decodeBase64(data.getBytes(UTF8));
//            byte[] bytePass = AESKEY.getBytes(UTF8);
//            byte[] byteV = AESIV.getBytes(UTF8);
//
//            byte[] byteKey = Arrays.copyOf(bytePass, 32);
//            byte[] byteIV = Arrays.copyOf(byteV, 16);
//
//            SecretKeySpec skeySpec = new SecretKeySpec(byteKey, AES);
//            IvParameterSpec ivSpec = new IvParameterSpec(byteIV);
//
//            Cipher cipher = Cipher.getInstance(INSTANCE);
//            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
//
//            byte[] byteText = cipher.doFinal(byteData);
//
//            return new String(byteText);
//        } catch (Exception ex) {
//            return ex.getMessage();
//        }
//    }
//}