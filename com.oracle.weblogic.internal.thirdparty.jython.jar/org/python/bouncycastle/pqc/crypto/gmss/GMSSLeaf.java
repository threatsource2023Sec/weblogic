package org.python.bouncycastle.pqc.crypto.gmss;

import org.python.bouncycastle.crypto.Digest;
import org.python.bouncycastle.pqc.crypto.gmss.util.GMSSRandom;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.encoders.Hex;

public class GMSSLeaf {
   private Digest messDigestOTS;
   private int mdsize;
   private int keysize;
   private GMSSRandom gmssRandom;
   private byte[] leaf;
   private byte[] concHashs;
   private int i;
   private int j;
   private int two_power_w;
   private int w;
   private int steps;
   private byte[] seed;
   byte[] privateKeyOTS;

   public GMSSLeaf(Digest var1, byte[][] var2, int[] var3) {
      this.i = var3[0];
      this.j = var3[1];
      this.steps = var3[2];
      this.w = var3[3];
      this.messDigestOTS = var1;
      this.gmssRandom = new GMSSRandom(this.messDigestOTS);
      this.mdsize = this.messDigestOTS.getDigestSize();
      int var4 = this.mdsize << 3;
      int var5 = (int)Math.ceil((double)var4 / (double)this.w);
      int var6 = this.getLog((var5 << this.w) + 1);
      this.keysize = var5 + (int)Math.ceil((double)var6 / (double)this.w);
      this.two_power_w = 1 << this.w;
      this.privateKeyOTS = var2[0];
      this.seed = var2[1];
      this.concHashs = var2[2];
      this.leaf = var2[3];
   }

   GMSSLeaf(Digest var1, int var2, int var3) {
      this.w = var2;
      this.messDigestOTS = var1;
      this.gmssRandom = new GMSSRandom(this.messDigestOTS);
      this.mdsize = this.messDigestOTS.getDigestSize();
      int var4 = this.mdsize << 3;
      int var5 = (int)Math.ceil((double)var4 / (double)var2);
      int var6 = this.getLog((var5 << var2) + 1);
      this.keysize = var5 + (int)Math.ceil((double)var6 / (double)var2);
      this.two_power_w = 1 << var2;
      this.steps = (int)Math.ceil((double)(((1 << var2) - 1) * this.keysize + 1 + this.keysize) / (double)var3);
      this.seed = new byte[this.mdsize];
      this.leaf = new byte[this.mdsize];
      this.privateKeyOTS = new byte[this.mdsize];
      this.concHashs = new byte[this.mdsize * this.keysize];
   }

   public GMSSLeaf(Digest var1, int var2, int var3, byte[] var4) {
      this.w = var2;
      this.messDigestOTS = var1;
      this.gmssRandom = new GMSSRandom(this.messDigestOTS);
      this.mdsize = this.messDigestOTS.getDigestSize();
      int var5 = this.mdsize << 3;
      int var6 = (int)Math.ceil((double)var5 / (double)var2);
      int var7 = this.getLog((var6 << var2) + 1);
      this.keysize = var6 + (int)Math.ceil((double)var7 / (double)var2);
      this.two_power_w = 1 << var2;
      this.steps = (int)Math.ceil((double)(((1 << var2) - 1) * this.keysize + 1 + this.keysize) / (double)var3);
      this.seed = new byte[this.mdsize];
      this.leaf = new byte[this.mdsize];
      this.privateKeyOTS = new byte[this.mdsize];
      this.concHashs = new byte[this.mdsize * this.keysize];
      this.initLeafCalc(var4);
   }

   private GMSSLeaf(GMSSLeaf var1) {
      this.messDigestOTS = var1.messDigestOTS;
      this.mdsize = var1.mdsize;
      this.keysize = var1.keysize;
      this.gmssRandom = var1.gmssRandom;
      this.leaf = Arrays.clone(var1.leaf);
      this.concHashs = Arrays.clone(var1.concHashs);
      this.i = var1.i;
      this.j = var1.j;
      this.two_power_w = var1.two_power_w;
      this.w = var1.w;
      this.steps = var1.steps;
      this.seed = Arrays.clone(var1.seed);
      this.privateKeyOTS = Arrays.clone(var1.privateKeyOTS);
   }

   void initLeafCalc(byte[] var1) {
      this.i = 0;
      this.j = 0;
      byte[] var2 = new byte[this.mdsize];
      System.arraycopy(var1, 0, var2, 0, this.seed.length);
      this.seed = this.gmssRandom.nextSeed(var2);
   }

   GMSSLeaf nextLeaf() {
      GMSSLeaf var1 = new GMSSLeaf(this);
      var1.updateLeafCalc();
      return var1;
   }

   private void updateLeafCalc() {
      byte[] var1 = new byte[this.messDigestOTS.getDigestSize()];

      for(int var2 = 0; var2 < this.steps + 10000; ++var2) {
         if (this.i == this.keysize && this.j == this.two_power_w - 1) {
            this.messDigestOTS.update(this.concHashs, 0, this.concHashs.length);
            this.leaf = new byte[this.messDigestOTS.getDigestSize()];
            this.messDigestOTS.doFinal(this.leaf, 0);
            return;
         }

         if (this.i != 0 && this.j != this.two_power_w - 1) {
            this.messDigestOTS.update(this.privateKeyOTS, 0, this.privateKeyOTS.length);
            this.privateKeyOTS = var1;
            this.messDigestOTS.doFinal(this.privateKeyOTS, 0);
            ++this.j;
            if (this.j == this.two_power_w - 1) {
               System.arraycopy(this.privateKeyOTS, 0, this.concHashs, this.mdsize * (this.i - 1), this.mdsize);
            }
         } else {
            ++this.i;
            this.j = 0;
            this.privateKeyOTS = this.gmssRandom.nextSeed(this.seed);
         }
      }

      throw new IllegalStateException("unable to updateLeaf in steps: " + this.steps + " " + this.i + " " + this.j);
   }

   public byte[] getLeaf() {
      return Arrays.clone(this.leaf);
   }

   private int getLog(int var1) {
      int var2 = 1;

      for(int var3 = 2; var3 < var1; ++var2) {
         var3 <<= 1;
      }

      return var2;
   }

   public byte[][] getStatByte() {
      byte[][] var1 = new byte[][]{new byte[this.mdsize], new byte[this.mdsize], new byte[this.mdsize * this.keysize], new byte[this.mdsize]};
      var1[0] = this.privateKeyOTS;
      var1[1] = this.seed;
      var1[2] = this.concHashs;
      var1[3] = this.leaf;
      return var1;
   }

   public int[] getStatInt() {
      int[] var1 = new int[]{this.i, this.j, this.steps, this.w};
      return var1;
   }

   public String toString() {
      String var1 = "";

      for(int var2 = 0; var2 < 4; ++var2) {
         var1 = var1 + this.getStatInt()[var2] + " ";
      }

      var1 = var1 + " " + this.mdsize + " " + this.keysize + " " + this.two_power_w + " ";
      byte[][] var4 = this.getStatByte();

      for(int var3 = 0; var3 < 4; ++var3) {
         if (var4[var3] != null) {
            var1 = var1 + new String(Hex.encode(var4[var3])) + " ";
         } else {
            var1 = var1 + "null ";
         }
      }

      return var1;
   }
}
