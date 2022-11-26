package weblogic.jms.dotnet.proxy.internal;

import java.util.Hashtable;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.encoders.BASE64Decoder;

public class EncrypUtil {
   public static final String USERNAME = "username";
   public static final String PASSWORD = "password";
   private static final String AES_PREFIX = "{AES}";

   static Hashtable decrypt(Transport transport, Hashtable map) throws Exception {
      Hashtable rtnMap = new Hashtable(map);
      Cipher aesDecrypt = createCipher(transport);
      rtnMap = decode(aesDecrypt, "java.naming.security.principal", rtnMap);
      rtnMap = decode(aesDecrypt, "java.naming.security.credentials", rtnMap);
      return rtnMap;
   }

   static String decryptString(Transport transport, String encodedString) throws Exception {
      Cipher aesDecrypt = createCipher(transport);
      return decryptString(aesDecrypt, encodedString);
   }

   private static Hashtable decode(Cipher aesDecrypt, String type, Hashtable aMap) throws Exception {
      int prefixLen = 33;
      int suffixLen = 13;
      Hashtable rtnMap = new Hashtable(aMap);
      if (rtnMap.containsKey(type)) {
         String sEncrypass = (String)rtnMap.get(type);
         if (sEncrypass != null && sEncrypass.startsWith("{AES}")) {
            sEncrypass = sEncrypass.substring("{AES}".length());
            byte[] dec = (new BASE64Decoder()).decodeBuffer(sEncrypass);
            byte[] dataBuf = aesDecrypt.doFinal(dec);
            int len = dataBuf.length - prefixLen - suffixLen;
            if (len < 0) {
               throw new Exception("The encrypted data for " + type + " is not right");
            }

            String clearText;
            if (len > 0) {
               byte[] utf8 = new byte[len];
               System.arraycopy(dataBuf, prefixLen, utf8, 0, len);
               clearText = new String(utf8, "US-ASCII");
            } else {
               clearText = "";
            }

            if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
               JMSDebug.JMSDotNetProxy.debug("Encrypted = true  for " + type + "; partial encrypted text : " + sEncrypass.substring(0, 5));
            }

            rtnMap.put(type, clearText);
         } else if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Encrypted = false  for " + type + "; partial text : *****");
         }
      }

      return rtnMap;
   }

   private static Cipher createCipher(Transport transport) throws Exception {
      long lKey = transport.getScratchId();
      byte[] w128 = new byte[16];

      for(int i = 0; i < 8; ++i) {
         w128[i] = (byte)((int)lKey);
         lKey >>>= 8;
      }

      w128[8] = -19;
      w128[9] = 29;
      w128[10] = -17;
      w128[11] = 74;
      w128[12] = -101;
      w128[13] = 37;
      w128[14] = -119;
      w128[15] = -95;
      byte[] aIV = new byte[]{123, w128[3], 34, w128[0], -8, w128[2], 34, w128[7], 121, w128[6], -67, w128[1], 100, w128[4], 99, -40};
      Cipher aesDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
      SecretKeySpec aSecKey = new SecretKeySpec(w128, "AES");
      IvParameterSpec aIvspec = new IvParameterSpec(aIV);
      aesDecrypt.init(2, aSecKey, aIvspec);
      return aesDecrypt;
   }

   private static String decryptString(Cipher aesDecrypt, String sEncrypass) throws Exception {
      int prefixLen = 33;
      int suffixLen = 13;
      String clearText;
      if (sEncrypass != null && sEncrypass.startsWith("{AES}")) {
         String sOrg = sEncrypass;
         sEncrypass = sEncrypass.substring("{AES}".length());
         byte[] dec = (new BASE64Decoder()).decodeBuffer(sEncrypass);
         byte[] dataBuf = aesDecrypt.doFinal(dec);
         int len = dataBuf.length - prefixLen - suffixLen;
         if (len <= 0) {
            throw new Exception("The encrypted data for " + sEncrypass + " is not right");
         }

         byte[] utf8 = new byte[len];
         System.arraycopy(dataBuf, prefixLen, utf8, 0, len);
         clearText = new String(utf8, "US-ASCII");
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Encrypted = true; partial encrypted text : " + sOrg.substring(0, 5));
         }
      } else {
         if (JMSDebug.JMSDotNetProxy.isDebugEnabled()) {
            JMSDebug.JMSDotNetProxy.debug("Encrypted = false; partial text : *****");
         }

         clearText = sEncrypass;
      }

      return clearText;
   }
}
