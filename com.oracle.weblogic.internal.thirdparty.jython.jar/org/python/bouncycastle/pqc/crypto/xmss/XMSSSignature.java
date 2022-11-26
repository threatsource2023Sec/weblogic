package org.python.bouncycastle.pqc.crypto.xmss;

import java.text.ParseException;
import org.python.bouncycastle.util.Pack;

public final class XMSSSignature extends XMSSReducedSignature implements XMSSStoreableObjectInterface {
   private final int index;
   private final byte[] random;

   private XMSSSignature(Builder var1) throws ParseException {
      super(var1);
      this.index = var1.index;
      int var2 = this.getParams().getDigestSize();
      byte[] var3 = var1.random;
      if (var3 != null) {
         if (var3.length != var2) {
            throw new IllegalArgumentException("size of random needs to be equal to size of digest");
         }

         this.random = var3;
      } else {
         this.random = new byte[var2];
      }

   }

   public byte[] toByteArray() {
      int var1 = this.getParams().getDigestSize();
      byte var2 = 4;
      int var4 = this.getParams().getWOTSPlus().getParams().getLen() * var1;
      int var5 = this.getParams().getHeight() * var1;
      int var6 = var2 + var1 + var4 + var5;
      byte[] var7 = new byte[var6];
      int var8 = 0;
      XMSSUtil.intToBytesBigEndianOffset(var7, this.index, var8);
      var8 += var2;
      XMSSUtil.copyBytesAtOffset(var7, this.random, var8);
      var8 += var1;
      byte[][] var9 = this.getWOTSPlusSignature().toByteArray();

      int var10;
      for(var10 = 0; var10 < var9.length; ++var10) {
         XMSSUtil.copyBytesAtOffset(var7, var9[var10], var8);
         var8 += var1;
      }

      for(var10 = 0; var10 < this.getAuthPath().size(); ++var10) {
         byte[] var11 = ((XMSSNode)this.getAuthPath().get(var10)).getValue();
         XMSSUtil.copyBytesAtOffset(var7, var11, var8);
         var8 += var1;
      }

      return var7;
   }

   public int getIndex() {
      return this.index;
   }

   public byte[] getRandom() {
      return XMSSUtil.cloneArray(this.random);
   }

   // $FF: synthetic method
   XMSSSignature(Builder var1, Object var2) throws ParseException {
      this(var1);
   }

   public static class Builder extends XMSSReducedSignature.Builder {
      private final XMSSParameters params;
      private int index = 0;
      private byte[] random = null;

      public Builder(XMSSParameters var1) {
         super(var1);
         this.params = var1;
      }

      public Builder withIndex(int var1) {
         this.index = var1;
         return this;
      }

      public Builder withRandom(byte[] var1) {
         this.random = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withSignature(byte[] var1) {
         if (var1 == null) {
            throw new NullPointerException("signature == null");
         } else {
            int var2 = this.params.getDigestSize();
            int var3 = this.params.getWOTSPlus().getParams().getLen();
            int var4 = this.params.getHeight();
            byte var5 = 4;
            int var7 = var3 * var2;
            int var8 = var4 * var2;
            int var9 = 0;
            this.index = Pack.bigEndianToInt(var1, var9);
            var9 += var5;
            this.random = XMSSUtil.extractBytesAtOffset(var1, var9, var2);
            var9 += var2;
            this.withReducedSignature(XMSSUtil.extractBytesAtOffset(var1, var9, var7 + var8));
            return this;
         }
      }

      public XMSSSignature build() throws ParseException {
         return new XMSSSignature(this);
      }
   }
}
