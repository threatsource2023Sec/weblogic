package org.apache.xml.security.encryption;

import java.security.AccessController;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class XMLCipherUtil {
   private static final Logger LOG = LoggerFactory.getLogger(XMLCipherUtil.class);
   private static final boolean gcmUseIvParameterSpec = (Boolean)AccessController.doPrivileged(() -> {
      return Boolean.getBoolean("org.apache.xml.security.cipher.gcm.useIvParameterSpec");
   });

   public static AlgorithmParameterSpec constructBlockCipherParameters(String algorithm, byte[] iv, Class callingClass) {
      if (!"http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(algorithm) && !"http://www.w3.org/2009/xmlenc11#aes192-gcm".equals(algorithm) && !"http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(algorithm)) {
         LOG.debug("Saw non-AES-GCM mode block cipher, returning IvParameterSpec: {}", algorithm);
         return new IvParameterSpec(iv);
      } else {
         return constructBlockCipherParametersForGCMAlgorithm(algorithm, iv, callingClass);
      }
   }

   public static AlgorithmParameterSpec constructBlockCipherParameters(boolean gcmAlgorithm, byte[] iv, Class callingClass) {
      if (gcmAlgorithm) {
         return constructBlockCipherParametersForGCMAlgorithm("AES/GCM/NoPadding", iv, callingClass);
      } else {
         LOG.debug("Saw non-AES-GCM mode block cipher, returning IvParameterSpec");
         return new IvParameterSpec(iv);
      }
   }

   private static AlgorithmParameterSpec constructBlockCipherParametersForGCMAlgorithm(String algorithm, byte[] iv, Class callingClass) {
      if (gcmUseIvParameterSpec) {
         LOG.debug("Saw AES-GCM block cipher, using IvParameterSpec due to system property override: {}", algorithm);
         return new IvParameterSpec(iv);
      } else {
         LOG.debug("Saw AES-GCM block cipher, attempting to create GCMParameterSpec: {}", algorithm);

         try {
            Class gcmSpecClass = ClassLoaderUtils.loadClass("javax.crypto.spec.GCMParameterSpec", callingClass);
            AlgorithmParameterSpec gcmSpec = (AlgorithmParameterSpec)gcmSpecClass.getConstructor(Integer.TYPE, byte[].class).newInstance(128, iv);
            LOG.debug("Successfully created GCMParameterSpec");
            return gcmSpec;
         } catch (Exception var5) {
            LOG.debug("Failed to create GCMParameterSpec, falling back to returning IvParameterSpec", var5);
            return new IvParameterSpec(iv);
         }
      }
   }
}
