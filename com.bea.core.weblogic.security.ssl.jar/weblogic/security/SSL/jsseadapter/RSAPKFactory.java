package weblogic.security.SSL.jsseadapter;

import com.rsa.jsafe.JSAFE_PrivateKey;
import com.rsa.jsafe.JSAFE_SecretKey;
import com.rsa.jsafe.JSAFE_SymmetricCipher;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weblogic.security.internal.encryption.JSafeEncryptionServiceImpl;

public class RSAPKFactory {
   private static final Hashtable SUPPORTED_PK_TYPES = new Hashtable();
   private static final String ENC_PKCS8_RSA_PK_BEGIN_HEADER = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
   private static final String ENC_PKCS8_RSA_PK_END_HEADER = "-----END ENCRYPTED PRIVATE KEY-----";
   private static final String UNENC_PKCS1_RSA_PK_BEGIN_HEADER = "-----BEGIN RSA PRIVATE KEY-----";
   private static final String UNENC_PKCS1_RSA_PK_END_HEADER = "-----END RSA PRIVATE KEY-----";
   private static final String UNENC_PKCS8_RSA_PK_BEGIN_HEADER = "-----BEGIN PRIVATE KEY-----";
   private static final String UNENC_PKCS8_RSA_PK_END_HEADER = "-----END PRIVATE KEY-----";

