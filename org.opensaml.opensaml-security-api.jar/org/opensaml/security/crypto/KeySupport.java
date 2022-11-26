package org.opensaml.security.crypto;

import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.cryptacular.util.KeyPairUtil;
import org.opensaml.security.SecurityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class KeySupport {
   private static Map keyMatchAlgorithms = new LazyMap();

   private KeySupport() {
   }

   @Nullable
   public static Integer getKeyLength(@Nonnull Key key) {
      Logger log = getLogger();
      log.debug("Attempting to determine length of Key with algorithm '{}' and encoding format '{}'", key.getAlgorithm(), key.getFormat());
      if (key instanceof SecretKey && "RAW".equals(key.getFormat())) {
         return key.getEncoded().length * 8;
      } else if (key instanceof RSAKey) {
         return ((RSAKey)key).getModulus().bitLength();
      } else if (key instanceof DSAKey) {
         return ((DSAKey)key).getParams().getP().bitLength();
      } else if (key instanceof ECKey) {
         return ((ECKey)key).getParams().getCurve().getField().getFieldSize();
      } else {
         log.debug("Unable to determine length in bits of specified Key instance");
         return null;
      }
   }

   @Nonnull
   public static SecretKey decodeSecretKey(@Nonnull byte[] key, @Nonnull String algorithm) throws KeyException {
      Logger log = getLogger();
      Constraint.isNotNull(key, "Secret key bytes can not be null");
      Constraint.isNotNull(algorithm, "Secret key algorithm can not be null");
      Constraint.isGreaterThanOrEqual(1L, (long)key.length, "Secret key bytes can not be empty");
      int keyLengthBits = key.length * 8;
      switch (algorithm) {
         case "AES":
            if (keyLengthBits != 128 && keyLengthBits != 192 && keyLengthBits != 256) {
               throw new KeyException(String.format("Saw invalid key length %d for algorithm %s", keyLengthBits, "AES"));
            }
            break;
         case "DES":
            if (keyLengthBits != 64) {
               throw new KeyException(String.format("Saw invalid key length %d for algorithm %s", keyLengthBits, "DES"));
            }
            break;
         case "DESede":
            if (keyLengthBits != 192 && keyLengthBits != 168) {
               throw new KeyException(String.format("Saw invalid key length %d for algorithm %s", keyLengthBits, "DESede"));
            }
            break;
         default:
            log.debug("No length and sanity checking done for key with algorithm: {}", algorithm);
      }

      return new SecretKeySpec(key, algorithm);
   }

   @Nonnull
   public static PublicKey decodePublicKey(@Nonnull byte[] key) throws KeyException {
      Constraint.isNotNull(key, "Encoded key bytes cannot be null");
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);

      try {
         return buildKey(keySpec, "RSA");
      } catch (KeyException var5) {
         try {
            return buildKey(keySpec, "DSA");
         } catch (KeyException var4) {
            try {
               return buildKey(keySpec, "EC");
            } catch (KeyException var3) {
               throw new KeyException("Unsupported key type.");
            }
         }
      }
   }

   @Nonnull
   public static PrivateKey decodePrivateKey(@Nonnull File key, @Nullable char[] password) throws KeyException {
      Constraint.isNotNull(key, "Key file cannot be null");
      if (!key.exists()) {
         throw new KeyException("Key file " + key.getAbsolutePath() + " does not exist");
      } else if (!key.canRead()) {
         throw new KeyException("Key file " + key.getAbsolutePath() + " is not readable");
      } else {
         try {
            return decodePrivateKey(Files.toByteArray(key), password);
         } catch (IOException var3) {
            throw new KeyException("Error reading Key file " + key.getAbsolutePath(), var3);
         }
      }
   }

   @Nonnull
   public static PrivateKey decodePrivateKey(@Nonnull InputStream key, @Nullable char[] password) throws KeyException {
      Constraint.isNotNull(key, "Key stream cannot be null");

      try {
         return decodePrivateKey(ByteStreams.toByteArray(key), password);
      } catch (IOException var3) {
         throw new KeyException("Error reading Key file ", var3);
      }
   }

   @Nonnull
   public static PrivateKey decodePrivateKey(@Nonnull byte[] key, @Nullable char[] password) throws KeyException {
      Constraint.isNotNull(key, "Encoded key bytes cannot be null");
      return password != null && password.length > 0 ? KeyPairUtil.decodePrivateKey(key, password) : KeyPairUtil.decodePrivateKey(key);
   }

   @Nonnull
   public static PublicKey derivePublicKey(@Nonnull PrivateKey key) throws KeyException {
      KeyFactory factory;
      if (key instanceof DSAPrivateKey) {
         DSAPrivateKey dsaKey = (DSAPrivateKey)key;
         DSAParams keyParams = dsaKey.getParams();
         BigInteger y = keyParams.getG().modPow(dsaKey.getX(), keyParams.getP());
         DSAPublicKeySpec pubKeySpec = new DSAPublicKeySpec(y, keyParams.getP(), keyParams.getQ(), keyParams.getG());

         try {
            factory = KeyFactory.getInstance("DSA");
            return factory.generatePublic(pubKeySpec);
         } catch (GeneralSecurityException var7) {
            throw new KeyException("Unable to derive public key from DSA private key", var7);
         }
      } else if (key instanceof RSAPrivateCrtKey) {
         RSAPrivateCrtKey rsaKey = (RSAPrivateCrtKey)key;
         RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(rsaKey.getModulus(), rsaKey.getPublicExponent());

         try {
            factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(pubKeySpec);
         } catch (GeneralSecurityException var8) {
            throw new KeyException("Unable to derive public key from RSA private key", var8);
         }
      } else {
         throw new KeyException("Private key was not a DSA or RSA key");
      }
   }

   @Nonnull
   public static DSAPublicKey buildJavaDSAPublicKey(@Nonnull String base64EncodedKey) throws KeyException {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Support.decode(base64EncodedKey));
      return (DSAPublicKey)buildKey(keySpec, "DSA");
   }

   @Nonnull
   public static RSAPublicKey buildJavaRSAPublicKey(@Nonnull String base64EncodedKey) throws KeyException {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Support.decode(base64EncodedKey));
      return (RSAPublicKey)buildKey(keySpec, "RSA");
   }

   @Nonnull
   public static ECPublicKey buildJavaECPublicKey(@Nonnull String base64EncodedKey) throws KeyException {
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64Support.decode(base64EncodedKey));
      return (ECPublicKey)buildKey(keySpec, "EC");
   }

   @Nonnull
   public static RSAPrivateKey buildJavaRSAPrivateKey(@Nonnull String base64EncodedKey) throws KeyException {
      PrivateKey key = buildJavaPrivateKey(base64EncodedKey);
      if (!(key instanceof RSAPrivateKey)) {
         throw new KeyException("Generated key was not an RSAPrivateKey instance");
      } else {
         return (RSAPrivateKey)key;
      }
   }

   @Nonnull
   public static DSAPrivateKey buildJavaDSAPrivateKey(@Nonnull String base64EncodedKey) throws KeyException {
      PrivateKey key = buildJavaPrivateKey(base64EncodedKey);
      if (!(key instanceof DSAPrivateKey)) {
         throw new KeyException("Generated key was not a DSAPrivateKey instance");
      } else {
         return (DSAPrivateKey)key;
      }
   }

   public static ECPrivateKey buildJavaECPrivateKey(String base64EncodedKey) throws KeyException {
      PrivateKey key = buildJavaPrivateKey(base64EncodedKey);
      if (!(key instanceof ECPrivateKey)) {
         throw new KeyException("Generated key was not an ECPrivateKey instance");
      } else {
         return (ECPrivateKey)key;
      }
   }

   @Nonnull
   public static PrivateKey buildJavaPrivateKey(@Nonnull String base64EncodedKey) throws KeyException {
      return decodePrivateKey((byte[])Base64Support.decode(base64EncodedKey), (char[])null);
   }

   @Nonnull
   public static PublicKey buildKey(@Nullable KeySpec keySpec, @Nonnull String keyAlgorithm) throws KeyException {
      Constraint.isNotNull(keyAlgorithm, "Key algorithm cannot be null");

      try {
         KeyFactory keyFactory = KeyFactory.getInstance(keyAlgorithm);
         return keyFactory.generatePublic(keySpec);
      } catch (NoSuchAlgorithmException var3) {
         throw new KeyException(keyAlgorithm + "algorithm is not supported by the JCA", var3);
      } catch (InvalidKeySpecException var4) {
         throw new KeyException("Invalid key information", var4);
      }
   }

   @Nonnull
   public static SecretKey generateKey(@Nonnull String algo, int keyLength, @Nullable String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
      Constraint.isNotNull(algo, "Key algorithm cannot be null");
      KeyGenerator keyGenerator = null;
      if (provider != null) {
         keyGenerator = KeyGenerator.getInstance(algo, provider);
      } else {
         keyGenerator = KeyGenerator.getInstance(algo);
      }

      keyGenerator.init(keyLength);
      return keyGenerator.generateKey();
   }

   @Nonnull
   public static KeyPair generateKeyPair(@Nonnull String algo, int keyLength, @Nullable String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
      Constraint.isNotNull(algo, "Key algorithm cannot be null");
      KeyPairGenerator keyGenerator = null;
      if (provider != null) {
         keyGenerator = KeyPairGenerator.getInstance(algo, provider);
      } else {
         keyGenerator = KeyPairGenerator.getInstance(algo);
      }

      keyGenerator.initialize(keyLength);
      return keyGenerator.generateKeyPair();
   }

   public static boolean matchKeyPair(@Nonnull PublicKey pubKey, @Nonnull PrivateKey privKey) throws SecurityException {
      if (pubKey != null && privKey != null) {
         String jcaAlgoID = (String)keyMatchAlgorithms.get(privKey.getAlgorithm());
         if (jcaAlgoID == null) {
            throw new SecurityException("Can't determine JCA algorithm ID for key matching from key algorithm: " + privKey.getAlgorithm());
         } else {
            Logger log = getLogger();
            if (log.isDebugEnabled()) {
               log.debug("Attempting to match key pair containing key algorithms public '{}' private '{}', using JCA signature algorithm '{}'", new Object[]{pubKey.getAlgorithm(), privKey.getAlgorithm(), jcaAlgoID});
            }

            byte[] data = "This is the data to sign".getBytes();
            byte[] signature = SigningUtil.sign(privKey, jcaAlgoID, data);
            return SigningUtil.verify(pubKey, jcaAlgoID, signature, data);
         }
      } else {
         throw new SecurityException("Either public or private key was null");
      }
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(KeySupport.class);
   }

   static {
      keyMatchAlgorithms.put("RSA", "SHA1withRSA");
      keyMatchAlgorithms.put("DSA", "SHA1withDSA");
      keyMatchAlgorithms.put("EC", "SHA1withECDSA");
   }
}
