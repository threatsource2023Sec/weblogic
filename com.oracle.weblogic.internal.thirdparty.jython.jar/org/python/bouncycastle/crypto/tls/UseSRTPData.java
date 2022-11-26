package org.python.bouncycastle.crypto.tls;

public class UseSRTPData {
   protected int[] protectionProfiles;
   protected byte[] mki;

   public UseSRTPData(int[] var1, byte[] var2) {
      if (var1 != null && var1.length >= 1 && var1.length < 32768) {
         if (var2 == null) {
            var2 = TlsUtils.EMPTY_BYTES;
         } else if (var2.length > 255) {
            throw new IllegalArgumentException("'mki' cannot be longer than 255 bytes");
         }

         this.protectionProfiles = var1;
         this.mki = var2;
      } else {
         throw new IllegalArgumentException("'protectionProfiles' must have length from 1 to (2^15 - 1)");
      }
   }

   public int[] getProtectionProfiles() {
      return this.protectionProfiles;
   }

   public byte[] getMki() {
      return this.mki;
   }
}
