package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;

public class XMSS {
   private XMSSParameters params;
   private WOTSPlus wotsPlus;
   private SecureRandom prng;
   private KeyedHashFunctions khf;
   private XMSSPrivateKeyParameters privateKey;
   private XMSSPublicKeyParameters publicKey;

   public XMSS(XMSSParameters var1) {
      if (var1 == null) {
         throw new NullPointerException("params == null");
      } else {
         this.params = var1;
         this.wotsPlus = var1.getWOTSPlus();
         this.prng = var1.getPRNG();
         this.khf = this.wotsPlus.getKhf();

         try {
            this.privateKey = (new XMSSPrivateKeyParameters.Builder(var1)).withBDSState(new BDS(this)).build();
            this.publicKey = (new XMSSPublicKeyParameters.Builder(var1)).build();
         } catch (ParseException var3) {
            var3.printStackTrace();
         } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
         } catch (IOException var5) {
            var5.printStackTrace();
         }

      }
   }

   public void generateKeys() {
      this.privateKey = this.generatePrivateKey();
      XMSSNode var1 = this.getBDSState().initialize((OTSHashAddress)(new OTSHashAddress.Builder()).build());

      try {
         this.privateKey = (new XMSSPrivateKeyParameters.Builder(this.params)).withIndex(this.privateKey.getIndex()).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.privateKey.getPublicSeed()).withRoot(var1.getValue()).withBDSState(this.privateKey.getBDSState()).build();
         this.publicKey = (new XMSSPublicKeyParameters.Builder(this.params)).withRoot(var1.getValue()).withPublicSeed(this.getPublicSeed()).build();
      } catch (ParseException var3) {
         var3.printStackTrace();
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   private XMSSPrivateKeyParameters generatePrivateKey() {
      int var1 = this.params.getDigestSize();
      byte[] var2 = new byte[var1];
      this.prng.nextBytes(var2);
      byte[] var3 = new byte[var1];
      this.prng.nextBytes(var3);
      byte[] var4 = new byte[var1];
      this.prng.nextBytes(var4);
      XMSSPrivateKeyParameters var5 = null;

      try {
         var5 = (new XMSSPrivateKeyParameters.Builder(this.params)).withSecretKeySeed(var2).withSecretKeyPRF(var3).withPublicSeed(var4).withBDSState(this.privateKey.getBDSState()).build();
      } catch (ParseException var7) {
         var7.printStackTrace();
      } catch (ClassNotFoundException var8) {
         var8.printStackTrace();
      } catch (IOException var9) {
         var9.printStackTrace();
      }

      return var5;
   }

   public void importState(byte[] var1, byte[] var2) throws ParseException, ClassNotFoundException, IOException {
      if (var1 == null) {
         throw new NullPointerException("privateKey == null");
      } else if (var2 == null) {
         throw new NullPointerException("publicKey == null");
      } else {
         XMSSPrivateKeyParameters var3 = (new XMSSPrivateKeyParameters.Builder(this.params)).withPrivateKey(var1, this).build();
         XMSSPublicKeyParameters var4 = (new XMSSPublicKeyParameters.Builder(this.params)).withPublicKey(var2).build();
         if (!XMSSUtil.compareByteArray(var3.getRoot(), var4.getRoot())) {
            throw new IllegalStateException("root of private key and public key do not match");
         } else if (!XMSSUtil.compareByteArray(var3.getPublicSeed(), var4.getPublicSeed())) {
            throw new IllegalStateException("public seed of private key and public key do not match");
         } else {
            this.privateKey = var3;
            this.publicKey = var4;
            this.wotsPlus.importKeys(new byte[this.params.getDigestSize()], this.privateKey.getPublicSeed());
         }
      }
   }

   public byte[] sign(byte[] var1) {
      if (var1 == null) {
         throw new NullPointerException("message == null");
      } else if (this.getBDSState().getAuthenticationPath().isEmpty()) {
         throw new IllegalStateException("not initialized");
      } else {
         int var2 = this.privateKey.getIndex();
         if (!XMSSUtil.isIndexValid(this.getParams().getHeight(), (long)var2)) {
            throw new IllegalArgumentException("index out of bounds");
         } else {
            byte[] var3 = this.khf.PRF(this.privateKey.getSecretKeyPRF(), XMSSUtil.toBytesBigEndian((long)var2, 32));
            byte[] var4 = XMSSUtil.concat(var3, this.privateKey.getRoot(), XMSSUtil.toBytesBigEndian((long)var2, this.params.getDigestSize()));
            byte[] var5 = this.khf.HMsg(var4, var1);
            OTSHashAddress var6 = (OTSHashAddress)(new OTSHashAddress.Builder()).withOTSAddress(var2).build();
            WOTSPlusSignature var7 = this.wotsSign(var5, var6);
            XMSSSignature var8 = null;

            try {
               var8 = (XMSSSignature)(new XMSSSignature.Builder(this.params)).withIndex(var2).withRandom(var3).withWOTSPlusSignature(var7).withAuthPath(this.getBDSState().getAuthenticationPath()).build();
            } catch (ParseException var10) {
               var10.printStackTrace();
            }

            int var9 = this.getParams().getHeight();
            if (var2 < (1 << var9) - 1) {
               this.getBDSState().nextAuthenticationPath((OTSHashAddress)(new OTSHashAddress.Builder()).build());
            }

            this.setIndex(var2 + 1);
            return var8.toByteArray();
         }
      }
   }

   public boolean verifySignature(byte[] var1, byte[] var2, byte[] var3) throws ParseException {
      if (var1 == null) {
         throw new NullPointerException("message == null");
      } else if (var2 == null) {
         throw new NullPointerException("signature == null");
      } else if (var3 == null) {
         throw new NullPointerException("publicKey == null");
      } else {
         XMSSSignature var4 = (new XMSSSignature.Builder(this.params)).withSignature(var2).build();
         XMSSPublicKeyParameters var5 = (new XMSSPublicKeyParameters.Builder(this.params)).withPublicKey(var3).build();
         int var6 = this.privateKey.getIndex();
         byte[] var7 = this.privateKey.getPublicSeed();
         int var8 = var4.getIndex();
         this.setIndex(var8);
         this.setPublicSeed(var5.getPublicSeed());
         this.wotsPlus.importKeys(new byte[this.params.getDigestSize()], this.getPublicSeed());
         byte[] var9 = XMSSUtil.concat(var4.getRandom(), var5.getRoot(), XMSSUtil.toBytesBigEndian((long)var8, this.params.getDigestSize()));
         byte[] var10 = this.khf.HMsg(var9, var1);
         OTSHashAddress var11 = (OTSHashAddress)(new OTSHashAddress.Builder()).withOTSAddress(var8).build();
         XMSSNode var12 = this.getRootNodeFromSignature(var10, var4, var11);
         this.setIndex(var6);
         this.setPublicSeed(var7);
         return XMSSUtil.compareByteArray(var12.getValue(), var5.getRoot());
      }
   }

   public byte[] exportPrivateKey() {
      return this.privateKey.toByteArray();
   }

   public byte[] exportPublicKey() {
      return this.publicKey.toByteArray();
   }

   protected XMSSNode randomizeHash(XMSSNode var1, XMSSNode var2, XMSSAddress var3) {
      if (var1 == null) {
         throw new NullPointerException("left == null");
      } else if (var2 == null) {
         throw new NullPointerException("right == null");
      } else if (var1.getHeight() != var2.getHeight()) {
         throw new IllegalStateException("height of both nodes must be equal");
      } else if (var3 == null) {
         throw new NullPointerException("address == null");
      } else {
         byte[] var4 = this.getPublicSeed();
         if (var3 instanceof LTreeAddress) {
            LTreeAddress var5 = (LTreeAddress)var3;
            var3 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var5.getLayerAddress())).withTreeAddress(var5.getTreeAddress())).withLTreeAddress(var5.getLTreeAddress()).withTreeHeight(var5.getTreeHeight()).withTreeIndex(var5.getTreeIndex()).withKeyAndMask(0)).build();
         } else if (var3 instanceof HashTreeAddress) {
            HashTreeAddress var11 = (HashTreeAddress)var3;
            var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var11.getLayerAddress())).withTreeAddress(var11.getTreeAddress())).withTreeHeight(var11.getTreeHeight()).withTreeIndex(var11.getTreeIndex()).withKeyAndMask(0)).build();
         }

         byte[] var12 = this.khf.PRF(var4, ((XMSSAddress)var3).toByteArray());
         if (var3 instanceof LTreeAddress) {
            LTreeAddress var6 = (LTreeAddress)var3;
            var3 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var6.getLayerAddress())).withTreeAddress(var6.getTreeAddress())).withLTreeAddress(var6.getLTreeAddress()).withTreeHeight(var6.getTreeHeight()).withTreeIndex(var6.getTreeIndex()).withKeyAndMask(1)).build();
         } else if (var3 instanceof HashTreeAddress) {
            HashTreeAddress var13 = (HashTreeAddress)var3;
            var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var13.getLayerAddress())).withTreeAddress(var13.getTreeAddress())).withTreeHeight(var13.getTreeHeight()).withTreeIndex(var13.getTreeIndex()).withKeyAndMask(1)).build();
         }

         byte[] var14 = this.khf.PRF(var4, ((XMSSAddress)var3).toByteArray());
         if (var3 instanceof LTreeAddress) {
            LTreeAddress var7 = (LTreeAddress)var3;
            var3 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var7.getLayerAddress())).withTreeAddress(var7.getTreeAddress())).withLTreeAddress(var7.getLTreeAddress()).withTreeHeight(var7.getTreeHeight()).withTreeIndex(var7.getTreeIndex()).withKeyAndMask(2)).build();
         } else if (var3 instanceof HashTreeAddress) {
            HashTreeAddress var15 = (HashTreeAddress)var3;
            var3 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var15.getLayerAddress())).withTreeAddress(var15.getTreeAddress())).withTreeHeight(var15.getTreeHeight()).withTreeIndex(var15.getTreeIndex()).withKeyAndMask(2)).build();
         }

         byte[] var16 = this.khf.PRF(var4, ((XMSSAddress)var3).toByteArray());
         int var8 = this.params.getDigestSize();
         byte[] var9 = new byte[2 * var8];

         int var10;
         for(var10 = 0; var10 < var8; ++var10) {
            var9[var10] = (byte)(var1.getValue()[var10] ^ var14[var10]);
         }

         for(var10 = 0; var10 < var8; ++var10) {
            var9[var10 + var8] = (byte)(var2.getValue()[var10] ^ var16[var10]);
         }

         byte[] var17 = this.khf.H(var12, var9);
         return new XMSSNode(var1.getHeight(), var17);
      }
   }

   protected XMSSNode lTree(WOTSPlusPublicKeyParameters var1, LTreeAddress var2) {
      if (var1 == null) {
         throw new NullPointerException("publicKey == null");
      } else if (var2 == null) {
         throw new NullPointerException("address == null");
      } else {
         int var3 = this.wotsPlus.getParams().getLen();
         byte[][] var4 = var1.toByteArray();
         XMSSNode[] var5 = new XMSSNode[var4.length];

         int var6;
         for(var6 = 0; var6 < var4.length; ++var6) {
            var5[var6] = new XMSSNode(0, var4[var6]);
         }

         for(var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var2.getLayerAddress())).withTreeAddress(var2.getTreeAddress())).withLTreeAddress(var2.getLTreeAddress()).withTreeHeight(0).withTreeIndex(var2.getTreeIndex()).withKeyAndMask(var2.getKeyAndMask())).build(); var3 > 1; var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var2.getLayerAddress())).withTreeAddress(var2.getTreeAddress())).withLTreeAddress(var2.getLTreeAddress()).withTreeHeight(var2.getTreeHeight() + 1).withTreeIndex(var2.getTreeIndex()).withKeyAndMask(var2.getKeyAndMask())).build()) {
            for(var6 = 0; var6 < (int)Math.floor((double)(var3 / 2)); ++var6) {
               var2 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var2.getLayerAddress())).withTreeAddress(var2.getTreeAddress())).withLTreeAddress(var2.getLTreeAddress()).withTreeHeight(var2.getTreeHeight()).withTreeIndex(var6).withKeyAndMask(var2.getKeyAndMask())).build();
               var5[var6] = this.randomizeHash(var5[2 * var6], var5[2 * var6 + 1], var2);
            }

            if (var3 % 2 == 1) {
               var5[(int)Math.floor((double)(var3 / 2))] = var5[var3 - 1];
            }

            var3 = (int)Math.ceil((double)var3 / 2.0);
         }

         return var5[0];
      }
   }

   protected WOTSPlusSignature wotsSign(byte[] var1, OTSHashAddress var2) {
      if (var1.length != this.params.getDigestSize()) {
         throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
      } else if (var2 == null) {
         throw new NullPointerException("otsHashAddress == null");
      } else {
         this.wotsPlus.importKeys(this.getWOTSPlusSecretKey(var2), this.getPublicSeed());
         return this.wotsPlus.sign(var1, var2);
      }
   }

   protected XMSSNode getRootNodeFromSignature(byte[] var1, XMSSReducedSignature var2, OTSHashAddress var3) {
      if (var1.length != this.params.getDigestSize()) {
         throw new IllegalArgumentException("size of messageDigest needs to be equal to size of digest");
      } else if (var2 == null) {
         throw new NullPointerException("signature == null");
      } else if (var3 == null) {
         throw new NullPointerException("otsHashAddress == null");
      } else {
         LTreeAddress var4 = (LTreeAddress)((LTreeAddress.Builder)((LTreeAddress.Builder)(new LTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withLTreeAddress(var3.getOTSAddress()).build();
         HashTreeAddress var5 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var3.getLayerAddress())).withTreeAddress(var3.getTreeAddress())).withTreeIndex(var3.getOTSAddress()).build();
         WOTSPlusPublicKeyParameters var6 = this.wotsPlus.getPublicKeyFromSignature(var1, var2.getWOTSPlusSignature(), var3);
         XMSSNode[] var7 = new XMSSNode[]{this.lTree(var6, var4), null};

         for(int var8 = 0; var8 < this.params.getHeight(); ++var8) {
            var5 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var5.getLayerAddress())).withTreeAddress(var5.getTreeAddress())).withTreeHeight(var8).withTreeIndex(var5.getTreeIndex()).withKeyAndMask(var5.getKeyAndMask())).build();
            if (Math.floor((double)(this.privateKey.getIndex() / (1 << var8))) % 2.0 == 0.0) {
               var5 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var5.getLayerAddress())).withTreeAddress(var5.getTreeAddress())).withTreeHeight(var5.getTreeHeight()).withTreeIndex(var5.getTreeIndex() / 2).withKeyAndMask(var5.getKeyAndMask())).build();
               var7[1] = this.randomizeHash(var7[0], (XMSSNode)var2.getAuthPath().get(var8), var5);
               var7[1] = new XMSSNode(var7[1].getHeight() + 1, var7[1].getValue());
            } else {
               var5 = (HashTreeAddress)((HashTreeAddress.Builder)((HashTreeAddress.Builder)((HashTreeAddress.Builder)(new HashTreeAddress.Builder()).withLayerAddress(var5.getLayerAddress())).withTreeAddress(var5.getTreeAddress())).withTreeHeight(var5.getTreeHeight()).withTreeIndex((var5.getTreeIndex() - 1) / 2).withKeyAndMask(var5.getKeyAndMask())).build();
               var7[1] = this.randomizeHash((XMSSNode)var2.getAuthPath().get(var8), var7[0], var5);
               var7[1] = new XMSSNode(var7[1].getHeight() + 1, var7[1].getValue());
            }

            var7[0] = var7[1];
         }

         return var7[0];
      }
   }

   protected byte[] getWOTSPlusSecretKey(OTSHashAddress var1) {
      var1 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var1.getLayerAddress())).withTreeAddress(var1.getTreeAddress())).withOTSAddress(var1.getOTSAddress()).build();
      return this.khf.PRF(this.privateKey.getSecretKeySeed(), var1.toByteArray());
   }

   public XMSSParameters getParams() {
      return this.params;
   }

   protected WOTSPlus getWOTSPlus() {
      return this.wotsPlus;
   }

   protected KeyedHashFunctions getKhf() {
      return this.khf;
   }

   public byte[] getRoot() {
      return this.privateKey.getRoot();
   }

   protected void setRoot(byte[] var1) {
      try {
         this.privateKey = (new XMSSPrivateKeyParameters.Builder(this.params)).withIndex(this.privateKey.getIndex()).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.getPublicSeed()).withRoot(var1).withBDSState(this.privateKey.getBDSState()).build();
         this.publicKey = (new XMSSPublicKeyParameters.Builder(this.params)).withRoot(var1).withPublicSeed(this.getPublicSeed()).build();
      } catch (ParseException var3) {
         var3.printStackTrace();
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public int getIndex() {
      return this.privateKey.getIndex();
   }

   protected void setIndex(int var1) {
      try {
         this.privateKey = (new XMSSPrivateKeyParameters.Builder(this.params)).withIndex(var1).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.privateKey.getPublicSeed()).withRoot(this.privateKey.getRoot()).withBDSState(this.privateKey.getBDSState()).build();
      } catch (ParseException var3) {
         var3.printStackTrace();
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public byte[] getPublicSeed() {
      return this.privateKey.getPublicSeed();
   }

   protected void setPublicSeed(byte[] var1) {
      try {
         this.privateKey = (new XMSSPrivateKeyParameters.Builder(this.params)).withIndex(this.privateKey.getIndex()).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(var1).withRoot(this.getRoot()).withBDSState(this.privateKey.getBDSState()).build();
         this.publicKey = (new XMSSPublicKeyParameters.Builder(this.params)).withRoot(this.getRoot()).withPublicSeed(var1).build();
      } catch (ParseException var3) {
         var3.printStackTrace();
      } catch (ClassNotFoundException var4) {
         var4.printStackTrace();
      } catch (IOException var5) {
         var5.printStackTrace();
      }

      this.wotsPlus.importKeys(new byte[this.params.getDigestSize()], var1);
   }

   protected BDS getBDSState() {
      return this.privateKey.getBDSState();
   }
}
