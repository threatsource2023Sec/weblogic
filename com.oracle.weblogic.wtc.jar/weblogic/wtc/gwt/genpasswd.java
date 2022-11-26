package weblogic.wtc.gwt;

import java.security.MessageDigest;
import java.text.Collator;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import weblogic.utils.encoders.BASE64Encoder;
import weblogic.wtc.jatmi.TPCrypt;

public final class genpasswd {
   public static void main(String[] args) throws Exception {
      if (args.length < 3) {
         System.out.println("Usage: genpasswd [-Daes] Key <LocalPassword|RemotePassword|AppPassword> <local|remote|application>");
      } else {
         BASE64Encoder encoder = new BASE64Encoder();
         String base64_pw;
         String base64_iv;
         String keyword;
         String key;
         String pw;
         byte[] rawKey256;
         byte[] encrypted;
         if (args.length == 4) {
            if (!"-Daes".equals(args[0])) {
               System.out.println("Usage: genpasswd [-Daes] Key <LocalPassword|RemotePassword|AppPassword> <local|remote|application>");
               return;
            }

            key = args[1];
            pw = args[2];
            if (pw.length() > 31) {
               System.out.println("password length can not be over 31 characters long!");
               return;
            }

            keyword = args[3];
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            sha.update(key.getBytes("UTF-8"), 0, key.getBytes("UTF-8").length);
            rawKey256 = sha.digest();
            SecretKeySpec skeySpec = new SecretKeySpec(rawKey256, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, skeySpec);
            encrypted = cipher.doFinal(pw.getBytes("UTF-8"));
            base64_pw = encoder.encodeBuffer(encrypted);
            base64_iv = encoder.encodeBuffer(cipher.getIV());
         } else {
            key = args[0];
            pw = args[1];
            if (pw.length() > 31) {
               System.out.println("password length can not be over 31 characters long!");
               return;
            }

            TPCrypt cipher = new TPCrypt();
            keyword = args[2];
            rawKey256 = new byte[8];
            System.arraycopy(cipher.randKey(), 0, rawKey256, 0, rawKey256.length);
            byte[] skey = new byte[8];
            cipher.pwToKey(key, skey);
            byte[] bPW = pw.getBytes();
            encrypted = new byte[32];
            byte[] raw_cipher_text = new byte[32];
            Arrays.fill(encrypted, (byte)0);
            Arrays.fill(raw_cipher_text, (byte)0);
            System.arraycopy(bPW, 0, encrypted, 0, bPW.length);
            cipher.setInitializationVector(rawKey256);
            cipher.crypt(encrypted, raw_cipher_text, encrypted.length, 1);
            base64_pw = encoder.encodeBuffer(raw_cipher_text);
            base64_iv = encoder.encodeBuffer(rawKey256);
         }

         Collator myCollator = Collator.getInstance();
         myCollator.setStrength(0);
         if (myCollator.compare(keyword, "local") == 0) {
            System.out.println("Local Password   : " + base64_pw);
            System.out.println("Local Password IV: " + base64_iv);
         } else if (myCollator.compare(keyword, "remote") == 0) {
            System.out.println("Remote Password   : " + base64_pw);
            System.out.println("Remote Password IV: " + base64_iv);
         } else if (myCollator.compare(keyword, "application") == 0) {
            System.out.println("App Password   : " + base64_pw);
            System.out.println("App Password IV: " + base64_iv);
         } else {
            System.out.println("Only local, remote, or application keyword are allowed");
         }

      }
   }
}
