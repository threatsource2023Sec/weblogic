package org.python.bouncycastle.pqc.crypto.gmss;

import java.util.Vector;
import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.python.bouncycastle.pqc.crypto.gmss.util.WinternitzOTSignature;
import org.python.bouncycastle.util.Arrays;

public class GMSSPrivateKeyParameters extends GMSSKeyParameters {
   private int[] index;
   private byte[][] currentSeeds;
   private byte[][] nextNextSeeds;
   private byte[][][] currentAuthPaths;
   private byte[][][] nextAuthPaths;
   private Treehash[][] currentTreehash;
   private Treehash[][] nextTreehash;
   private Vector[] currentStack;
   private Vector[] nextStack;
   private Vector[][] currentRetain;
   private Vector[][] nextRetain;
   private byte[][][] keep;
   private GMSSLeaf[] nextNextLeaf;
   private GMSSLeaf[] upperLeaf;
   private GMSSLeaf[] upperTreehashLeaf;
   private int[] minTreehash;
   private GMSSParameters gmssPS;
   private byte[][] nextRoot;
   private GMSSRootCalc[] nextNextRoot;
   private byte[][] currentRootSig;
   private GMSSRootSig[] nextRootSig;
   private GMSSDigestProvider digestProvider;
   private boolean used;
   private int[] heightOfTrees;
   private int[] otsIndex;
   private int[] K;
   private int numLayer;
   private Digest messDigestTrees;
   private int mdLength;
   private GMSSRandom gmssRandom;
   private int[] numLeafs;

   public GMSSPrivateKeyParameters(byte[][] var1, byte[][] var2, byte[][][] var3, byte[][][] var4, Treehash[][] var5, Treehash[][] var6, Vector[] var7, Vector[] var8, Vector[][] var9, Vector[][] var10, byte[][] var11, byte[][] var12, GMSSParameters var13, GMSSDigestProvider var14) {
      this((int[])null, var1, var2, var3, var4, (byte[][][])null, var5, var6, var7, var8, var9, var10, (GMSSLeaf[])null, (GMSSLeaf[])null, (GMSSLeaf[])null, (int[])null, var11, (GMSSRootCalc[])null, var12, (GMSSRootSig[])null, var13, var14);
   }

