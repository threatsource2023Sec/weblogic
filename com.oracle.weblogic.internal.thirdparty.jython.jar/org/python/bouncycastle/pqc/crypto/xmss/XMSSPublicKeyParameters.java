package org.python.bouncycastle.pqc.crypto.xmss;

import java.text.ParseException;

public final class XMSSPublicKeyParameters implements XMSSStoreableObjectInterface {
   private final XMSSParameters params;
   private final byte[] root;
   private final byte[] publicSeed;

   private XMSSPublicKeyParameters(Builder var1) throws ParseException {
      this.params = var1.params;
      if (this.params == null) {
         throw new NullPointerException("params == null");
      } else {
         int var2 = this.params.getDigestSize();
         byte[] var3 = var1.publicKey;
         if (var3 != null) {
            int var6 = var2 + var2;
            if (var3.length != var6) {
               throw new ParseException("public key has wrong size", 0);
            }

            int var7 = 0;
            this.root = XMSSUtil.extractBytesAtOffset(var3, var7, var2);
            var7 += var2;
            this.publicSeed = XMSSUtil.extractBytesAtOffset(var3, var7, var2);
         } else {
            byte[] var4 = var1.root;
            if (var4 != null) {
               if (var4.length != var2) {
                  throw new IllegalArgumentException("length of root must be equal to length of digest");
               }

               this.root = var4;
            } else {
               this.root = new byte[var2];
            }

            byte[] var5 = var1.publicSeed;
            if (var5 != null) {
               if (var5.length != var2) {
                  throw new IllegalArgumentException("length of publicSeed must be equal to length of digest");
               }

               this.publicSeed = var5;
            } else {
               this.publicSeed = new byte[var2];
            }
         }

      }
   }

   public byte[] toByteArray() {
      int var1 = this.params.getDigestSize();
      int var4 = var1 + var1;
      byte[] var5 = new byte[var4];
      int var6 = 0;
      XMSSUtil.copyBytesAtOffset(var5, this.root, var6);
      var6 += var1;
      XMSSUtil.copyBytesAtOffset(var5, this.publicSeed, var6);
      return var5;
   }

   public byte[] getRoot() {
      return XMSSUtil.cloneArray(this.root);
   }

   public byte[] getPublicSeed() {
      return XMSSUtil.cloneArray(this.publicSeed);
   }

   // $FF: synthetic method
   XMSSPublicKeyParameters(Builder var1, Object var2) throws ParseException {
      this(var1);
   }

   public static class Builder {
      private final XMSSParameters params;
      private byte[] root = null;
      private byte[] publicSeed = null;
      private byte[] publicKey = null;

      public Builder(XMSSParameters var1) {
         this.params = var1;
      }

      public Builder withRoot(byte[] var1) {
         this.root = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withPublicSeed(byte[] var1) {
         this.publicSeed = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withPublicKey(byte[] var1) {
         this.publicKey = XMSSUtil.cloneArray(var1);
         return this;
      }

      public XMSSPublicKeyParameters build() throws ParseException {
         return new XMSSPublicKeyParameters(this);
      }
   }
}