   public static PrivateKey getPrivateKey(InputStream is, char[] keyPassword) throws KeyManagementException {
      byte[] rawBytes = null;
      byte[] keyMaterial = null;
      String pkHeader = null;
      String keyStr = null;
      PrivateKey pk = null;
      if (JaLogger.isLoggable(Level.FINEST)) {
         JaLogger.log(Level.FINEST, JaLogger.Component.SSLCONTEXT, "Private key input stream: {0}", is);
      }

      HeaderPKNVP nvp = null;
      PEMUtils.PEMData factory;
      if (is instanceof PEMInputStream) {
         factory = ((PEMInputStream)is).getPEMData();
         rawBytes = factory.getData();
         if (SUPPORTED_PK_TYPES.get(factory.getHeader()) != null) {
            nvp = new HeaderPKNVP(factory.getHeader(), factory.getData());
         } else if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "No supported header in PEMInputStream (found: " + factory.getHeader() + ")");
         }
      } else {
         try {
            rawBytes = JaSSLSupport.readFully(is);
            keyStr = new String(rawBytes);
         } catch (IOException var11) {
            if (JaLogger.isLoggable(Level.SEVERE)) {
               JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "Failed to read key material from the input stream: " + var11.getMessage());
            }
         }

         nvp = extractKeyBytesFromPEMData(keyStr);
      }

      if (nvp == null) {
         nvp = extractKeyBytesFromPEMData(new String(JaSSLSupport.convertDER2PEM(new ByteArrayInputStream(rawBytes)).toByteArray()));
      }

      if (nvp != null) {
         pkHeader = nvp.s;
         keyMaterial = nvp.b;
      }

      try {
         if (keyMaterial != null) {
            factory = null;
            KeyFactory factory = KeyFactory.getInstance("RSA");
            if ("-----BEGIN RSA PRIVATE KEY-----".equalsIgnoreCase(pkHeader)) {
               RSAPrivateCrtKeySpec keySpec = getRSAKeySpec(keyMaterial);
               pk = factory.generatePrivate(keySpec);
            } else if ("-----BEGIN PRIVATE KEY-----".equalsIgnoreCase(pkHeader)) {
               EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyMaterial);
               pk = factory.generatePrivate(keySpec);
            } else if ("-----BEGIN ENCRYPTED PRIVATE KEY-----".equalsIgnoreCase(pkHeader)) {
               pk = getEncryptedPKCS8PrivateKey(keyMaterial, keyPassword);
            }
         }

         return pk;
      } catch (Exception var10) {
         if (JaLogger.isLoggable(Level.SEVERE)) {
            JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, "Failed to create the private key: " + var10.getMessage());
         }

         throw new KeyManagementException(var10);
      }
   }

   private static RSAPrivateCrtKeySpec getRSAKeySpec(byte[] keyBytes) throws IOException {
      DERDecoder derDecoder = new DERDecoder(keyBytes);
      ASN1Object sequence = derDecoder.readObject();
      if (sequence.getType() != 16) {
         throw new IOException("Unexpected type; not a sequence");
      } else {
         derDecoder = sequence.getDecoder();
         derDecoder.readObject();
         BigInteger modulus = derDecoder.readObject().getBigInteger();
         BigInteger publicExp = derDecoder.readObject().getBigInteger();
         BigInteger privateExp = derDecoder.readObject().getBigInteger();
         BigInteger prime1 = derDecoder.readObject().getBigInteger();
         BigInteger prime2 = derDecoder.readObject().getBigInteger();
         BigInteger exp1 = derDecoder.readObject().getBigInteger();
         BigInteger exp2 = derDecoder.readObject().getBigInteger();
         BigInteger crtCoef = derDecoder.readObject().getBigInteger();
         RSAPrivateCrtKeySpec keySpec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
         return keySpec;
      }
   }

   private static PrivateKey getEncryptedPKCS8PrivateKey(byte[] encryptedKeyData, char[] pwd) throws Exception {
      JSAFE_SymmetricCipher decrypter = JSafeEncryptionServiceImpl.getNonFIPS140Ctx() == null ? JSAFE_SymmetricCipher.getInstance(encryptedKeyData, 0, "Java") : JSAFE_SymmetricCipher.getInstance(encryptedKeyData, 0, "Java", JSafeEncryptionServiceImpl.getNonFIPS140Ctx());
      JSAFE_SecretKey key = decrypter.getBlankKey();
      key.setPassword(pwd, 0, pwd.length);
      decrypter.decryptInit(key, (SecureRandom)null);
      JSAFE_PrivateKey pk = decrypter.unwrapPrivateKey(encryptedKeyData, 0, encryptedKeyData.length, true);
      return new JSAFE_PrivateKeyWrapper(pk);
   }

   private static HeaderPKNVP extractKeyBytesFromPEMData(String keyStr) {
      String pkHeader = null;
      byte[] keyMaterial = null;
      HeaderPKNVP nvp = null;
      if (keyStr != null && keyStr.length() > 0) {
         Iterator regExes = SUPPORTED_PK_TYPES.entrySet().iterator();

         try {
            while(regExes.hasNext()) {
               Map.Entry me = (Map.Entry)regExes.next();
               Matcher m = Pattern.compile((String)me.getValue(), 32).matcher(keyStr);
               if (m.find()) {
                  byte[] keyMaterial = JaSSLSupport.decodeData(m.group(1));
                  pkHeader = (String)me.getKey();
                  nvp = new HeaderPKNVP(pkHeader, keyMaterial);
                  break;
               }
            }
         } catch (IOException var7) {
            if (JaLogger.isLoggable(Level.SEVERE)) {
               JaLogger.log(Level.SEVERE, JaLogger.Component.SSLCONTEXT, var7, "Error extracting key bytes from PEM string.");
            }
         }
      }

      return nvp;
   }

   static {
      SUPPORTED_PK_TYPES.put("-----BEGIN ENCRYPTED PRIVATE KEY-----", "-----BEGIN ENCRYPTED PRIVATE KEY-----(.+?)-----END ENCRYPTED PRIVATE KEY-----");
      SUPPORTED_PK_TYPES.put("-----BEGIN RSA PRIVATE KEY-----", "-----BEGIN RSA PRIVATE KEY-----(.+?)-----END RSA PRIVATE KEY-----");
      SUPPORTED_PK_TYPES.put("-----BEGIN PRIVATE KEY-----", "-----BEGIN PRIVATE KEY-----(.+?)-----END PRIVATE KEY-----");
   }

   private static class HeaderPKNVP {
      String s;
      byte[] b;

      public HeaderPKNVP(String s, byte[] b) {
         this.s = s;
         this.b = b;
      }
   }
}
