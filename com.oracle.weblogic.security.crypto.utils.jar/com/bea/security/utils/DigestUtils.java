package com.bea.security.utils;

import com.bea.common.security.utils.encoders.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtils {
   public static final String UTF_8 = "UTF-8";
   public static final String DIGEST_ALGORITHM_SHA1 = "SHA1";

   public static String digestSHA1(String data) throws NoSuchAlgorithmException, UnsupportedEncodingException {
      return digest(data.getBytes("UTF-8"), "SHA1");
   }

   public static String digest(byte[] data, String algorithm) throws NoSuchAlgorithmException {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
      messageDigest.update(data);
      byte[] digest = messageDigest.digest();
      BASE64Encoder encoder = new BASE64Encoder();
      return encoder.encodeBuffer(digest);
   }
}
