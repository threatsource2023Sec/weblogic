package org.python.bouncycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.Digest;

public final class XMSSParameters {
   private final XMSSOid oid;
   private final WOTSPlus wotsPlus;
   private final SecureRandom prng;
   private final int height;
   private final int k;

   public XMSSParameters(int var1, Digest var2, SecureRandom var3) {
      if (var1 < 2) {
         throw new IllegalArgumentException("height must be >= 2");
      } else if (var2 == null) {
         throw new NullPointerException("digest == null");
      } else if (var3 == null) {
         throw new NullPointerException("prng == null");
      } else {
         this.wotsPlus = new WOTSPlus(new WOTSPlusParameters(var2));
         this.prng = var3;
         this.height = var1;
         this.k = this.determineMinK();
         this.oid = DefaultXMSSOid.lookup(this.getDigest().getAlgorithmName(), this.getDigestSize(), this.getWinternitzParameter(), this.wotsPlus.getParams().getLen(), var1);
      }
   }

   private int determineMinK() {
      for(int var1 = 2; var1 <= this.height; ++var1) {
         if ((this.height - var1) % 2 == 0) {
            return var1;
         }
      }

      throw new IllegalStateException("should never happen...");
   }

   protected Digest getDigest() {
      return this.wotsPlus.getParams().getDigest();
   }

   protected SecureRandom getPRNG() {
      return this.prng;
   }

   public int getDigestSize() {
      return this.wotsPlus.getParams().getDigestSize();
   }

   public int getWinternitzParameter() {
      return this.wotsPlus.getParams().getWinternitzParameter();
   }

   public int getHeight() {
      return this.height;
   }

   protected WOTSPlus getWOTSPlus() {
      return this.wotsPlus;
   }

   protected int getK() {
      return this.k;
   }
}
