package org.python.bouncycastle.pqc.crypto.xmss;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.Digest;

public final class XMSSMTParameters {
   private final XMSSOid oid;
   private final XMSS xmss;
   private final int height;
   private final int layers;

   public XMSSMTParameters(int var1, int var2, Digest var3, SecureRandom var4) {
      this.height = var1;
      this.layers = var2;
      this.xmss = new XMSS(new XMSSParameters(xmssTreeHeight(var1, var2), var3, var4));
      this.oid = DefaultXMSSMTOid.lookup(this.getDigest().getAlgorithmName(), this.getDigestSize(), this.getWinternitzParameter(), this.getLen(), this.getHeight(), var2);
   }

   private static int xmssTreeHeight(int var0, int var1) throws IllegalArgumentException {
      if (var0 < 2) {
         throw new IllegalArgumentException("totalHeight must be > 1");
      } else if (var0 % var1 != 0) {
         throw new IllegalArgumentException("layers must divide totalHeight without remainder");
      } else if (var0 / var1 == 1) {
         throw new IllegalArgumentException("height / layers must be greater than 1");
      } else {
         return var0 / var1;
      }
   }

   public int getHeight() {
      return this.height;
   }

   public int getLayers() {
      return this.layers;
   }

   protected XMSS getXMSS() {
      return this.xmss;
   }

   protected WOTSPlus getWOTSPlus() {
      return this.xmss.getWOTSPlus();
   }

   protected Digest getDigest() {
      return this.xmss.getParams().getDigest();
   }

   public int getDigestSize() {
      return this.xmss.getParams().getDigestSize();
   }

   public int getWinternitzParameter() {
      return this.xmss.getParams().getWinternitzParameter();
   }

   protected int getLen() {
      return this.xmss.getWOTSPlus().getParams().getLen();
   }
}
