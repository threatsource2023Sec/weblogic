package org.python.bouncycastle.pqc.crypto.xmss;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Map;

public final class XMSSMT {
   private XMSSMTParameters params;
   private XMSS xmss;
   private SecureRandom prng;
   private KeyedHashFunctions khf;
   private XMSSMTPrivateKeyParameters privateKey;
   private XMSSMTPublicKeyParameters publicKey;

   public XMSSMT(XMSSMTParameters var1) {
      if (var1 == null) {
         throw new NullPointerException("params == null");
      } else {
         this.params = var1;
         this.xmss = var1.getXMSS();
         this.prng = var1.getXMSS().getParams().getPRNG();
         this.khf = this.xmss.getKhf();

         try {
            this.privateKey = (new XMSSMTPrivateKeyParameters.Builder(var1)).build();
            this.publicKey = (new XMSSMTPublicKeyParameters.Builder(var1)).build();
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
      XMSSPrivateKeyParameters var1 = null;
      XMSSPublicKeyParameters var2 = null;

      try {
         var1 = (new XMSSPrivateKeyParameters.Builder(this.xmss.getParams())).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.privateKey.getPublicSeed()).withBDSState(new BDS(this.xmss)).build();
         var2 = (new XMSSPublicKeyParameters.Builder(this.xmss.getParams())).withPublicSeed(this.getPublicSeed()).build();
      } catch (ParseException var14) {
         var14.printStackTrace();
      } catch (ClassNotFoundException var15) {
         var15.printStackTrace();
      } catch (IOException var16) {
         var16.printStackTrace();
      }

      try {
         this.xmss.importState(var1.toByteArray(), var2.toByteArray());
      } catch (ParseException var11) {
         var11.printStackTrace();
      } catch (ClassNotFoundException var12) {
         var12.printStackTrace();
      } catch (IOException var13) {
         var13.printStackTrace();
      }

      int var3 = this.params.getLayers() - 1;
      OTSHashAddress var4 = (OTSHashAddress)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var3)).build();
      BDS var5 = new BDS(this.xmss);
      XMSSNode var6 = var5.initialize(var4);
      this.getBDSState().put(var3, var5);
      this.xmss.setRoot(var6.getValue());

