package com.bea.security.utils.wss;

import com.bea.common.security.ProvidersLogger;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class PasswordDigestUtils {
   public static final String UTF_8 = "UTF-8";

   private PasswordDigestUtils() {
   }

   public static byte[] getPasswordDigest(byte[] nonce, String created, byte[] password) throws NoSuchAlgorithmException, IllegalArgumentException {
      if (nonce != null && nonce.length != 0) {
         if (created != null && !created.equals("")) {
            if (password != null && password.length != 0) {
               MessageDigest md = MessageDigest.getInstance("SHA-1");
               md.update(nonce);

               try {
                  md.update(created.getBytes("UTF-8"));
               } catch (UnsupportedEncodingException var5) {
                  throw new NoSuchAlgorithmException(ProvidersLogger.getUnablePasswordDigestUtf8Required(var5.getMessage()));
               }

               md.update(password);
               byte[] digest = md.digest();
               return digest;
            } else {
               throw new IllegalArgumentException(ProvidersLogger.getUnablePasswordDigestWithNullField("Password"));
            }
         } else {
            throw new IllegalArgumentException(ProvidersLogger.getUnablePasswordDigestWithNullField("Creation Timestamp"));
         }
      } else {
         throw new IllegalArgumentException(ProvidersLogger.getUnablePasswordDigestWithNullField("Nonce"));
      }
   }

   public static boolean verifyDigest(byte[] nonce, String created, byte[] password, byte[] decodedPasswordDigestValue) throws NoSuchAlgorithmException, IllegalArgumentException {
      byte[] calculated = getPasswordDigest(nonce, created, password);
      return Arrays.equals(calculated, decodedPasswordDigestValue);
   }

   public static byte[] getDerivedKey(byte[] salt, int iteration, byte[] password) throws NoSuchAlgorithmException, IllegalArgumentException {
      if (password != null && password.length != 0) {
         if (salt != null && salt.length != 0) {
            int iter = iteration;
            if (iteration <= 0) {
               iter = 1000;
            }

            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password);
            md.update(salt);
            byte[] digest = md.digest();

            for(int count = 1; count != iter; ++count) {
               md.update(digest);
               digest = md.digest();
            }

            return digest;
         } else {
            throw new IllegalArgumentException(ProvidersLogger.getUnableDeriveKeyWithNullField("Salt"));
         }
      } else {
         throw new IllegalArgumentException(ProvidersLogger.getUnableDeriveKeyWithNullField("Password"));
      }
   }
}