   public GMSSPrivateKeyParameters(int[] var1, byte[][] var2, byte[][] var3, byte[][][] var4, byte[][][] var5, byte[][][] var6, Treehash[][] var7, Treehash[][] var8, Vector[] var9, Vector[] var10, Vector[][] var11, Vector[][] var12, GMSSLeaf[] var13, GMSSLeaf[] var14, GMSSLeaf[] var15, int[] var16, byte[][] var17, GMSSRootCalc[] var18, byte[][] var19, GMSSRootSig[] var20, GMSSParameters var21, GMSSDigestProvider var22) {
      super(true, var21);
      this.used = false;
      this.messDigestTrees = var22.get();
      this.mdLength = this.messDigestTrees.getDigestSize();
      this.gmssPS = var21;
      this.otsIndex = var21.getWinternitzParameter();
      this.K = var21.getK();
      this.heightOfTrees = var21.getHeightOfTrees();
      this.numLayer = this.gmssPS.getNumOfLayers();
      int var23;
      if (var1 == null) {
         this.index = new int[this.numLayer];

         for(var23 = 0; var23 < this.numLayer; ++var23) {
            this.index[var23] = 0;
         }
      } else {
         this.index = var1;
      }

      this.currentSeeds = var2;
      this.nextNextSeeds = var3;
      this.currentAuthPaths = var4;
      this.nextAuthPaths = var5;
      if (var6 == null) {
         this.keep = new byte[this.numLayer][][];

         for(var23 = 0; var23 < this.numLayer; ++var23) {
            this.keep[var23] = new byte[(int)Math.floor((double)(this.heightOfTrees[var23] / 2))][this.mdLength];
         }
      } else {
         this.keep = var6;
      }

      if (var9 == null) {
         this.currentStack = new Vector[this.numLayer];

         for(var23 = 0; var23 < this.numLayer; ++var23) {
            this.currentStack[var23] = new Vector();
         }
      } else {
         this.currentStack = var9;
      }

      if (var10 == null) {
         this.nextStack = new Vector[this.numLayer - 1];

         for(var23 = 0; var23 < this.numLayer - 1; ++var23) {
            this.nextStack[var23] = new Vector();
         }
      } else {
         this.nextStack = var10;
      }

      this.currentTreehash = var7;
      this.nextTreehash = var8;
      this.currentRetain = var11;
      this.nextRetain = var12;
      this.nextRoot = var17;
      this.digestProvider = var22;
      if (var18 == null) {
         this.nextNextRoot = new GMSSRootCalc[this.numLayer - 1];

         for(var23 = 0; var23 < this.numLayer - 1; ++var23) {
            this.nextNextRoot[var23] = new GMSSRootCalc(this.heightOfTrees[var23 + 1], this.K[var23 + 1], this.digestProvider);
         }
      } else {
         this.nextNextRoot = var18;
      }

      this.currentRootSig = var19;
      this.numLeafs = new int[this.numLayer];

      for(var23 = 0; var23 < this.numLayer; ++var23) {
         this.numLeafs[var23] = 1 << this.heightOfTrees[var23];
      }

      this.gmssRandom = new GMSSRandom(this.messDigestTrees);
      if (this.numLayer > 1) {
         if (var13 == null) {
            this.nextNextLeaf = new GMSSLeaf[this.numLayer - 2];

            for(var23 = 0; var23 < this.numLayer - 2; ++var23) {
               this.nextNextLeaf[var23] = new GMSSLeaf(var22.get(), this.otsIndex[var23 + 1], this.numLeafs[var23 + 2], this.nextNextSeeds[var23]);
            }
         } else {
            this.nextNextLeaf = var13;
         }
      } else {
         this.nextNextLeaf = new GMSSLeaf[0];
      }

      if (var14 == null) {
         this.upperLeaf = new GMSSLeaf[this.numLayer - 1];

         for(var23 = 0; var23 < this.numLayer - 1; ++var23) {
            this.upperLeaf[var23] = new GMSSLeaf(var22.get(), this.otsIndex[var23], this.numLeafs[var23 + 1], this.currentSeeds[var23]);
         }
      } else {
         this.upperLeaf = var14;
      }

      if (var15 == null) {
         this.upperTreehashLeaf = new GMSSLeaf[this.numLayer - 1];

         for(var23 = 0; var23 < this.numLayer - 1; ++var23) {
            this.upperTreehashLeaf[var23] = new GMSSLeaf(var22.get(), this.otsIndex[var23], this.numLeafs[var23 + 1]);
         }
      } else {
         this.upperTreehashLeaf = var15;
      }

      if (var16 == null) {
         this.minTreehash = new int[this.numLayer - 1];

         for(var23 = 0; var23 < this.numLayer - 1; ++var23) {
            this.minTreehash[var23] = -1;
         }
      } else {
         this.minTreehash = var16;
      }

      byte[] var26 = new byte[this.mdLength];
      byte[] var24 = new byte[this.mdLength];
      if (var20 == null) {
         this.nextRootSig = new GMSSRootSig[this.numLayer - 1];

         for(int var25 = 0; var25 < this.numLayer - 1; ++var25) {
            System.arraycopy(var2[var25], 0, var26, 0, this.mdLength);
            this.gmssRandom.nextSeed(var26);
            var24 = this.gmssRandom.nextSeed(var26);
            this.nextRootSig[var25] = new GMSSRootSig(var22.get(), this.otsIndex[var25], this.heightOfTrees[var25 + 1]);
            this.nextRootSig[var25].initSign(var24, var17[var25]);
         }
      } else {
         this.nextRootSig = var20;
      }

   }

   private GMSSPrivateKeyParameters(GMSSPrivateKeyParameters var1) {
      super(true, var1.getParameters());
      this.used = false;
      this.index = Arrays.clone(var1.index);
      this.currentSeeds = Arrays.clone(var1.currentSeeds);
      this.nextNextSeeds = Arrays.clone(var1.nextNextSeeds);
      this.currentAuthPaths = Arrays.clone(var1.currentAuthPaths);
      this.nextAuthPaths = Arrays.clone(var1.nextAuthPaths);
      this.currentTreehash = var1.currentTreehash;
      this.nextTreehash = var1.nextTreehash;
      this.currentStack = var1.currentStack;
      this.nextStack = var1.nextStack;
      this.currentRetain = var1.currentRetain;
      this.nextRetain = var1.nextRetain;
      this.keep = Arrays.clone(var1.keep);
      this.nextNextLeaf = var1.nextNextLeaf;
      this.upperLeaf = var1.upperLeaf;
      this.upperTreehashLeaf = var1.upperTreehashLeaf;
      this.minTreehash = var1.minTreehash;
      this.gmssPS = var1.gmssPS;
      this.nextRoot = Arrays.clone(var1.nextRoot);
      this.nextNextRoot = var1.nextNextRoot;
      this.currentRootSig = var1.currentRootSig;
      this.nextRootSig = var1.nextRootSig;
      this.digestProvider = var1.digestProvider;
      this.heightOfTrees = var1.heightOfTrees;
      this.otsIndex = var1.otsIndex;
      this.K = var1.K;
      this.numLayer = var1.numLayer;
      this.messDigestTrees = var1.messDigestTrees;
      this.mdLength = var1.mdLength;
      this.gmssRandom = var1.gmssRandom;
      this.numLeafs = var1.numLeafs;
   }

