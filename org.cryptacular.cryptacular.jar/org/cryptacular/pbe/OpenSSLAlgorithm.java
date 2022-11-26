package org.cryptacular.pbe;

import org.cryptacular.spec.KeyedBlockCipherSpec;

public enum OpenSSLAlgorithm {
   AES_128_CBC("aes-128-cbc", new KeyedBlockCipherSpec("AES", "CBC", "PKCS5", 128)),
   AES_192_CBC("aes-192-cbc", new KeyedBlockCipherSpec("AES", "CBC", "PKCS5", 192)),
   AES_256_CBC("aes-256-cbc", new KeyedBlockCipherSpec("AES", "CBC", "PKCS5", 256)),
   DES_CBC("des-cbc", new KeyedBlockCipherSpec("DES", "CBC", "PKCS5", 64)),
   DES_EDE3_CBC("des-ede3-cbc", new KeyedBlockCipherSpec("DESede", "CBC", "PKCS5", 192)),
   RC2_CBC("rc2-cbc", new KeyedBlockCipherSpec("RC2", "CBC", "PKCS5", 128)),
   RC2_40_CBC("rc2-40-cbc", new KeyedBlockCipherSpec("RC2", "CBC", "PKCS5", 40)),
   RC2_64_CBC("rc2-64-cbc", new KeyedBlockCipherSpec("RC2", "CBC", "PKCS5", 64));

   private final String algorithmId;
   private final KeyedBlockCipherSpec cipherSpec;

   private OpenSSLAlgorithm(String algId, KeyedBlockCipherSpec cipherSpec) {
      this.algorithmId = algId;
      this.cipherSpec = cipherSpec;
   }

   public String getAlgorithmId() {
      return this.algorithmId;
   }

   public KeyedBlockCipherSpec getCipherSpec() {
      return this.cipherSpec;
   }

   public static OpenSSLAlgorithm fromAlgorithmId(String algorithmId) {
      OpenSSLAlgorithm[] var1 = values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         OpenSSLAlgorithm alg = var1[var3];
         if (alg.getAlgorithmId().equalsIgnoreCase(algorithmId)) {
            return alg;
         }
      }

      throw new IllegalArgumentException("Unsupported algorithm " + algorithmId);
   }
}
