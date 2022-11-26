package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public final class XMSSMTPrivateKeyParameters implements XMSSStoreableObjectInterface {
   private final XMSSMTParameters params;
   private final long index;
   private final byte[] secretKeySeed;
   private final byte[] secretKeyPRF;
   private final byte[] publicSeed;
   private final byte[] root;
   private final Map bdsState;

   private XMSSMTPrivateKeyParameters(Builder var1) throws ParseException, ClassNotFoundException, IOException {
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
            int var5 = (int)Math.ceil((double)var4 / 8.0);
            int var10 = 0;
            this.index = XMSSUtil.bytesToXBigEndian(var3, var10, var5);
            if (!XMSSUtil.isIndexValid(var4, this.index)) {
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
            TreeMap var12 = (TreeMap)XMSSUtil.deserialize(var11);
            Iterator var13 = var12.keySet().iterator();

            while(var13.hasNext()) {
               Integer var14 = (Integer)var13.next();
               BDS var15 = (BDS)var12.get(var14);
               var15.setXMSS(var1.xmss);
               var15.validate();
            }

            this.bdsState = var12;
         } else {
            this.index = var1.index;
            byte[] var16 = var1.secretKeySeed;
            if (var16 != null) {
               if (var16.length != var2) {
                  throw new IllegalArgumentException("size of secretKeySeed needs to be equal size of digest");
               }

               this.secretKeySeed = var16;
            } else {
               this.secretKeySeed = new byte[var2];
            }

            byte[] var17 = var1.secretKeyPRF;
            if (var17 != null) {
               if (var17.length != var2) {
                  throw new IllegalArgumentException("size of secretKeyPRF needs to be equal size of digest");
               }

               this.secretKeyPRF = var17;
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

            Map var8 = var1.bdsState;
            if (var8 != null) {
               this.bdsState = var8;
            } else {
               this.bdsState = new TreeMap();
            }
         }

      }
   }

   public byte[] toByteArray() {
      int var1 = this.params.getDigestSize();
      int var2 = (int)Math.ceil((double)this.params.getHeight() / 8.0);
      int var7 = var2 + var1 + var1 + var1 + var1;
      byte[] var8 = new byte[var7];
      int var9 = 0;
      byte[] var10 = XMSSUtil.toBytesBigEndian(this.index, var2);
      XMSSUtil.copyBytesAtOffset(var8, var10, var9);
      var9 += var2;
      XMSSUtil.copyBytesAtOffset(var8, this.secretKeySeed, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.secretKeyPRF, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.publicSeed, var9);
      var9 += var1;
      XMSSUtil.copyBytesAtOffset(var8, this.root, var9);
      Object var11 = null;

      byte[] var14;
      try {
         var14 = XMSSUtil.serialize(this.bdsState);
      } catch (IOException var13) {
         var13.printStackTrace();
         throw new RuntimeException("error serializing bds state");
      }

      return XMSSUtil.concat(var8, var14);
   }

   public long getIndex() {
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

   public Map getBDSState() {
      return this.bdsState;
   }

   // $FF: synthetic method
   XMSSMTPrivateKeyParameters(Builder var1, Object var2) throws ParseException, ClassNotFoundException, IOException {
      this(var1);
   }

   public static class Builder {
      private final XMSSMTParameters params;
      private long index = 0L;
      private byte[] secretKeySeed = null;
      private byte[] secretKeyPRF = null;
      private byte[] publicSeed = null;
      private byte[] root = null;
      private Map bdsState = null;
      private byte[] privateKey = null;
      private XMSS xmss = null;

      public Builder(XMSSMTParameters var1) {
         this.params = var1;
      }

      public Builder withIndex(long var1) {
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

      public Builder withBDSState(Map var1) {
         this.bdsState = var1;
         return this;
      }

      public Builder withPrivateKey(byte[] var1, XMSS var2) {
         this.privateKey = XMSSUtil.cloneArray(var1);
         this.xmss = var2;
         return this;
      }

      public XMSSMTPrivateKeyParameters build() throws ParseException, ClassNotFoundException, IOException {
         return new XMSSMTPrivateKeyParameters(this);
      }
   }
}