   public boolean isUsed() {
      return this.used;
   }

   public void markUsed() {
      this.used = true;
   }

   public GMSSPrivateKeyParameters nextKey() {
      GMSSPrivateKeyParameters var1 = new GMSSPrivateKeyParameters(this);
      var1.nextKey(this.gmssPS.getNumOfLayers() - 1);
      return var1;
   }

   private void nextKey(int var1) {
      if (var1 == this.numLayer - 1) {
         int var10002 = this.index[var1]++;
      }

      if (this.index[var1] == this.numLeafs[var1]) {
         if (this.numLayer != 1) {
            this.nextTree(var1);
            this.index[var1] = 0;
         }
      } else {
         this.updateKey(var1);
      }

   }

   private void nextTree(int var1) {
      if (var1 > 0) {
         int var10002 = this.index[var1 - 1]++;
         boolean var2 = true;
         int var3 = var1;

         do {
            --var3;
            if (this.index[var3] < this.numLeafs[var3]) {
               var2 = false;
            }
         } while(var2 && var3 > 0);

         if (!var2) {
            this.gmssRandom.nextSeed(this.currentSeeds[var1]);
            this.nextRootSig[var1 - 1].updateSign();
            if (var1 > 1) {
               this.nextNextLeaf[var1 - 1 - 1] = this.nextNextLeaf[var1 - 1 - 1].nextLeaf();
            }

            this.upperLeaf[var1 - 1] = this.upperLeaf[var1 - 1].nextLeaf();
            byte[] var4;
            if (this.minTreehash[var1 - 1] >= 0) {
               this.upperTreehashLeaf[var1 - 1] = this.upperTreehashLeaf[var1 - 1].nextLeaf();
               var4 = this.upperTreehashLeaf[var1 - 1].getLeaf();

               try {
                  this.currentTreehash[var1 - 1][this.minTreehash[var1 - 1]].update(this.gmssRandom, var4);
                  if (this.currentTreehash[var1 - 1][this.minTreehash[var1 - 1]].wasFinished()) {
                  }
               } catch (Exception var6) {
                  System.out.println(var6);
               }
            }

            this.updateNextNextAuthRoot(var1);
            this.currentRootSig[var1 - 1] = this.nextRootSig[var1 - 1].getSig();

            int var7;
            for(var7 = 0; var7 < this.heightOfTrees[var1] - this.K[var1]; ++var7) {
               this.currentTreehash[var1][var7] = this.nextTreehash[var1 - 1][var7];
               this.nextTreehash[var1 - 1][var7] = this.nextNextRoot[var1 - 1].getTreehash()[var7];
            }

            for(var7 = 0; var7 < this.heightOfTrees[var1]; ++var7) {
               System.arraycopy(this.nextAuthPaths[var1 - 1][var7], 0, this.currentAuthPaths[var1][var7], 0, this.mdLength);
               System.arraycopy(this.nextNextRoot[var1 - 1].getAuthPath()[var7], 0, this.nextAuthPaths[var1 - 1][var7], 0, this.mdLength);
            }

            for(var7 = 0; var7 < this.K[var1] - 1; ++var7) {
               this.currentRetain[var1][var7] = this.nextRetain[var1 - 1][var7];
               this.nextRetain[var1 - 1][var7] = this.nextNextRoot[var1 - 1].getRetain()[var7];
            }

            this.currentStack[var1] = this.nextStack[var1 - 1];
            this.nextStack[var1 - 1] = this.nextNextRoot[var1 - 1].getStack();
            this.nextRoot[var1 - 1] = this.nextNextRoot[var1 - 1].getRoot();
            var4 = new byte[this.mdLength];
            byte[] var5 = new byte[this.mdLength];
            System.arraycopy(this.currentSeeds[var1 - 1], 0, var5, 0, this.mdLength);
            this.gmssRandom.nextSeed(var5);
            this.gmssRandom.nextSeed(var5);
            var4 = this.gmssRandom.nextSeed(var5);
            this.nextRootSig[var1 - 1].initSign(var4, this.nextRoot[var1 - 1]);
            this.nextKey(var1 - 1);
         }
      }

   }

