package org.python.bouncycastle.pqc.crypto.gmss;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.crypto.params.ParametersWithRandom;
import org.python.bouncycastle.pqc.crypto.MessageSigner;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSUtil;
import org.python.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSVerify;
import org.python.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.python.bouncycastle.util.Arrays;

public class GMSSSigner implements MessageSigner {
   private GMSSUtil gmssUtil = new GMSSUtil();
   private byte[] pubKeyBytes;
   private Digest messDigestTrees;
   private int mdLength;
   private int numLayer;
   private Digest messDigestOTS;
   private WinternitzOTSignature ots;
   private GMSSDigestProvider digestProvider;
   private int[] index;
   private byte[][][] currentAuthPaths;
   private byte[][] subtreeRootSig;
   private GMSSParameters gmssPS;
   private GMSSRandom gmssRandom;
   GMSSKeyParameters key;
   private SecureRandom random;

   public GMSSSigner(GMSSDigestProvider var1) {
      this.digestProvider = var1;
      this.messDigestTrees = var1.get();
      this.messDigestOTS = this.messDigestTrees;
      this.mdLength = this.messDigestTrees.getDigestSize();
      this.gmssRandom = new GMSSRandom(this.messDigestTrees);
   }

   public void init(boolean var1, CipherParameters var2) {
      if (var1) {
         if (var2 instanceof ParametersWithRandom) {
            ParametersWithRandom var3 = (ParametersWithRandom)var2;
            this.random = var3.getRandom();
            this.key = (GMSSPrivateKeyParameters)var3.getParameters();
            this.initSign();
         } else {
            this.random = new SecureRandom();
            this.key = (GMSSPrivateKeyParameters)var2;
            this.initSign();
         }
      } else {
         this.key = (GMSSPublicKeyParameters)var2;
         this.initVerify();
      }

   }

   private void initSign() {
      this.messDigestTrees.reset();
      GMSSPrivateKeyParameters var1 = (GMSSPrivateKeyParameters)this.key;
      if (var1.isUsed()) {
         throw new IllegalStateException("Private key already used");
      } else if (var1.getIndex(0) >= var1.getNumLeafs(0)) {
         throw new IllegalStateException("No more signatures can be generated");
      } else {
         this.gmssPS = var1.getParameters();
         this.numLayer = this.gmssPS.getNumOfLayers();
         byte[] var2 = var1.getCurrentSeeds()[this.numLayer - 1];
         byte[] var3 = new byte[this.mdLength];
         byte[] var4 = new byte[this.mdLength];
         System.arraycopy(var2, 0, var4, 0, this.mdLength);
         var3 = this.gmssRandom.nextSeed(var4);
         this.ots = new WinternitzOTSignature(var3, this.digestProvider.get(), this.gmssPS.getWinternitzParameter()[this.numLayer - 1]);
         byte[][][] var5 = var1.getCurrentAuthPaths();
         this.currentAuthPaths = new byte[this.numLayer][][];

         int var7;
         for(int var6 = 0; var6 < this.numLayer; ++var6) {
            this.currentAuthPaths[var6] = new byte[var5[var6].length][this.mdLength];

            for(var7 = 0; var7 < var5[var6].length; ++var7) {
               System.arraycopy(var5[var6][var7], 0, this.currentAuthPaths[var6][var7], 0, this.mdLength);
            }
         }

         this.index = new int[this.numLayer];
         System.arraycopy(var1.getIndex(), 0, this.index, 0, this.numLayer);
         this.subtreeRootSig = new byte[this.numLayer - 1][];

         for(var7 = 0; var7 < this.numLayer - 1; ++var7) {
            byte[] var8 = var1.getSubtreeRootSig(var7);
            this.subtreeRootSig[var7] = new byte[var8.length];
            System.arraycopy(var8, 0, this.subtreeRootSig[var7], 0, var8.length);
         }

         var1.markUsed();
      }
   }

