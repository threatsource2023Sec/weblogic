package org.cryptacular;

import org.cryptacular.codec.Encoder;
import org.cryptacular.util.CodecUtil;

public class SaltedHash {
   private final byte[] hash;
   private final byte[] salt;

   public SaltedHash(byte[] hash, byte[] salt) {
      this.hash = hash;
      this.salt = salt;
   }

   public SaltedHash(byte[] hashWithSalt, int digestLength, boolean toEnd) {
      this.hash = new byte[digestLength];
      this.salt = new byte[hashWithSalt.length - digestLength];
      if (toEnd) {
         System.arraycopy(hashWithSalt, 0, this.hash, 0, this.hash.length);
         System.arraycopy(hashWithSalt, this.hash.length, this.salt, 0, this.salt.length);
      } else {
         System.arraycopy(hashWithSalt, 0, this.salt, 0, this.salt.length);
         System.arraycopy(hashWithSalt, this.salt.length, this.hash, 0, this.hash.length);
      }

   }

   public byte[] getHash() {
      return this.hash;
   }

   public byte[] getSalt() {
      return this.salt;
   }

   public byte[] getSalt(int n) {
      if (n > this.salt.length) {
         throw new IllegalArgumentException("Requested size exceeded length: " + n + ">" + this.salt.length);
      } else {
         byte[] bytes = new byte[n];
         System.arraycopy(this.salt, 0, bytes, 0, n);
         return bytes;
      }
   }

   public String concatenateSalt(boolean toEnd, Encoder encoder) {
      return CodecUtil.encode(encoder, this.concatenateSalt(toEnd));
   }

   public byte[] concatenateSalt(boolean toEnd) {
      byte[] output = new byte[this.hash.length + this.salt.length];
      if (toEnd) {
         System.arraycopy(this.hash, 0, output, 0, this.hash.length);
         System.arraycopy(this.salt, 0, output, this.hash.length, this.salt.length);
      } else {
         System.arraycopy(this.salt, 0, output, 0, this.salt.length);
         System.arraycopy(this.hash, 0, output, this.salt.length, this.hash.length);
      }

      return output;
   }
}