      try {
         this.privateKey = (new XMSSMTPrivateKeyParameters.Builder(this.params)).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.privateKey.getPublicSeed()).withRoot(this.xmss.getRoot()).withBDSState(this.privateKey.getBDSState()).build();
         this.publicKey = (new XMSSMTPublicKeyParameters.Builder(this.params)).withRoot(var6.getValue()).withPublicSeed(this.getPublicSeed()).build();
      } catch (ParseException var8) {
         var8.printStackTrace();
      } catch (ClassNotFoundException var9) {
         var9.printStackTrace();
      } catch (IOException var10) {
         var10.printStackTrace();
      }

   }

   private XMSSMTPrivateKeyParameters generatePrivateKey() {
      int var1 = this.params.getDigestSize();
      byte[] var2 = new byte[var1];
      this.prng.nextBytes(var2);
      byte[] var3 = new byte[var1];
      this.prng.nextBytes(var3);
      byte[] var4 = new byte[var1];
      this.prng.nextBytes(var4);
      XMSSMTPrivateKeyParameters var5 = null;

      try {
         var5 = (new XMSSMTPrivateKeyParameters.Builder(this.params)).withSecretKeySeed(var2).withSecretKeyPRF(var3).withPublicSeed(var4).withBDSState(this.privateKey.getBDSState()).build();
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
         XMSSMTPrivateKeyParameters var3 = (new XMSSMTPrivateKeyParameters.Builder(this.params)).withPrivateKey(var1, this.xmss).build();
         XMSSMTPublicKeyParameters var4 = (new XMSSMTPublicKeyParameters.Builder(this.params)).withPublicKey(var2).build();
         if (!XMSSUtil.compareByteArray(var3.getRoot(), var4.getRoot())) {
            throw new IllegalStateException("root of private key and public key do not match");
         } else if (!XMSSUtil.compareByteArray(var3.getPublicSeed(), var4.getPublicSeed())) {
            throw new IllegalStateException("public seed of private key and public key do not match");
         } else {
            XMSSPrivateKeyParameters var5 = (new XMSSPrivateKeyParameters.Builder(this.xmss.getParams())).withSecretKeySeed(var3.getSecretKeySeed()).withSecretKeyPRF(var3.getSecretKeyPRF()).withPublicSeed(var3.getPublicSeed()).withRoot(var3.getRoot()).withBDSState(new BDS(this.xmss)).build();
            XMSSPublicKeyParameters var6 = (new XMSSPublicKeyParameters.Builder(this.xmss.getParams())).withRoot(var3.getRoot()).withPublicSeed(this.getPublicSeed()).build();
            this.xmss.importState(var5.toByteArray(), var6.toByteArray());
            this.privateKey = var3;
            this.publicKey = var4;
         }
      }
   }

   public byte[] sign(byte[] var1) {
      if (var1 == null) {
         throw new NullPointerException("message == null");
      } else if (this.getBDSState().isEmpty()) {
         throw new IllegalStateException("not initialized");
      } else {
         long var2 = this.getIndex();
         int var4 = this.params.getHeight();
         int var5 = this.xmss.getParams().getHeight();
         if (!XMSSUtil.isIndexValid(var4, var2)) {
            throw new IllegalArgumentException("index out of bounds");
         } else {
            byte[] var6 = this.khf.PRF(this.privateKey.getSecretKeyPRF(), XMSSUtil.toBytesBigEndian(var2, 32));
            byte[] var7 = XMSSUtil.concat(var6, this.privateKey.getRoot(), XMSSUtil.toBytesBigEndian(var2, this.params.getDigestSize()));
            byte[] var8 = this.khf.HMsg(var7, var1);
            XMSSMTSignature var9 = null;

            try {
               var9 = (new XMSSMTSignature.Builder(this.params)).withIndex(var2).withRandom(var6).build();
            } catch (ParseException var25) {
               var25.printStackTrace();
            }

            long var11 = XMSSUtil.getTreeIndex(var2, var5);
            int var13 = XMSSUtil.getLeafIndex(var2, var5);
            this.xmss.setIndex(var13);
            this.xmss.setPublicSeed(this.getPublicSeed());
            OTSHashAddress var14 = (OTSHashAddress)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withTreeAddress(var11)).withOTSAddress(var13).build();
            WOTSPlusSignature var15 = this.xmss.wotsSign(var8, var14);
            if (this.getBDSState().get(0) == null || var13 == 0) {
               this.getBDSState().put(0, new BDS(this.xmss));
               ((BDS)this.getBDSState().get(0)).initialize(var14);
            }

            XMSSReducedSignature var16 = null;

            try {
               var16 = (new XMSSReducedSignature.Builder(this.xmss.getParams())).withWOTSPlusSignature(var15).withAuthPath(((BDS)this.getBDSState().get(0)).getAuthenticationPath()).build();
            } catch (ParseException var24) {
               var24.printStackTrace();
            }

            var9.getReducedSignatures().add(var16);
            if (var13 < (1 << var5) - 1) {
               ((BDS)this.getBDSState().get(0)).nextAuthenticationPath(var14);
            }

            for(int var17 = 1; var17 < this.params.getLayers(); ++var17) {
               XMSSNode var18 = ((BDS)this.getBDSState().get(var17 - 1)).getRoot();
               var13 = XMSSUtil.getLeafIndex(var11, var5);
               var11 = XMSSUtil.getTreeIndex(var11, var5);
               this.xmss.setIndex(var13);
               var14 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var17)).withTreeAddress(var11)).withOTSAddress(var13).build();
               var15 = this.xmss.wotsSign(var18.getValue(), var14);
               if (this.getBDSState().get(var17) == null || XMSSUtil.isNewBDSInitNeeded(var2, var5, var17)) {
                  this.getBDSState().put(var17, new BDS(this.xmss));
                  ((BDS)this.getBDSState().get(var17)).initialize(var14);
               }

               try {
                  var16 = (new XMSSReducedSignature.Builder(this.xmss.getParams())).withWOTSPlusSignature(var15).withAuthPath(((BDS)this.getBDSState().get(var17)).getAuthenticationPath()).build();
               } catch (ParseException var23) {
                  var23.printStackTrace();
               }

               var9.getReducedSignatures().add(var16);
               if (var13 < (1 << var5) - 1 && XMSSUtil.isNewAuthenticationPathNeeded(var2, var5, var17)) {
                  ((BDS)this.getBDSState().get(var17)).nextAuthenticationPath(var14);
               }
            }

            try {
               this.privateKey = (new XMSSMTPrivateKeyParameters.Builder(this.params)).withIndex(var2 + 1L).withSecretKeySeed(this.privateKey.getSecretKeySeed()).withSecretKeyPRF(this.privateKey.getSecretKeyPRF()).withPublicSeed(this.privateKey.getPublicSeed()).withRoot(this.privateKey.getRoot()).withBDSState(this.privateKey.getBDSState()).build();
            } catch (ParseException var20) {
               var20.printStackTrace();
            } catch (ClassNotFoundException var21) {
               var21.printStackTrace();
            } catch (IOException var22) {
               var22.printStackTrace();
            }

            return var9.toByteArray();
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
         XMSSMTSignature var4 = (new XMSSMTSignature.Builder(this.params)).withSignature(var2).build();
         XMSSMTPublicKeyParameters var5 = (new XMSSMTPublicKeyParameters.Builder(this.params)).withPublicKey(var3).build();
         byte[] var6 = XMSSUtil.concat(var4.getRandom(), var5.getRoot(), XMSSUtil.toBytesBigEndian(var4.getIndex(), this.params.getDigestSize()));
         byte[] var7 = this.khf.HMsg(var6, var1);
         long var8 = var4.getIndex();
         int var10 = this.xmss.getParams().getHeight();
         long var11 = XMSSUtil.getTreeIndex(var8, var10);
         int var13 = XMSSUtil.getLeafIndex(var8, var10);
         this.xmss.setIndex(var13);
         this.xmss.setPublicSeed(var5.getPublicSeed());
         OTSHashAddress var14 = (OTSHashAddress)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withTreeAddress(var11)).withOTSAddress(var13).build();
         XMSSReducedSignature var15 = (XMSSReducedSignature)var4.getReducedSignatures().get(0);
         XMSSNode var16 = this.xmss.getRootNodeFromSignature(var7, var15, var14);

         for(int var17 = 1; var17 < this.params.getLayers(); ++var17) {
            var15 = (XMSSReducedSignature)var4.getReducedSignatures().get(var17);
            var13 = XMSSUtil.getLeafIndex(var11, var10);
            var11 = XMSSUtil.getTreeIndex(var11, var10);
            this.xmss.setIndex(var13);
            var14 = (OTSHashAddress)((OTSHashAddress.Builder)((OTSHashAddress.Builder)(new OTSHashAddress.Builder()).withLayerAddress(var17)).withTreeAddress(var11)).withOTSAddress(var13).build();
            var16 = this.xmss.getRootNodeFromSignature(var16.getValue(), var15, var14);
         }

         return XMSSUtil.compareByteArray(var16.getValue(), var5.getRoot());
      }
   }

   public byte[] exportPrivateKey() {
      return this.privateKey.toByteArray();
   }

   public byte[] exportPublicKey() {
      return this.publicKey.toByteArray();
   }

   public XMSSMTParameters getParams() {
      return this.params;
   }

   public long getIndex() {
      return this.privateKey.getIndex();
   }

   public byte[] getPublicSeed() {
      return this.privateKey.getPublicSeed();
   }

   protected Map getBDSState() {
      return this.privateKey.getBDSState();
   }

   protected XMSS getXMSS() {
      return this.xmss;
   }
}