   private void updateKey(int var1) {
      this.computeAuthPaths(var1);
      if (var1 > 0) {
         if (var1 > 1) {
            this.nextNextLeaf[var1 - 1 - 1] = this.nextNextLeaf[var1 - 1 - 1].nextLeaf();
         }

         this.upperLeaf[var1 - 1] = this.upperLeaf[var1 - 1].nextLeaf();
         int var2 = (int)Math.floor((double)(this.getNumLeafs(var1) * 2) / (double)(this.heightOfTrees[var1 - 1] - this.K[var1 - 1]));
         if (this.index[var1] % var2 == 1) {
            byte[] var3;
            if (this.index[var1] > 1 && this.minTreehash[var1 - 1] >= 0) {
               var3 = this.upperTreehashLeaf[var1 - 1].getLeaf();

               try {
                  this.currentTreehash[var1 - 1][this.minTreehash[var1 - 1]].update(this.gmssRandom, var3);
                  if (this.currentTreehash[var1 - 1][this.minTreehash[var1 - 1]].wasFinished()) {
                  }
               } catch (Exception var5) {
                  System.out.println(var5);
               }
            }

            this.minTreehash[var1 - 1] = this.getMinTreehashIndex(var1 - 1);
            if (this.minTreehash[var1 - 1] >= 0) {
               var3 = this.currentTreehash[var1 - 1][this.minTreehash[var1 - 1]].getSeedActive();
               this.upperTreehashLeaf[var1 - 1] = new GMSSLeaf(this.digestProvider.get(), this.otsIndex[var1 - 1], var2, var3);
               this.upperTreehashLeaf[var1 - 1] = this.upperTreehashLeaf[var1 - 1].nextLeaf();
            }
         } else if (this.minTreehash[var1 - 1] >= 0) {
            this.upperTreehashLeaf[var1 - 1] = this.upperTreehashLeaf[var1 - 1].nextLeaf();
         }

         this.nextRootSig[var1 - 1].updateSign();
         if (this.index[var1] == 1) {
            this.nextNextRoot[var1 - 1].initialize(new Vector());
         }

         this.updateNextNextAuthRoot(var1);
      }

   }

   private int getMinTreehashIndex(int var1) {
      int var2 = -1;

      for(int var3 = 0; var3 < this.heightOfTrees[var1] - this.K[var1]; ++var3) {
         if (this.currentTreehash[var1][var3].wasInitialized() && !this.currentTreehash[var1][var3].wasFinished()) {
            if (var2 == -1) {
               var2 = var3;
            } else if (this.currentTreehash[var1][var3].getLowestNodeHeight() < this.currentTreehash[var1][var2].getLowestNodeHeight()) {
               var2 = var3;
            }
         }
      }

      return var2;
   }

