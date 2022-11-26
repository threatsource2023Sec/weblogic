package org.glassfish.tyrus.core;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

class SecKey {
   private static final Random random = new SecureRandom();
   private static final int KEY_SIZE = 16;
   private final String secKey;

   public SecKey() {
      this.secKey = this.create();
   }

   private String create() {
      byte[] bytes = new byte[16];
      random.nextBytes(bytes);
      return Base64.getEncoder().encodeToString(bytes);
   }

   public SecKey(String base64) throws HandshakeException {
      if (base64 == null) {
         throw new HandshakeException(LocalizationMessages.SEC_KEY_NULL_NOT_ALLOWED());
      } else {
         this.secKey = base64;
      }
   }

   public static SecKey generateServerKey(SecKey clientKey) throws HandshakeException {
      String key = clientKey.getSecKey() + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";

      try {
         MessageDigest instance = MessageDigest.getInstance("SHA-1");
         instance.update(key.getBytes("UTF-8"));
         byte[] digest = instance.digest();
         if (digest.length != 20) {
            throw new HandshakeException(LocalizationMessages.SEC_KEY_INVALID_LENGTH(digest.length));
         } else {
            return new SecKey(Base64.getEncoder().encodeToString(digest));
         }
      } catch (UnsupportedEncodingException | NoSuchAlgorithmException var4) {
         throw new HandshakeException(var4.getMessage());
      }
   }

   public String getSecKey() {
      return this.secKey;
   }

   public String toString() {
      return this.secKey;
   }

   public void validateServerKey(String serverKey) throws HandshakeException {
      SecKey key = generateServerKey(this);
      if (!key.getSecKey().equals(serverKey)) {
         throw new HandshakeException(LocalizationMessages.SEC_KEY_INVALID_SERVER());
      }
   }
}
