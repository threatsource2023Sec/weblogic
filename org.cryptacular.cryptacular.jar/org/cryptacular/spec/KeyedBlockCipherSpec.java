package org.cryptacular.spec;

public class KeyedBlockCipherSpec extends BufferedBlockCipherSpec {
   private final int keyLength;

   public KeyedBlockCipherSpec(String algName, String cipherMode, String cipherPadding, int keyBitLength) {
      super(algName, cipherMode, cipherPadding);
      if (keyBitLength < 0) {
         throw new IllegalArgumentException("Key length must be non-negative");
      } else {
         this.keyLength = keyBitLength;
      }
   }

   public int getKeyLength() {
      return this.keyLength;
   }
}