   private void computeAuthPaths(int var1) {
      int var2 = this.index[var1];
      int var3 = this.heightOfTrees[var1];
      int var4 = this.K[var1];

      int var5;
      for(var5 = 0; var5 < var3 - var4; ++var5) {
         this.currentTreehash[var1][var5].updateNextSeed(this.gmssRandom);
      }

      var5 = this.heightOfPhi(var2);
      byte[] var6 = new byte[this.mdLength];
      var6 = this.gmssRandom.nextSeed(this.currentSeeds[var1]);
      int var7 = var2 >>> var5 + 1 & 1;
      byte[] var8 = new byte[this.mdLength];
      if (var5 < var3 - 1 && var7 == 0) {
         System.arraycopy(this.currentAuthPaths[var1][var5], 0, var8, 0, this.mdLength);
      }

      byte[] var9 = new byte[this.mdLength];
      int var11;
      byte[] var17;
      if (var5 == 0) {
         if (var1 == this.numLayer - 1) {
            WinternitzOTSignature var10 = new WinternitzOTSignature(var6, this.digestProvider.get(), this.otsIndex[var1]);
            var9 = var10.getPublicKey();
         } else {
            var17 = new byte[this.mdLength];
            System.arraycopy(this.currentSeeds[var1], 0, var17, 0, this.mdLength);
            this.gmssRandom.nextSeed(var17);
            var9 = this.upperLeaf[var1].getLeaf();
            this.upperLeaf[var1].initLeafCalc(var17);
         }

         System.arraycopy(var9, 0, this.currentAuthPaths[var1][0], 0, this.mdLength);
      } else {
         var17 = new byte[this.mdLength << 1];
         System.arraycopy(this.currentAuthPaths[var1][var5 - 1], 0, var17, 0, this.mdLength);
         System.arraycopy(this.keep[var1][(int)Math.floor((double)((var5 - 1) / 2))], 0, var17, this.mdLength, this.mdLength);
         this.messDigestTrees.update(var17, 0, var17.length);
         this.currentAuthPaths[var1][var5] = new byte[this.messDigestTrees.getDigestSize()];
         this.messDigestTrees.doFinal(this.currentAuthPaths[var1][var5], 0);

         for(var11 = 0; var11 < var5; ++var11) {
            if (var11 < var3 - var4) {
               if (this.currentTreehash[var1][var11].wasFinished()) {
                  System.arraycopy(this.currentTreehash[var1][var11].getFirstNode(), 0, this.currentAuthPaths[var1][var11], 0, this.mdLength);
                  this.currentTreehash[var1][var11].destroy();
               } else {
                  System.err.println("Treehash (" + var1 + "," + var11 + ") not finished when needed in AuthPathComputation");
               }
            }

            if (var11 < var3 - 1 && var11 >= var3 - var4 && this.currentRetain[var1][var11 - (var3 - var4)].size() > 0) {
               System.arraycopy(this.currentRetain[var1][var11 - (var3 - var4)].lastElement(), 0, this.currentAuthPaths[var1][var11], 0, this.mdLength);
               this.currentRetain[var1][var11 - (var3 - var4)].removeElementAt(this.currentRetain[var1][var11 - (var3 - var4)].size() - 1);
            }

            if (var11 < var3 - var4) {
               int var12 = var2 + 3 * (1 << var11);
               if (var12 < this.numLeafs[var1]) {
                  this.currentTreehash[var1][var11].initialize();
               }
            }
         }
      }

      if (var5 < var3 - 1 && var7 == 0) {
         System.arraycopy(var8, 0, this.keep[var1][(int)Math.floor((double)(var5 / 2))], 0, this.mdLength);
      }

      if (var1 == this.numLayer - 1) {
         for(int var18 = 1; var18 <= (var3 - var4) / 2; ++var18) {
            var11 = this.getMinTreehashIndex(var1);
            if (var11 >= 0) {
               try {
                  byte[] var19 = new byte[this.mdLength];
                  System.arraycopy(this.currentTreehash[var1][var11].getSeedActive(), 0, var19, 0, this.mdLength);
                  byte[] var13 = this.gmssRandom.nextSeed(var19);
                  WinternitzOTSignature var14 = new WinternitzOTSignature(var13, this.digestProvider.get(), this.otsIndex[var1]);
                  byte[] var15 = var14.getPublicKey();
                  this.currentTreehash[var1][var11].update(this.gmssRandom, var15);
               } catch (Exception var16) {
                  System.out.println(var16);
               }
            }
         }
      } else {
         this.minTreehash[var1] = this.getMinTreehashIndex(var1);
      }

   }

   private int heightOfPhi(int var1) {
      if (var1 == 0) {
         return -1;
      } else {
         int var2 = 0;

         for(int var3 = 1; var1 % var3 == 0; ++var2) {
            var3 *= 2;
         }

         return var2 - 1;
      }
   }

   private void updateNextNextAuthRoot(int var1) {
      byte[] var2 = new byte[this.mdLength];
      var2 = this.gmssRandom.nextSeed(this.nextNextSeeds[var1 - 1]);
      if (var1 == this.numLayer - 1) {
         WinternitzOTSignature var3 = new WinternitzOTSignature(var2, this.digestProvider.get(), this.otsIndex[var1]);
         this.nextNextRoot[var1 - 1].update(this.nextNextSeeds[var1 - 1], var3.getPublicKey());
      } else {
         this.nextNextRoot[var1 - 1].update(this.nextNextSeeds[var1 - 1], this.nextNextLeaf[var1 - 1].getLeaf());
         this.nextNextLeaf[var1 - 1].initLeafCalc(this.nextNextSeeds[var1 - 1]);
      }

   }

   public int[] getIndex() {
      return this.index;
   }

   public int getIndex(int var1) {
      return this.index[var1];
   }

   public byte[][] getCurrentSeeds() {
      return Arrays.clone(this.currentSeeds);
   }

   public byte[][][] getCurrentAuthPaths() {
      return Arrays.clone(this.currentAuthPaths);
   }

   public byte[] getSubtreeRootSig(int var1) {
      return this.currentRootSig[var1];
   }

   public GMSSDigestProvider getName() {
      return this.digestProvider;
   }

   public int getNumLeafs(int var1) {
      return this.numLeafs[var1];
   }
}
