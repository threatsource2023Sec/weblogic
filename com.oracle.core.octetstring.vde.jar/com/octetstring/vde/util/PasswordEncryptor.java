package com.octetstring.vde.util;

import com.octetstring.nls.Messages;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordEncryptor {
   private static final String UTF8 = "UTF8";
   static EncryptionHelper externalEncryptor = null;

   private PasswordEncryptor() {
   }

   public static boolean compare(String attempt, String value) {
      byte[] attemptBytes = getUTFBytes(attempt);
      return compare(attemptBytes, value);
   }

   public static boolean compare(byte[] attempt, String value) {
      if (!value.startsWith("{")) {
         return getUTFString(attempt).equals(value);
      } else {
         int end = value.indexOf("}");
         String type = value.substring(1, end);
         String encval = value.substring(end + 1);
         if (Logger.getInstance().isLogable(9)) {
            Logger.getInstance().log(9, new PasswordEncryptor(), Messages.getString("Encrypted_password_type____3") + type + Messages.getString("___encoded_as____4") + encval + "'");
         }

         String plain;
         if (type.equalsIgnoreCase("crypt")) {
            plain = doCrypt(getUTFString(attempt), encval.substring(0, 2));
            return plain == null ? false : plain.equals(encval);
         } else if (type.equalsIgnoreCase("sha")) {
            plain = doSHA(attempt);
            return plain == null ? false : encval.equals(plain);
         } else {
            byte[] hash;
            byte[] salt;
            String myenc;
            byte[][] hs;
            if (type.equalsIgnoreCase("ssha")) {
               hs = split(Base64.decode(encval), 20);
               hash = hs[0];
               salt = hs[1];
               myenc = doSSHA(attempt, salt);
               return myenc == null ? false : myenc.equals(encval);
            } else if (type.equalsIgnoreCase("ssha256")) {
               hs = split(Base64.decode(encval), 32);
               hash = hs[0];
               salt = hs[1];
               myenc = doSSHA256(attempt, salt);
               return myenc == null ? false : myenc.equals(encval);
            } else if (externalEncryptor != null) {
               plain = externalEncryptor.decrypt(value);
               return getUTFString(attempt).equals(plain);
            } else {
               return false;
            }
         }
      }
   }

   public static String doSHA(String plain) {
      byte[] plainBytes = getUTFBytes(plain);
      return doSHA(plainBytes);
   }

   public static String doSHA(byte[] plain) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         md.update(plain);
         return Base64.encode(md.digest());
      } catch (NoSuchAlgorithmException var2) {
         return null;
      }
   }

   public static String doSSHA(String plain) {
      byte[] plainBytes = getUTFBytes(plain);
      return doSSHA(plainBytes);
   }

   public static String doSSHA(byte[] plain) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         md.update(plain);
         byte[] salt = genSalt(4);
         md.update(salt);
         byte[] dig = md.digest();
         byte[] full = new byte[dig.length + salt.length];
         System.arraycopy(dig, 0, full, 0, dig.length);
         System.arraycopy(salt, 0, full, dig.length, salt.length);
         return Base64.encode(full);
      } catch (NoSuchAlgorithmException var5) {
         return null;
      }
   }

   public static String doSSHA(String plain, byte[] salt) {
      byte[] plainBytes = getUTFBytes(plain);
      return doSSHA(plainBytes, salt);
   }

   public static String doSSHA(byte[] plain, byte[] salt) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-1");
         md.update(plain);
         md.update(salt);
         byte[] dig = md.digest();
         byte[] full = new byte[dig.length + salt.length];
         System.arraycopy(dig, 0, full, 0, dig.length);
         System.arraycopy(salt, 0, full, dig.length, salt.length);
         return Base64.encode(full);
      } catch (NoSuchAlgorithmException var5) {
         return null;
      }
   }

   public static String doSSHA256(String plain) {
      byte[] plainBytes = getUTFBytes(plain);
      return doSSHA256(plainBytes);
   }

   public static String doSSHA256(byte[] plain) {
      byte[] salt = genSalt(4);
      return doSSHA256(plain, salt);
   }

   public static String doSSHA256(String plain, byte[] salt) {
      byte[] plainBytes = getUTFBytes(plain);
      return doSSHA256(plainBytes, salt);
   }

   public static String doSSHA256(byte[] plain, byte[] salt) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");
         md.update(plain);
         md.update(salt);
         byte[] dig = md.digest();
         byte[] full = new byte[dig.length + salt.length];
         System.arraycopy(dig, 0, full, 0, dig.length);
         System.arraycopy(salt, 0, full, dig.length, salt.length);
         return Base64.encode(full);
      } catch (NoSuchAlgorithmException var5) {
         return null;
      }
   }

   public static String doCrypt(String plain) {
      return UnixCrypt.crypt(plain);
   }

   public static String doCrypt(String plain, String salt) {
      return UnixCrypt.crypt(salt, plain);
   }

   public static String doExternal(String plain) {
      return externalEncryptor != null ? externalEncryptor.encrypt(plain) : null;
   }

   public static void setExternalEncryptionHelper(EncryptionHelper encryptor) {
      externalEncryptor = encryptor;
   }

   private static byte[] genSalt(int numBytes) {
      byte[] salt = new byte[numBytes];
      (new Random()).nextBytes(salt);
      return salt;
   }

   private static byte[][] split(byte[] src, int n) {
      byte[] l;
      byte[] r;
      if (src.length <= n) {
         l = src;
         r = new byte[0];
      } else {
         l = new byte[n];
         r = new byte[src.length - n];
         System.arraycopy(src, 0, l, 0, n);
         System.arraycopy(src, n, r, 0, r.length);
      }

      byte[][] lr = new byte[][]{l, r};
      return lr;
   }

   public static byte[] getUTFBytes(String aString) {
      byte[] value;
      try {
         value = aString.getBytes("UTF8");
      } catch (UnsupportedEncodingException var3) {
         value = aString.getBytes();
      }

      return value;
   }

   public static String getUTFString(byte[] bytes) {
      String value;
      try {
         value = new String(bytes, "UTF8");
      } catch (UnsupportedEncodingException var3) {
         value = new String(bytes);
      }

      return value;
   }
}
