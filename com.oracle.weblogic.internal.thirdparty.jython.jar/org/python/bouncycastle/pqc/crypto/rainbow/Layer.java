package org.python.bouncycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.python.bouncycastle.pqc.crypto.rainbow.util.GF2Field;
import org.python.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.python.bouncycastle.util.Arrays;

public class Layer {
   private int vi;
   private int viNext;
   private int oi;
   private short[][][] coeff_alpha;
   private short[][][] coeff_beta;
   private short[][] coeff_gamma;
   private short[] coeff_eta;

   public Layer(byte var1, byte var2, short[][][] var3, short[][][] var4, short[][] var5, short[] var6) {
      this.vi = var1 & 255;
      this.viNext = var2 & 255;
      this.oi = this.viNext - this.vi;
      this.coeff_alpha = var3;
      this.coeff_beta = var4;
      this.coeff_gamma = var5;
      this.coeff_eta = var6;
   }

   public Layer(int var1, int var2, SecureRandom var3) {
      this.vi = var1;
      this.viNext = var2;
      this.oi = var2 - var1;
      this.coeff_alpha = new short[this.oi][this.oi][this.vi];
      this.coeff_beta = new short[this.oi][this.vi][this.vi];
      this.coeff_gamma = new short[this.oi][this.viNext];
      this.coeff_eta = new short[this.oi];
      int var4 = this.oi;

      int var5;
      int var6;
      int var7;
      for(var5 = 0; var5 < var4; ++var5) {
         for(var6 = 0; var6 < this.oi; ++var6) {
            for(var7 = 0; var7 < this.vi; ++var7) {
               this.coeff_alpha[var5][var6][var7] = (short)(var3.nextInt() & 255);
            }
         }
      }

      for(var5 = 0; var5 < var4; ++var5) {
         for(var6 = 0; var6 < this.vi; ++var6) {
            for(var7 = 0; var7 < this.vi; ++var7) {
               this.coeff_beta[var5][var6][var7] = (short)(var3.nextInt() & 255);
            }
         }
      }

      for(var5 = 0; var5 < var4; ++var5) {
         for(var6 = 0; var6 < this.viNext; ++var6) {
            this.coeff_gamma[var5][var6] = (short)(var3.nextInt() & 255);
         }
      }

      for(var5 = 0; var5 < var4; ++var5) {
         this.coeff_eta[var5] = (short)(var3.nextInt() & 255);
      }

   }

   public short[][] plugInVinegars(short[] var1) {
      boolean var2 = false;
      short[][] var3 = new short[this.oi][this.oi + 1];
      short[] var4 = new short[this.oi];

      int var5;
      int var6;
      int var7;
      short var8;
      for(var5 = 0; var5 < this.oi; ++var5) {
         for(var6 = 0; var6 < this.vi; ++var6) {
            for(var7 = 0; var7 < this.vi; ++var7) {
               var8 = GF2Field.multElem(this.coeff_beta[var5][var6][var7], var1[var6]);
               var8 = GF2Field.multElem(var8, var1[var7]);
               var4[var5] = GF2Field.addElem(var4[var5], var8);
            }
         }
      }

      for(var5 = 0; var5 < this.oi; ++var5) {
         for(var6 = 0; var6 < this.oi; ++var6) {
            for(var7 = 0; var7 < this.vi; ++var7) {
               var8 = GF2Field.multElem(this.coeff_alpha[var5][var6][var7], var1[var7]);
               var3[var5][var6] = GF2Field.addElem(var3[var5][var6], var8);
            }
         }
      }

      for(var5 = 0; var5 < this.oi; ++var5) {
         for(var6 = 0; var6 < this.vi; ++var6) {
            var8 = GF2Field.multElem(this.coeff_gamma[var5][var6], var1[var6]);
            var4[var5] = GF2Field.addElem(var4[var5], var8);
         }
      }

      for(var5 = 0; var5 < this.oi; ++var5) {
         for(var6 = this.vi; var6 < this.viNext; ++var6) {
            var3[var5][var6 - this.vi] = GF2Field.addElem(this.coeff_gamma[var5][var6], var3[var5][var6 - this.vi]);
         }
      }

      for(var5 = 0; var5 < this.oi; ++var5) {
         var4[var5] = GF2Field.addElem(var4[var5], this.coeff_eta[var5]);
      }

      for(var5 = 0; var5 < this.oi; ++var5) {
         var3[var5][this.oi] = var4[var5];
      }

      return var3;
   }

   public int getVi() {
      return this.vi;
   }

   public int getViNext() {
      return this.viNext;
   }

   public int getOi() {
      return this.oi;
   }

   public short[][][] getCoeffAlpha() {
      return this.coeff_alpha;
   }

   public short[][][] getCoeffBeta() {
      return this.coeff_beta;
   }

   public short[][] getCoeffGamma() {
      return this.coeff_gamma;
   }

   public short[] getCoeffEta() {
      return this.coeff_eta;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof Layer) {
         Layer var2 = (Layer)var1;
         return this.vi == var2.getVi() && this.viNext == var2.getViNext() && this.oi == var2.getOi() && RainbowUtil.equals(this.coeff_alpha, var2.getCoeffAlpha()) && RainbowUtil.equals(this.coeff_beta, var2.getCoeffBeta()) && RainbowUtil.equals(this.coeff_gamma, var2.getCoeffGamma()) && RainbowUtil.equals(this.coeff_eta, var2.getCoeffEta());
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.vi;
      var1 = var1 * 37 + this.viNext;
      var1 = var1 * 37 + this.oi;
      var1 = var1 * 37 + Arrays.hashCode(this.coeff_alpha);
      var1 = var1 * 37 + Arrays.hashCode(this.coeff_beta);
      var1 = var1 * 37 + Arrays.hashCode(this.coeff_gamma);
      var1 = var1 * 37 + Arrays.hashCode(this.coeff_eta);
      return var1;
   }
}
