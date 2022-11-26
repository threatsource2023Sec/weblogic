package org.apache.xml.security.algorithms;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.xml.security.utils.JavaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class JCEMapper {
   private static final Logger LOG = LoggerFactory.getLogger(JCEMapper.class);
   private static Map algorithmsMap = new ConcurrentHashMap();
   private static String providerName;

   public static void register(String id, Algorithm algorithm) {
      JavaUtils.checkRegisterPermission();
      algorithmsMap.put(id, algorithm);
   }

   public static void registerDefaultAlgorithms() {
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#md5", new Algorithm("", "MD5", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#ripemd160", new Algorithm("", "RIPEMD160", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#sha1", new Algorithm("", "SHA-1", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#sha224", new Algorithm("", "SHA-224", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#sha256", new Algorithm("", "SHA-256", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#sha384", new Algorithm("", "SHA-384", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#sha512", new Algorithm("", "SHA-512", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#whirlpool", new Algorithm("", "WHIRLPOOL", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-224", new Algorithm("", "SHA3-224", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-256", new Algorithm("", "SHA3-256", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-384", new Algorithm("", "SHA3-384", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-512", new Algorithm("", "SHA3-512", "MessageDigest"));
      algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#dsa-sha1", new Algorithm("DSA", "SHA1withDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2009/xmldsig11#dsa-sha256", new Algorithm("DSA", "SHA256withDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-md5", new Algorithm("RSA", "MD5withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-ripemd160", new Algorithm("RSA", "RIPEMD160withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#rsa-sha1", new Algorithm("RSA", "SHA1withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha224", new Algorithm("RSA", "SHA224withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256", new Algorithm("RSA", "SHA256withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha384", new Algorithm("RSA", "SHA384withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512", new Algorithm("RSA", "SHA512withRSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha1-rsa-MGF1", new Algorithm("RSA", "SHA1withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha224-rsa-MGF1", new Algorithm("RSA", "SHA224withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha256-rsa-MGF1", new Algorithm("RSA", "SHA256withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha384-rsa-MGF1", new Algorithm("RSA", "SHA384withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha512-rsa-MGF1", new Algorithm("RSA", "SHA512withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-224-rsa-MGF1", new Algorithm("RSA", "SHA3-224withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-256-rsa-MGF1", new Algorithm("RSA", "SHA3-256withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-384-rsa-MGF1", new Algorithm("RSA", "SHA3-384withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#sha3-512-rsa-MGF1", new Algorithm("RSA", "SHA3-512withRSAandMGF1", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha1", new Algorithm("EC", "SHA1withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha224", new Algorithm("EC", "SHA224withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256", new Algorithm("EC", "SHA256withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha384", new Algorithm("EC", "SHA384withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha512", new Algorithm("EC", "SHA512withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#ecdsa-ripemd160", new Algorithm("EC", "RIPEMD160withECDSA", "Signature"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-md5", new Algorithm("", "HmacMD5", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-ripemd160", new Algorithm("", "HMACRIPEMD160", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2000/09/xmldsig#hmac-sha1", new Algorithm("", "HmacSHA1", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha224", new Algorithm("", "HmacSHA224", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha256", new Algorithm("", "HmacSHA256", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha384", new Algorithm("", "HmacSHA384", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#hmac-sha512", new Algorithm("", "HmacSHA512", "Mac", 0, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#tripledes-cbc", new Algorithm("DESede", "DESede/CBC/ISO10126Padding", "BlockEncryption", 192, 64));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes128-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 128, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes192-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 192, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#aes256-cbc", new Algorithm("AES", "AES/CBC/ISO10126Padding", "BlockEncryption", 256, 128));
      algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes128-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 128, 96));
      algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes192-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 192, 96));
      algorithmsMap.put("http://www.w3.org/2009/xmlenc11#aes256-gcm", new Algorithm("AES", "AES/GCM/NoPadding", "BlockEncryption", 256, 96));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#seed128-cbc", new Algorithm("SEED", "SEED/CBC/ISO10126Padding", "BlockEncryption", 128, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#camellia128-cbc", new Algorithm("Camellia", "Camellia/CBC/ISO10126Padding", "BlockEncryption", 128, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#camellia192-cbc", new Algorithm("Camellia", "Camellia/CBC/ISO10126Padding", "BlockEncryption", 192, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#camellia256-cbc", new Algorithm("Camellia", "Camellia/CBC/ISO10126Padding", "BlockEncryption", 256, 128));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#rsa-1_5", new Algorithm("RSA", "RSA/ECB/PKCS1Padding", "KeyTransport"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p", new Algorithm("RSA", "RSA/ECB/OAEPPadding", "KeyTransport"));
      algorithmsMap.put("http://www.w3.org/2009/xmlenc11#rsa-oaep", new Algorithm("RSA", "RSA/ECB/OAEPPadding", "KeyTransport"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#dh", new Algorithm("", "", "KeyAgreement"));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-tripledes", new Algorithm("DESede", "DESedeWrap", "SymmetricKeyWrap", 192, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes128", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 128, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes192", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 192, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmlenc#kw-aes256", new Algorithm("AES", "AESWrap", "SymmetricKeyWrap", 256, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#kw-camellia128", new Algorithm("Camellia", "CamelliaWrap", "SymmetricKeyWrap", 128, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#kw-camellia192", new Algorithm("Camellia", "CamelliaWrap", "SymmetricKeyWrap", 192, 0));
      algorithmsMap.put("http://www.w3.org/2001/04/xmldsig-more#kw-camellia256", new Algorithm("Camellia", "CamelliaWrap", "SymmetricKeyWrap", 256, 0));
      algorithmsMap.put("http://www.w3.org/2007/05/xmldsig-more#kw-seed128", new Algorithm("SEED", "SEEDWrap", "SymmetricKeyWrap", 128, 0));
   }

   public static String translateURItoJCEID(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.jceName : null;
   }

   public static String getAlgorithmClassFromURI(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.algorithmClass : null;
   }

   public static int getKeyLengthFromURI(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.keyLength : 0;
   }

   public static int getIVLengthFromURI(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.ivLength : 0;
   }

   public static String getJCEKeyAlgorithmFromURI(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.requiredKey : null;
   }

   public static String getJCEProviderFromURI(String algorithmURI) {
      Algorithm algorithm = getAlgorithm(algorithmURI);
      return algorithm != null ? algorithm.jceProvider : null;
   }

   private static Algorithm getAlgorithm(String algorithmURI) {
      LOG.debug("Request for URI {}", algorithmURI);
      return algorithmURI != null ? (Algorithm)algorithmsMap.get(algorithmURI) : null;
   }

   public static String getProviderId() {
      return providerName;
   }

   public static void setProviderId(String provider) {
      JavaUtils.checkRegisterPermission();
      providerName = provider;
   }

   public static class Algorithm {
      final String requiredKey;
      final String jceName;
      final String algorithmClass;
      final int keyLength;
      final int ivLength;
      final String jceProvider;

      public Algorithm(Element el) {
         this.requiredKey = el.getAttributeNS((String)null, "RequiredKey");
         this.jceName = el.getAttributeNS((String)null, "JCEName");
         this.algorithmClass = el.getAttributeNS((String)null, "AlgorithmClass");
         this.jceProvider = el.getAttributeNS((String)null, "JCEProvider");
         if (el.hasAttribute("KeyLength")) {
            this.keyLength = Integer.parseInt(el.getAttributeNS((String)null, "KeyLength"));
         } else {
            this.keyLength = 0;
         }

         if (el.hasAttribute("IVLength")) {
            this.ivLength = Integer.parseInt(el.getAttributeNS((String)null, "IVLength"));
         } else {
            this.ivLength = 0;
         }

      }

      public Algorithm(String requiredKey, String jceName) {
         this(requiredKey, jceName, (String)null, 0, 0);
      }

      public Algorithm(String requiredKey, String jceName, String algorithmClass) {
         this(requiredKey, jceName, algorithmClass, 0, 0);
      }

      public Algorithm(String requiredKey, String jceName, int keyLength) {
         this(requiredKey, jceName, (String)null, keyLength, 0);
      }

      public Algorithm(String requiredKey, String jceName, String algorithmClass, int keyLength, int ivLength) {
         this(requiredKey, jceName, algorithmClass, keyLength, ivLength, (String)null);
      }

      public Algorithm(String requiredKey, String jceName, String algorithmClass, int keyLength, int ivLength, String jceProvider) {
         this.requiredKey = requiredKey;
         this.jceName = jceName;
         this.algorithmClass = algorithmClass;
         this.keyLength = keyLength;
         this.ivLength = ivLength;
         this.jceProvider = jceProvider;
      }
   }
}
