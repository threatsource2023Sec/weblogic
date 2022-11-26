package org.python.bouncycastle.pqc.crypto.xmss;

import org.python.bouncycastle.crypto.Digest;

public final class WOTSPlusParameters {
   private final XMSSOid oid;
   private final Digest digest;
   private final int digestSize;
   private final int winternitzParameter;
   private final int len;
   private final int len1;
   private final int len2;

   protected WOTSPlusParameters(Digest var1) {
      if (var1 == null) {
         throw new NullPointerException("digest == null");
      } else {
         this.digest = var1;
         this.digestSize = XMSSUtil.getDigestSize(var1);
         this.winternitzParameter = 16;
         this.len1 = (int)Math.ceil((double)(8 * this.digestSize) / (double)XMSSUtil.log2(this.winternitzParameter));
         this.len2 = (int)Math.floor((double)(XMSSUtil.log2(this.len1 * (this.winternitzParameter - 1)) / XMSSUtil.log2(this.winternitzParameter))) + 1;
         this.len = this.len1 + this.len2;
         this.oid = WOTSPlusOid.lookup(var1.getAlgorithmName(), this.digestSize, this.winternitzParameter, this.len);
         if (this.oid == null) {
            throw new IllegalArgumentException("cannot find OID for digest algorithm: " + var1.getAlgorithmName());
         }
      }
   }

   protected XMSSOid getOid() {
      return this.oid;
   }

   protected Digest getDigest() {
      return this.digest;
   }

   protected int getDigestSize() {
      return this.digestSize;
   }

   protected int getWinternitzParameter() {
      return this.winternitzParameter;
   }

   protected int getLen() {
      return this.len;
   }

   protected int getLen1() {
      return this.len1;
   }

   protected int getLen2() {
      return this.len2;
   }
}
