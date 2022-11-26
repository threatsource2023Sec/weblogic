package org.python.bouncycastle.pqc.crypto.gmss;

import java.security.SecureRandom;
import java.util.Vector;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.python.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.python.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;

public class GMSSKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private GMSSRandom gmssRandom;
   private Digest messDigestTree;
   private byte[][] currentSeeds;
   private byte[][] nextNextSeeds;
   private byte[][] currentRootSigs;
   private GMSSDigestProvider digestProvider;
   private int mdLength;
   private int numLayer;
   private boolean initialized = false;
   private GMSSParameters gmssPS;
   private int[] heightOfTrees;
   private int[] otsIndex;
   private int[] K;
   private GMSSKeyGenerationParameters gmssParams;
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.3";

   public GMSSKeyPairGenerator(GMSSDigestProvider var1) {
      this.digestProvider = var1;
      this.messDigestTree = var1.get();
      this.mdLength = this.messDigestTree.getDigestSize();
      this.gmssRandom = new GMSSRandom(this.messDigestTree);
   }

   private AsymmetricCipherKeyPair genKeyPair() {
      if (!this.initialized) {
         this.initializeDefault();
      }

      byte[][][] var1 = new byte[this.numLayer][][];
      byte[][][] var2 = new byte[this.numLayer - 1][][];
      Treehash[][] var3 = new Treehash[this.numLayer][];
      Treehash[][] var4 = new Treehash[this.numLayer - 1][];
      Vector[] var5 = new Vector[this.numLayer];
      Vector[] var6 = new Vector[this.numLayer - 1];
      Vector[][] var7 = new Vector[this.numLayer][];
      Vector[][] var8 = new Vector[this.numLayer - 1][];

      for(int var9 = 0; var9 < this.numLayer; ++var9) {
         var1[var9] = new byte[this.heightOfTrees[var9]][this.mdLength];
         var3[var9] = new Treehash[this.heightOfTrees[var9] - this.K[var9]];
         if (var9 > 0) {
            var2[var9 - 1] = new byte[this.heightOfTrees[var9]][this.mdLength];
            var4[var9 - 1] = new Treehash[this.heightOfTrees[var9] - this.K[var9]];
         }

         var5[var9] = new Vector();
         if (var9 > 0) {
            var6[var9 - 1] = new Vector();
         }
      }

      byte[][] var16 = new byte[this.numLayer][this.mdLength];
      byte[][] var10 = new byte[this.numLayer - 1][this.mdLength];
      byte[][] var11 = new byte[this.numLayer][this.mdLength];

      int var12;
      for(var12 = 0; var12 < this.numLayer; ++var12) {
         System.arraycopy(this.currentSeeds[var12], 0, var11[var12], 0, this.mdLength);
      }

      this.currentRootSigs = new byte[this.numLayer - 1][this.mdLength];

      GMSSRootCalc var13;
      int var14;
      for(var12 = this.numLayer - 1; var12 >= 0; --var12) {
         var13 = new GMSSRootCalc(this.heightOfTrees[var12], this.K[var12], this.digestProvider);

         try {
            if (var12 == this.numLayer - 1) {
               var13 = this.generateCurrentAuthpathAndRoot((byte[])null, var5[var12], var11[var12], var12);
            } else {
               var13 = this.generateCurrentAuthpathAndRoot(var16[var12 + 1], var5[var12], var11[var12], var12);
            }
         } catch (Exception var15) {
            var15.printStackTrace();
         }

         for(var14 = 0; var14 < this.heightOfTrees[var12]; ++var14) {
            System.arraycopy(var13.getAuthPath()[var14], 0, var1[var12][var14], 0, this.mdLength);
         }

         var7[var12] = var13.getRetain();
         var3[var12] = var13.getTreehash();
         System.arraycopy(var13.getRoot(), 0, var16[var12], 0, this.mdLength);
      }

      for(var12 = this.numLayer - 2; var12 >= 0; --var12) {
         var13 = this.generateNextAuthpathAndRoot(var6[var12], var11[var12 + 1], var12 + 1);

         for(var14 = 0; var14 < this.heightOfTrees[var12 + 1]; ++var14) {
            System.arraycopy(var13.getAuthPath()[var14], 0, var2[var12][var14], 0, this.mdLength);
         }

         var8[var12] = var13.getRetain();
         var4[var12] = var13.getTreehash();
         System.arraycopy(var13.getRoot(), 0, var10[var12], 0, this.mdLength);
         System.arraycopy(var11[var12 + 1], 0, this.nextNextSeeds[var12], 0, this.mdLength);
      }

      GMSSPublicKeyParameters var17 = new GMSSPublicKeyParameters(var16[0], this.gmssPS);
      GMSSPrivateKeyParameters var18 = new GMSSPrivateKeyParameters(this.currentSeeds, this.nextNextSeeds, var1, var2, var3, var4, var5, var6, var7, var8, var10, this.currentRootSigs, this.gmssPS, this.digestProvider);
      return new AsymmetricCipherKeyPair(var17, var18);
   }

   private GMSSRootCalc generateCurrentAuthpathAndRoot(byte[] var1, Vector var2, byte[] var3, int var4) {
      byte[] var5 = new byte[this.mdLength];
      byte[] var6 = new byte[this.mdLength];
      var6 = this.gmssRandom.nextSeed(var3);
      GMSSRootCalc var7 = new GMSSRootCalc(this.heightOfTrees[var4], this.K[var4], this.digestProvider);
      var7.initialize(var2);
      WinternitzOTSignature var8;
      if (var4 == this.numLayer - 1) {
         var8 = new WinternitzOTSignature(var6, this.digestProvider.get(), this.otsIndex[var4]);
         var5 = var8.getPublicKey();
      } else {
         var8 = new WinternitzOTSignature(var6, this.digestProvider.get(), this.otsIndex[var4]);
         this.currentRootSigs[var4] = var8.getSignature(var1);
         WinternitzOTSVerify var9 = new WinternitzOTSVerify(this.digestProvider.get(), this.otsIndex[var4]);
         var5 = var9.Verify(var1, this.currentRootSigs[var4]);
      }

      var7.update(var5);
      int var12 = 3;
      int var10 = 0;

      for(int var11 = 1; var11 < 1 << this.heightOfTrees[var4]; ++var11) {
         if (var11 == var12 && var10 < this.heightOfTrees[var4] - this.K[var4]) {
            var7.initializeTreehashSeed(var3, var10);
            var12 *= 2;
            ++var10;
         }

         var6 = this.gmssRandom.nextSeed(var3);
         var8 = new WinternitzOTSignature(var6, this.digestProvider.get(), this.otsIndex[var4]);
         var7.update(var8.getPublicKey());
      }

      if (var7.wasFinished()) {
         return var7;
      } else {
         System.err.println("Baum noch nicht fertig konstruiert!!!");
         return null;
      }
   }

   private GMSSRootCalc generateNextAuthpathAndRoot(Vector var1, byte[] var2, int var3) {
      byte[] var4 = new byte[this.numLayer];
      GMSSRootCalc var5 = new GMSSRootCalc(this.heightOfTrees[var3], this.K[var3], this.digestProvider);
      var5.initialize(var1);
      int var6 = 3;
      int var7 = 0;

      for(int var8 = 0; var8 < 1 << this.heightOfTrees[var3]; ++var8) {
         if (var8 == var6 && var7 < this.heightOfTrees[var3] - this.K[var3]) {
            var5.initializeTreehashSeed(var2, var7);
            var6 *= 2;
            ++var7;
         }

         var4 = this.gmssRandom.nextSeed(var2);
         WinternitzOTSignature var9 = new WinternitzOTSignature(var4, this.digestProvider.get(), this.otsIndex[var3]);
         var5.update(var9.getPublicKey());
      }

      if (var5.wasFinished()) {
         return var5;
      } else {
         System.err.println("N�chster Baum noch nicht fertig konstruiert!!!");
         return null;
      }
   }

   public void initialize(int var1, SecureRandom var2) {
      int[] var3;
      int[] var4;
      int[] var5;
      GMSSKeyGenerationParameters var6;
      if (var1 <= 10) {
         var3 = new int[]{10};
         var4 = new int[]{3};
         var5 = new int[]{2};
         var6 = new GMSSKeyGenerationParameters(var2, new GMSSParameters(var3.length, var3, var4, var5));
      } else if (var1 <= 20) {
         var3 = new int[]{10, 10};
         var4 = new int[]{5, 4};
         var5 = new int[]{2, 2};
         var6 = new GMSSKeyGenerationParameters(var2, new GMSSParameters(var3.length, var3, var4, var5));
      } else {
         var3 = new int[]{10, 10, 10, 10};
         var4 = new int[]{9, 9, 9, 3};
         var5 = new int[]{2, 2, 2, 2};
         var6 = new GMSSKeyGenerationParameters(var2, new GMSSParameters(var3.length, var3, var4, var5));
      }

      this.initialize(var6);
   }

   public void initialize(KeyGenerationParameters var1) {
      this.gmssParams = (GMSSKeyGenerationParameters)var1;
      this.gmssPS = new GMSSParameters(this.gmssParams.getParameters().getNumOfLayers(), this.gmssParams.getParameters().getHeightOfTrees(), this.gmssParams.getParameters().getWinternitzParameter(), this.gmssParams.getParameters().getK());
      this.numLayer = this.gmssPS.getNumOfLayers();
      this.heightOfTrees = this.gmssPS.getHeightOfTrees();
      this.otsIndex = this.gmssPS.getWinternitzParameter();
      this.K = this.gmssPS.getK();
      this.currentSeeds = new byte[this.numLayer][this.mdLength];
      this.nextNextSeeds = new byte[this.numLayer - 1][this.mdLength];
      SecureRandom var2 = new SecureRandom();

      for(int var3 = 0; var3 < this.numLayer; ++var3) {
         var2.nextBytes(this.currentSeeds[var3]);
         this.gmssRandom.nextSeed(this.currentSeeds[var3]);
      }

      this.initialized = true;
   }

   private void initializeDefault() {
      int[] var1 = new int[]{10, 10, 10, 10};
      int[] var2 = new int[]{3, 3, 3, 3};
      int[] var3 = new int[]{2, 2, 2, 2};
      GMSSKeyGenerationParameters var4 = new GMSSKeyGenerationParameters(new SecureRandom(), new GMSSParameters(var1.length, var1, var2, var3));
      this.initialize(var4);
   }

   public void init(KeyGenerationParameters var1) {
      this.initialize(var1);
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      return this.genKeyPair();
   }
}
