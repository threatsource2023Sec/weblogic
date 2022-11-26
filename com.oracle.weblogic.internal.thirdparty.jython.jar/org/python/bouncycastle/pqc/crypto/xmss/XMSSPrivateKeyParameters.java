package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import java.text.ParseException;
import org.python.bouncycastle.util.Pack;

public final class XMSSPrivateKeyParameters implements XMSSStoreableObjectInterface {
   private final XMSSParameters params;
   private final int index;
   private final byte[] secretKeySeed;
   private final byte[] secretKeyPRF;
   private final byte[] publicSeed;
   private final byte[] root;
   private final BDS bdsState;

   private XMSSPrivateKeyParameters(Builder var1) throws ParseException, ClassNotFoundException, IOException {
      this.params = var1.params;
      if (this.params == null) {
         throw new NullPointerException("params == null");
      } else {
         int var2 = this.params.getDigestSize();
         byte[] var3 = var1.privateKey;
         if (var3 != null) {
            if (var1.xmss == null) {
               throw new NullPointerException("xmss == null");
            }

            int var4 = this.params.getHeight();
            byte var5 = 4;
            int var10 = 0;
            this.index = Pack.bigEndianToInt(var3, var10);
            if (!XMSSUtil.isIndexValid(var4, (long)this.index)) {
               throw new ParseException("index out of bounds", 0);
            }

            var10 += var5;
            this.secretKeySeed = XMSSUtil.extractBytesAtOffset(var3, var10, var2);
            var10 += var2;
            this.secretKeyPRF = XMSSUtil.extractBytesAtOffset(var3, var10, var2);
            var10 += var2;
            this.publicSeed = XMSSUtil.extractBytesAtOffset(var3, var10, var2);
            var10 += var2;
            this.root = XMSSUtil.extractBytesAtOffset(var3, var10, var2);
            var10 += var2;
            byte[] var11 = XMSSUtil.extractBytesAtOffset(var3, var10, var3.length - var10);
            BDS var12 = (BDS)XMSSUtil.deserialize(var11);
            var12.setXMSS(var1.xmss);
            var12.validate();
            this.bdsState = var12;
         } else {
            this.index = var1.index;
            byte[] var13 = var1.secretKeySeed;
            if (var13 != null) {
               if (var13.length != var2) {
                  throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
               }

               this.secretKeySeed = var13;
            } else {
               this.secretKeySeed = new byte[var2];
            }

            byte[] var14 = var1.secretKeyPRF;
            if (var14 != null) {
               if (var14.length != var2) {
                  throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
               }

               this.secretKeyPRF = var14;
            } else {
               this.secretKeyPRF = new byte[var2];
            }

            byte[] var6 = var1.publicSeed;
            if (var6 != null) {
               if (var6.length != var2) {
                  throw new IllegalArgumentException("size of publicSeed needs to be equal size of digest");
               }

               this.publicSeed = var6;
            } else {
               this.publicSeed = new byte[var2];
            }

            byte[] var7 = var1.root;
            if (var7 != null) {
               if (var7.length != var2) {
                  throw new IllegalArgumentException("size of root needs to be equal size of digest");
               }

               this.root = var7;
            } else {
               this.root = new byte[var2];
            }

            BDS var8 = var1.bdsState;
            if (var8 != null) {
               this.bdsState = var8;
            } else {
               this.bdsState = new BDS(new XMSS(this.params));
            }
         }

      }
   }

   public byte[] toByteArray() {
      int var1 = this.params.getDigestSize();
      byte var2 = 4;
      int var7 = var2 + var1 + var1 + var1 + var1;
      byte[] var8 = new byte[var7];
      int var9 = 0;
      XMSSUtil.intToBytesBigEndianOffset(var8, this.index, var9);
      var9 += var2;
      XMSSUtil.copyBytesAtOffset(var8, this.secretKeySeed, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.secretKeyPRF, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.publicSeed, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.root, var9);
      Object var10 = null;

      byte[] var13;
      try {
         var13 = XMSSUtil.serialize(this.bdsState);
      } catch (IOException var12) {
         var12.printStackTrace();
         throw new RuntimeException("error serializing bds state");
      }

      return XMSSUtil.concat(var8, var13);
   }

   public int getIndex() {
      return this.index;
   }

   public byte[] getSecretKeySeed() {
      return XMSSUtil.cloneArray(this.secretKeySeed);
   }

   public byte[] getSecretKeyPRF() {
      return XMSSUtil.cloneArray(this.secretKeyPRF);
   }

   public byte[] getPublicSeed() {
      return XMSSUtil.cloneArray(this.publicSeed);
   }

   public byte[] getRoot() {
      return XMSSUtil.cloneArray(this.root);
   }

   public BDS getBDSState() {
      return this.bdsState;
   }

   // $FF: synthetic method
   XMSSPrivateKeyParameters(Builder var1, Object var2) throws ParseException, ClassNotFoundException, IOException {
      this(var1);
   }

   public static class Builder {
      private final XMSSParameters params;
      private int index = 0;
      private byte[] secretKeySeed = null;
      private byte[] secretKeyPRF = null;
      private byte[] publicSeed = null;
      private byte[] root = null;
      private BDS bdsState = null;
      private byte[] privateKey = null;
      private XMSS xmss = null;

      public Builder(XMSSParameters var1) {
         this.params = var1;
      }

      public Builder withIndex(int var1) {
         this.index = var1;
         return this;
      }

      public Builder withSecretKeySeed(byte[] var1) {
         this.secretKeySeed = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withSecretKeyPRF(byte[] var1) {
         this.secretKeyPRF = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withPublicSeed(byte[] var1) {
         this.publicSeed = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withRoot(byte[] var1) {
         this.root = XMSSUtil.cloneArray(var1);
         return this;
      }

      public Builder withBDSState(BDS var1) {
         this.bdsState = var1;
         return this;
      }

      public Builder withPrivateKey(byte[] var1, XMSS var2) {
         this.privateKey = XMSSUtil.cloneArray(var1);
         this.xmss = var2;
         return this;
      }

      public XMSSPrivateKeyParameters build() throws ParseException, ClassNotFoundException, IOException {
         return new XMSSPrivateKeyParameters(this);
      }
   }
}
