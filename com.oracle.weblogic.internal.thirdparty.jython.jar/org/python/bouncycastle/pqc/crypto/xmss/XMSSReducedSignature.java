package org.python.bouncycastle.pqc.crypto.xmss;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class XMSSReducedSignature implements XMSSStoreableObjectInterface {
   private final XMSSParameters params;
   private final WOTSPlusSignature wotsPlusSignature;
   private final List authPath;

   protected XMSSReducedSignature(Builder var1) throws ParseException {
      this.params = var1.params;
      if (this.params == null) {
         throw new NullPointerException("params == null");
      } else {
         int var2 = this.params.getDigestSize();
         int var3 = this.params.getWOTSPlus().getParams().getLen();
         int var4 = this.params.getHeight();
         byte[] var5 = var1.reducedSignature;
         if (var5 != null) {
            int var6 = var3 * var2;
            int var7 = var4 * var2;
            int var8 = var6 + var7;
            if (var5.length != var8) {
               throw new ParseException("signature has wrong size", 0);
            }

            int var9 = 0;
            byte[][] var10 = new byte[var3][];

            for(int var11 = 0; var11 < var10.length; ++var11) {
               var10[var11] = XMSSUtil.extractBytesAtOffset(var5, var9, var2);
               var9 += var2;
            }

            this.wotsPlusSignature = new WOTSPlusSignature(this.params.getWOTSPlus().getParams(), var10);
            ArrayList var13 = new ArrayList();

            for(int var12 = 0; var12 < var4; ++var12) {
               var13.add(new XMSSNode(var12, XMSSUtil.extractBytesAtOffset(var5, var9, var2)));
               var9 += var2;
            }

            this.authPath = var13;
         } else {
            WOTSPlusSignature var14 = var1.wotsPlusSignature;
            if (var14 != null) {
               this.wotsPlusSignature = var14;
            } else {
               this.wotsPlusSignature = new WOTSPlusSignature(this.params.getWOTSPlus().getParams(), new byte[var3][var2]);
            }

            List var15 = var1.authPath;
            if (var15 != null) {
               if (var15.size() != var4) {
                  throw new IllegalArgumentException("size of authPath needs to be equal to height of tree");
               }

               this.authPath = var15;
            } else {
               this.authPath = new ArrayList();
            }
         }

      }
   }

   public byte[] toByteArray() {
      int var1 = this.params.getDigestSize();
      int var2 = this.params.getWOTSPlus().getParams().getLen() * var1;
      int var3 = this.params.getHeight() * var1;
      int var4 = var2 + var3;
      byte[] var5 = new byte[var4];
      int var6 = 0;
      byte[][] var7 = this.wotsPlusSignature.toByteArray();

      int var8;
      for(var8 = 0; var8 < var7.length; ++var8) {
         XMSSUtil.copyBytesAtOffset(var5, var7[var8], var6);
         var6 += var1;
      }

      for(var8 = 0; var8 < this.authPath.size(); ++var8) {
         byte[] var9 = ((XMSSNode)this.authPath.get(var8)).getValue();
         XMSSUtil.copyBytesAtOffset(var5, var9, var6);
         var6 += var1;
      }

      return var5;
   }

   public XMSSParameters getParams() {
      return this.params;
   }

   public WOTSPlusSignature getWOTSPlusSignature() {
      return this.wotsPlusSignature;
   }

   public List getAuthPath() {
      return this.authPath;
   }

   public static class Builder {
      private final XMSSParameters params;
      private WOTSPlusSignature wotsPlusSignature = null;
      private List authPath = null;
      private byte[] reducedSignature = null;

      public Builder(XMSSParameters var1) {
         this.params = var1;
      }

      public Builder withWOTSPlusSignature(WOTSPlusSignature var1) {
         this.wotsPlusSignature = var1;
         return this;
      }

      public Builder withAuthPath(List var1) {
         this.authPath = var1;
         return this;
      }

      public Builder withReducedSignature(byte[] var1) {
         this.reducedSignature = XMSSUtil.cloneArray(var1);
         return this;
      }

      public XMSSReducedSignature build() throws ParseException {
         return new XMSSReducedSignature(this);
      }
   }
}
