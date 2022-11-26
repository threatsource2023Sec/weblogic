package org.python.bouncycastle.pqc.crypto.xmss;

import java.security.SecureRandom;

public final class NullPRNG extends SecureRandom {
   private static final long serialVersionUID = 1L;

   public void nextBytes(byte[] var1) {
      for(int var2 = 0; var2 < var1.length; ++var2) {
         var1[var2] = 0;
      }

   }
}
