package org.opensaml.xmlsec.algorithm;

import com.google.common.base.Strings;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.opensaml.core.config.ConfigurationService;
import org.opensaml.security.credential.BasicCredential;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.crypto.KeySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AlgorithmSupport {
   private static final Logger LOG = LoggerFactory.getLogger(AlgorithmSupport.class);

   private AlgorithmSupport() {
   }

   @Nullable
   public static AlgorithmRegistry getGlobalAlgorithmRegistry() {
      return (AlgorithmRegistry)ConfigurationService.get(AlgorithmRegistry.class);
   }

   public static boolean isKeyEncryptionAlgorithm(@Nullable AlgorithmDescriptor algorithm) {
      if (algorithm == null) {
         return false;
      } else {
         switch (algorithm.getType()) {
            case KeyTransport:
            case SymmetricKeyWrap:
               return true;
            default:
               return false;
         }
      }
   }

   public static boolean isDataEncryptionAlgorithm(@Nullable AlgorithmDescriptor algorithm) {
      if (algorithm == null) {
         return false;
      } else {
         switch (algorithm.getType()) {
            case BlockEncryption:
               return true;
            default:
               return false;
         }
      }
   }

   public static boolean credentialSupportsAlgorithmForSigning(@Nullable Credential credential, @Nullable AlgorithmDescriptor algorithm) {
      if (credential != null && algorithm != null) {
         Key key = CredentialSupport.extractSigningKey(credential);
         if (key == null) {
            return false;
         } else {
            switch (algorithm.getType()) {
               case Signature:
                  if (!(key instanceof PrivateKey)) {
                     return false;
                  }
                  break;
               case Mac:
                  if (!(key instanceof SecretKey)) {
                     return false;
                  }
                  break;
               default:
                  return false;
            }

            return checkKeyAlgorithmAndLength(key, algorithm);
         }
      } else {
         return false;
      }
   }

   public static boolean credentialSupportsAlgorithmForEncryption(@Nullable Credential credential, @Nullable AlgorithmDescriptor algorithm) {
      if (credential != null && algorithm != null) {
         Key key = CredentialSupport.extractEncryptionKey(credential);
         if (key == null) {
            return false;
         } else {
            switch (algorithm.getType()) {
               case KeyTransport:
                  if (!(key instanceof PublicKey)) {
                     return false;
                  }
                  break;
               case SymmetricKeyWrap:
               case BlockEncryption:
                  if (!(key instanceof SecretKey)) {
                     return false;
                  }
                  break;
               default:
                  return false;
            }

            return checkKeyAlgorithmAndLength(key, algorithm);
         }
      } else {
         return false;
      }
   }

   public static boolean checkKeyAlgorithmAndLength(@Nonnull Key key, @Nonnull AlgorithmDescriptor algorithm) {
      if (algorithm instanceof KeySpecifiedAlgorithm) {
         String specifiedKey = ((KeySpecifiedAlgorithm)algorithm).getKey();
         if (!specifiedKey.equals(key.getAlgorithm())) {
            return false;
         }
      }

      if (algorithm instanceof KeyLengthSpecifiedAlgorithm) {
         Integer specifiedKeyLength = ((KeyLengthSpecifiedAlgorithm)algorithm).getKeyLength();
         if (!specifiedKeyLength.equals(KeySupport.getKeyLength(key))) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   public static String getAlgorithmID(@Nonnull String algorithmURI) {
      AlgorithmRegistry registry = getGlobalAlgorithmRegistry();
      if (registry != null) {
         AlgorithmDescriptor descriptor = registry.get(algorithmURI);
         if (descriptor != null) {
            return descriptor.getJCAAlgorithmID();
         }
      }

      return null;
   }

   public static boolean isRSAOAEP(@Nonnull String keyTransportAlgorithm) {
      return "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(keyTransportAlgorithm) || "http://www.w3.org/2009/xmlenc11#rsa-oaep".equals(keyTransportAlgorithm);
   }

   public static boolean isHMAC(@Nonnull String signatureAlgorithm) {
      AlgorithmRegistry registry = getGlobalAlgorithmRegistry();
      if (registry != null) {
         AlgorithmDescriptor descriptor = registry.get(signatureAlgorithm);
         if (descriptor != null) {
            return descriptor.getType().equals(AlgorithmDescriptor.AlgorithmType.Mac);
         }
      }

      return false;
   }

   @Nullable
   public static String getKeyAlgorithm(@Nonnull String algorithmURI) {
      AlgorithmRegistry registry = getGlobalAlgorithmRegistry();
      if (registry != null) {
         AlgorithmDescriptor descriptor = registry.get(algorithmURI);
         if (descriptor != null && descriptor instanceof KeySpecifiedAlgorithm) {
            return ((KeySpecifiedAlgorithm)descriptor).getKey();
         }
      }

      return null;
   }

   @Nullable
   public static Integer getKeyLength(@Nonnull String algorithmURI) {
      Logger log = getLogger();
      AlgorithmRegistry registry = getGlobalAlgorithmRegistry();
      if (registry != null) {
         AlgorithmDescriptor descriptor = registry.get(algorithmURI);
         if (descriptor != null && descriptor instanceof KeyLengthSpecifiedAlgorithm) {
            return ((KeyLengthSpecifiedAlgorithm)descriptor).getKeyLength();
         }
      }

      log.info("Mapping from algorithm URI {} to key length not available", algorithmURI);
      return null;
   }

   @Nonnull
   public static SecretKey generateSymmetricKey(@Nonnull String algoURI) throws NoSuchAlgorithmException, KeyException {
      Logger log = getLogger();
      String jceAlgorithmName = getKeyAlgorithm(algoURI);
      if (Strings.isNullOrEmpty(jceAlgorithmName)) {
         log.error("Mapping from algorithm URI '" + algoURI + "' to key algorithm not available, key generation failed");
         throw new NoSuchAlgorithmException("Algorithm URI'" + algoURI + "' is invalid for key generation");
      } else {
         Integer keyLength = null;
         switch (algoURI) {
            case "http://www.w3.org/2001/04/xmlenc#tripledes-cbc":
            case "http://www.w3.org/2001/04/xmlenc#kw-tripledes":
               keyLength = 168;
               break;
            default:
               keyLength = getKeyLength(algoURI);
         }

         if (keyLength == null) {
            log.error("Key length could not be determined from algorithm URI, can't generate key");
            throw new KeyException("Key length not determinable from algorithm URI, could not generate new key");
         } else {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(jceAlgorithmName);
            keyGenerator.init(keyLength);
            return keyGenerator.generateKey();
         }
      }
   }

   @Nonnull
   public static KeyPair generateKeyPair(@Nonnull String algoURI, int keyLength) throws NoSuchAlgorithmException, NoSuchProviderException {
      String jceAlgorithmName = getKeyAlgorithm(algoURI);
      return KeySupport.generateKeyPair(jceAlgorithmName, keyLength, (String)null);
   }

   @Nonnull
   public static Credential generateSymmetricKeyAndCredential(@Nonnull String algorithmURI) throws NoSuchAlgorithmException, KeyException {
      SecretKey key = generateSymmetricKey(algorithmURI);
      return new BasicCredential(key);
   }

   @Nonnull
   public static Credential generateKeyPairAndCredential(@Nonnull String algorithmURI, int keyLength, boolean includePrivate) throws NoSuchAlgorithmException, NoSuchProviderException {
      KeyPair keyPair = generateKeyPair(algorithmURI, keyLength);
      BasicCredential credential = new BasicCredential(keyPair.getPublic());
      if (includePrivate) {
         credential.setPrivateKey(keyPair.getPrivate());
      }

      return credential;
   }

   public static boolean validateAlgorithmURI(@Nonnull String algorithmURI, @Nullable Collection whitelistedAlgorithmURIs, @Nullable Collection blacklistedAlgorithmURIs) {
      if (blacklistedAlgorithmURIs != null) {
         LOG.debug("Saw non-null algorithm blacklist: {}", blacklistedAlgorithmURIs);
         if (blacklistedAlgorithmURIs.contains(algorithmURI)) {
            LOG.warn("Algorithm failed blacklist validation: {}", algorithmURI);
            return false;
         }

         LOG.debug("Algorithm passed blacklist validation: {}", algorithmURI);
      } else {
         LOG.debug("Saw null algorithm blacklist, nothing to evaluate");
      }

      if (whitelistedAlgorithmURIs != null) {
         LOG.debug("Saw non-null algorithm whitelist: {}", whitelistedAlgorithmURIs);
         if (!whitelistedAlgorithmURIs.isEmpty()) {
            if (!whitelistedAlgorithmURIs.contains(algorithmURI)) {
               LOG.warn("Algorithm failed whitelist validation: {}", algorithmURI);
               return false;
            }

            LOG.debug("Algorithm passed whitelist validation: {}", algorithmURI);
         } else {
            LOG.debug("Non-null algorithm whitelist was empty, skipping evaluation");
         }
      } else {
         LOG.debug("Saw null algorithm whitelist, nothing to evaluate");
      }

      return true;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(AlgorithmSupport.class);
   }
}