   public byte[] generateSignature(byte[] var1) {
      byte[] var2 = new byte[this.mdLength];
      var2 = this.ots.getSignature(var1);
      byte[] var3 = this.gmssUtil.concatenateArray(this.currentAuthPaths[this.numLayer - 1]);
      byte[] var4 = this.gmssUtil.intToBytesLittleEndian(this.index[this.numLayer - 1]);
      byte[] var5 = new byte[var4.length + var2.length + var3.length];
      System.arraycopy(var4, 0, var5, 0, var4.length);
      System.arraycopy(var2, 0, var5, var4.length, var2.length);
      System.arraycopy(var3, 0, var5, var4.length + var2.length, var3.length);
      byte[] var6 = new byte[0];

      for(int var7 = this.numLayer - 1 - 1; var7 >= 0; --var7) {
         var3 = this.gmssUtil.concatenateArray(this.currentAuthPaths[var7]);
         var4 = this.gmssUtil.intToBytesLittleEndian(this.index[var7]);
         byte[] var8 = new byte[var6.length];
         System.arraycopy(var6, 0, var8, 0, var6.length);
         var6 = new byte[var8.length + var4.length + this.subtreeRootSig[var7].length + var3.length];
         System.arraycopy(var8, 0, var6, 0, var8.length);
         System.arraycopy(var4, 0, var6, var8.length, var4.length);
         System.arraycopy(this.subtreeRootSig[var7], 0, var6, var8.length + var4.length, this.subtreeRootSig[var7].length);
         System.arraycopy(var3, 0, var6, var8.length + var4.length + this.subtreeRootSig[var7].length, var3.length);
      }

      byte[] var9 = new byte[var5.length + var6.length];
      System.arraycopy(var5, 0, var9, 0, var5.length);
      System.arraycopy(var6, 0, var9, var5.length, var6.length);
      return var9;
   }

   private void initVerify() {
      this.messDigestTrees.reset();
      GMSSPublicKeyParameters var1 = (GMSSPublicKeyParameters)this.key;
      this.pubKeyBytes = var1.getPublicKey();
      this.gmssPS = var1.getParameters();
      this.numLayer = this.gmssPS.getNumOfLayers();
   }

   public boolean verifySignature(byte[] var1, byte[] var2) {
      boolean var3 = false;
      this.messDigestOTS.reset();
      byte[] var4 = var1;
      int var5 = 0;

      for(int var6 = this.numLayer - 1; var6 >= 0; --var6) {
         WinternitzOTSVerify var7 = new WinternitzOTSVerify(this.digestProvider.get(), this.gmssPS.getWinternitzParameter()[var6]);
         int var8 = var7.getSignatureLength();
         int var9 = this.gmssUtil.bytesToIntLittleEndian(var2, var5);
         var5 += 4;
         byte[] var10 = new byte[var8];
         System.arraycopy(var2, var5, var10, 0, var8);
         var5 += var8;
         byte[] var11 = var7.Verify(var4, var10);
         if (var11 == null) {
            System.err.println("OTS Public Key is null in GMSSSignature.verify");
            return false;
         }

         byte[][] var12 = new byte[this.gmssPS.getHeightOfTrees()[var6]][this.mdLength];

         int var13;
         for(var13 = 0; var13 < var12.length; ++var13) {
            System.arraycopy(var2, var5, var12[var13], 0, this.mdLength);
            var5 += this.mdLength;
         }

         var4 = new byte[this.mdLength];
         var4 = var11;
         var13 = 1 << var12.length;
         var13 += var9;

         for(int var14 = 0; var14 < var12.length; ++var14) {
            byte[] var15 = new byte[this.mdLength << 1];
            if (var13 % 2 == 0) {
               System.arraycopy(var4, 0, var15, 0, this.mdLength);
               System.arraycopy(var12[var14], 0, var15, this.mdLength, this.mdLength);
               var13 /= 2;
            } else {
               System.arraycopy(var12[var14], 0, var15, 0, this.mdLength);
               System.arraycopy(var4, 0, var15, this.mdLength, var4.length);
               var13 = (var13 - 1) / 2;
            }

            this.messDigestTrees.update(var15, 0, var15.length);
            var4 = new byte[this.messDigestTrees.getDigestSize()];
            this.messDigestTrees.doFinal(var4, 0);
         }
      }

      if (Arrays.areEqual(this.pubKeyBytes, var4)) {
         var3 = true;
      }

      return var3;
   }
}
