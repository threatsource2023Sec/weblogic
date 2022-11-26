package org.glassfish.soteria.identitystores.hash;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.Dependent;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@Dependent
public class Pbkdf2PasswordHashImpl implements Pbkdf2PasswordHash {
   private static final Set SUPPORTED_ALGORITHMS = Collections.unmodifiableSet(new HashSet(Arrays.asList("PBKDF2WithHmacSHA224", "PBKDF2WithHmacSHA256", "PBKDF2WithHmacSHA384", "PBKDF2WithHmacSHA512")));
   private static final String DEFAULT_ALGORITHM = "PBKDF2WithHmacSHA256";
   private static final int DEFAULT_ITERATIONS = 2048;
   private static final int DEFAULT_SALT_SIZE = 32;
   private static final int DEFAULT_KEY_SIZE = 32;
   private static final int MIN_ITERATIONS = 1024;
   private static final int MIN_SALT_SIZE = 16;
   private static final int MIN_KEY_SIZE = 16;
   private static final String PROPERTY_ALGORITHM = "Pbkdf2PasswordHash.Algorithm";
   private static final String PROPERTY_ITERATIONS = "Pbkdf2PasswordHash.Iterations";
   private static final String PROPERTY_SALTSIZE = "Pbkdf2PasswordHash.SaltSizeBytes";
   private static final String PROPERTY_KEYSIZE = "Pbkdf2PasswordHash.KeySizeBytes";
   private String configuredAlgorithm = "PBKDF2WithHmacSHA256";
   private int configuredIterations = 2048;
   private int configuredSaltSizeBytes = 32;
   private int configuredKeySizeBytes = 32;
   private final SecureRandom random = new SecureRandom();

   public void initialize(Map parameters) {
      Iterator var2 = parameters.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         if (((String)entry.getKey()).equals("Pbkdf2PasswordHash.Algorithm")) {
            if (!SUPPORTED_ALGORITHMS.contains(entry.getValue())) {
               throw new IllegalArgumentException("Bad Algorithm parameter: " + (String)entry.getValue());
            }

            this.configuredAlgorithm = (String)entry.getValue();
         } else if (((String)entry.getKey()).equals("Pbkdf2PasswordHash.Iterations")) {
            try {
               this.configuredIterations = Integer.parseInt((String)entry.getValue());
            } catch (Exception var6) {
               throw new IllegalArgumentException("Bad Iterations parameter: " + (String)entry.getValue());
            }

            if (this.configuredIterations < 1024) {
               throw new IllegalArgumentException("Bad Iterations parameter: " + (String)entry.getValue());
            }
         } else if (((String)entry.getKey()).equals("Pbkdf2PasswordHash.SaltSizeBytes")) {
            try {
               this.configuredSaltSizeBytes = Integer.parseInt((String)entry.getValue());
            } catch (Exception var7) {
               throw new IllegalArgumentException("Bad SaltSizeBytes parameter: " + (String)entry.getValue());
            }

            if (this.configuredSaltSizeBytes < 16) {
               throw new IllegalArgumentException("Bad SaltSizeBytes parameter: " + (String)entry.getValue());
            }
         } else {
            if (!((String)entry.getKey()).equals("Pbkdf2PasswordHash.KeySizeBytes")) {
               throw new IllegalArgumentException("Unrecognized parameter for Pbkdf2PasswordHash");
            }

            try {
               this.configuredKeySizeBytes = Integer.parseInt((String)entry.getValue());
            } catch (Exception var5) {
               throw new IllegalArgumentException("Bad KeySizeBytes parameter: " + (String)entry.getValue());
            }

            if (this.configuredKeySizeBytes < 16) {
               throw new IllegalArgumentException("Bad KeySizeBytes parameter: " + (String)entry.getValue());
            }
         }
      }

   }

   public String generate(char[] password) {
      byte[] salt = this.getRandomSalt(new byte[this.configuredSaltSizeBytes]);
      byte[] hash = this.pbkdf2(password, salt, this.configuredAlgorithm, this.configuredIterations, this.configuredKeySizeBytes);
      return (new EncodedPasswordHash(hash, salt, this.configuredAlgorithm, this.configuredIterations)).getEncoded();
   }

   public boolean verify(char[] password, String hashedPassword) {
      EncodedPasswordHash encodedPasswordHash = new EncodedPasswordHash(hashedPassword);
      byte[] hashToVerify = this.pbkdf2(password, encodedPasswordHash.getSalt(), encodedPasswordHash.getAlgorithm(), encodedPasswordHash.getIterations(), encodedPasswordHash.getHash().length);
      return PasswordHashCompare.compareBytes(hashToVerify, encodedPasswordHash.getHash());
   }

   private byte[] pbkdf2(char[] password, byte[] salt, String algorithm, int iterations, int keySizeBytes) {
      try {
         return SecretKeyFactory.getInstance(algorithm).generateSecret(new PBEKeySpec(password, salt, iterations, keySizeBytes * 8)).getEncoded();
      } catch (InvalidKeySpecException | NoSuchAlgorithmException var7) {
         throw new IllegalStateException(var7);
      }
   }

   private synchronized byte[] getRandomSalt(byte[] salt) {
      this.random.nextBytes(salt);
      return salt;
   }

   private static class EncodedPasswordHash {
      private String algorithm;
      private int iterations;
      private byte[] salt;
      private byte[] hash;
      private String encoded;

      private EncodedPasswordHash() {
      }

      EncodedPasswordHash(byte[] hash, byte[] salt, String algorithm, int iterations) {
         this.algorithm = algorithm;
         this.iterations = iterations;
         this.salt = salt;
         this.hash = hash;
         this.encode();
      }

      EncodedPasswordHash(String encoded) {
         this.encoded = encoded;
         this.decode();
      }

      String getAlgorithm() {
         return this.algorithm;
      }

      int getIterations() {
         return this.iterations;
      }

      byte[] getSalt() {
         return this.salt;
      }

      byte[] getHash() {
         return this.hash;
      }

      String getEncoded() {
         return this.encoded;
      }

      private void encode() {
         this.encoded = this.algorithm + ":" + this.iterations + ":" + Base64.getEncoder().encodeToString(this.salt) + ":" + Base64.getEncoder().encodeToString(this.hash);
      }

      private void decode() {
         String[] tokens = this.encoded.split(":");
         if (tokens.length != 4) {
            throw new IllegalArgumentException("Bad hash encoding");
         } else if (!Pbkdf2PasswordHashImpl.SUPPORTED_ALGORITHMS.contains(tokens[0])) {
            throw new IllegalArgumentException("Bad hash encoding");
         } else {
            this.algorithm = tokens[0];

            try {
               this.iterations = Integer.parseInt(tokens[1]);
               this.salt = Base64.getDecoder().decode(tokens[2]);
               this.hash = Base64.getDecoder().decode(tokens[3]);
            } catch (Exception var3) {
               throw new IllegalArgumentException("Bad hash encoding", var3);
            }
         }
      }
   }
}
