package org.opensaml;

import java.security.SecureRandom;

public class SAMLIdentifier {
   private static final SecureRandom rand = new SecureRandom();
   protected StringBuffer id = new StringBuffer();

   public SAMLIdentifier() {
      byte[] var1 = new byte[32];

      do {
         rand.nextBytes(var1);
      } while((var1[0] & 15) < 10);

      for(int var2 = 0; var2 < 32; ++var2) {
         this.id.append(Character.forDigit(var1[var2] & 15, 16));
      }

   }

   public String toString() {
      return this.id.toString();
   }
}
