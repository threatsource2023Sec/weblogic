package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import weblogic.utils.encoders.BASE64Decoder;

public final class PasswordUtils {
   public static final int MAXTIDENT = 30;

   public static String decryptPassword(String key, String iv64, String epasswd64, String encryptionType) {
      boolean traceEnabled = ntrace.getTraceLevel() == 1000373;
      if (traceEnabled) {
         ntrace.doTrace("[/Utilities/decryptPassword/");
      }

      if (key != null && iv64 != null && epasswd64 != null) {
         try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] iv = decoder.decodeBuffer(iv64);
            byte[] epasswd = decoder.decodeBuffer(epasswd64);
            byte[] skey;
            if (encryptionType != null && "AES".equalsIgnoreCase(encryptionType)) {
               MessageDigest sha = MessageDigest.getInstance("SHA-256");
               sha.update(key.getBytes("UTF-8"), 0, key.getBytes("UTF-8").length);
               skey = sha.digest();
               SecretKeySpec skeySpec = new SecretKeySpec(skey, "AES");
               IvParameterSpec ivSpec = new IvParameterSpec(iv);
               Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
               cipher.init(2, skeySpec, ivSpec);
               byte[] decrypted = cipher.doFinal(epasswd);
               String passwd = new String(decrypted, "UTF-8");
               if (traceEnabled) {
                  ntrace.doTrace("]/Utilities/decryptPassword/20/xxx");
               }

               return passwd;
            } else {
               TPCrypt cipher = new TPCrypt();
               skey = new byte[8];
               byte[] bpasswd = new byte[32];
               cipher.pwToKey(key, skey);
               cipher.setInitializationVector(iv);
               cipher.crypt(epasswd, bpasswd, epasswd.length, 0);

               int i;
               for(i = 0; i < 32 && bpasswd[i] != 0; ++i) {
               }

               String passwd = new String(bpasswd, 0, i);
               if (traceEnabled) {
                  ntrace.doTrace("]/Utilities/decryptPassword/30/xxx");
               }

               return passwd;
            }
         } catch (Exception var16) {
            if (traceEnabled) {
               ntrace.doTrace("]/Utilities/decryptPassword/30/" + var16);
            }

            return null;
         }
      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/Utilities/decryptPassword/10");
         }

         return null;
      }
   }
}
