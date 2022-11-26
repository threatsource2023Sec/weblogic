package org.python.bouncycastle.pqc.crypto.rainbow;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.util.ComputeInField;
import org.python.bouncycastle.pqc.crypto.rainbow.util.GF2Field;

public class RainbowKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private boolean initialized = false;
   private SecureRandom sr;
   private RainbowKeyGenerationParameters rainbowParams;
   private short[][] A1;
   private short[][] A1inv;
   private short[] b1;
   private short[][] A2;
   private short[][] A2inv;
   private short[] b2;
   private int numOfLayers;
   private Layer[] layers;
   private int[] vi;
   private short[][] pub_quadratic;
   private short[][] pub_singular;
   private short[] pub_scalar;

   public AsymmetricCipherKeyPair genKeyPair() {
      if (!this.initialized) {
         this.initializeDefault();
      }

      this.keygen();
      RainbowPrivateKeyParameters var1 = new RainbowPrivateKeyParameters(this.A1inv, this.b1, this.A2inv, this.b2, this.vi, this.layers);
      RainbowPublicKeyParameters var2 = new RainbowPublicKeyParameters(this.vi[this.vi.length - 1] - this.vi[0], this.pub_quadratic, this.pub_singular, this.pub_scalar);
      return new AsymmetricCipherKeyPair(var2, var1);
   }

   public void initialize(KeyGenerationParameters var1) {
      this.rainbowParams = (RainbowKeyGenerationParameters)var1;
      this.sr = new SecureRandom();
      this.vi = this.rainbowParams.getParameters().getVi();
      this.numOfLayers = this.rainbowParams.getParameters().getNumOfLayers();
      this.initialized = true;
   }

   private void initializeDefault() {
      RainbowKeyGenerationParameters var1 = new RainbowKeyGenerationParameters(new SecureRandom(), new RainbowParameters());
      this.initialize(var1);
   }

   private void keygen() {
      this.generateL1();
      this.generateL2();
      this.generateF();
      this.computePublicKey();
   }

   private void generateL1() {
      int var1 = this.vi[this.vi.length - 1] - this.vi[0];
      this.A1 = new short[var1][var1];
      this.A1inv = (short[][])null;

      int var3;
      for(ComputeInField var2 = new ComputeInField(); this.A1inv == null; this.A1inv = var2.inverse(this.A1)) {
         for(var3 = 0; var3 < var1; ++var3) {
            for(int var4 = 0; var4 < var1; ++var4) {
               this.A1[var3][var4] = (short)(this.sr.nextInt() & 255);
            }
         }
      }

      this.b1 = new short[var1];

      for(var3 = 0; var3 < var1; ++var3) {
         this.b1[var3] = (short)(this.sr.nextInt() & 255);
      }

   }

   private void generateL2() {
      int var1 = this.vi[this.vi.length - 1];
      this.A2 = new short[var1][var1];
      this.A2inv = (short[][])null;

      int var3;
      for(ComputeInField var2 = new ComputeInField(); this.A2inv == null; this.A2inv = var2.inverse(this.A2)) {
         for(var3 = 0; var3 < var1; ++var3) {
            for(int var4 = 0; var4 < var1; ++var4) {
               this.A2[var3][var4] = (short)(this.sr.nextInt() & 255);
            }
         }
      }

      this.b2 = new short[var1];

      for(var3 = 0; var3 < var1; ++var3) {
         this.b2[var3] = (short)(this.sr.nextInt() & 255);
      }

   }

   private void generateF() {
      this.layers = new Layer[this.numOfLayers];

      for(int var1 = 0; var1 < this.numOfLayers; ++var1) {
         this.layers[var1] = new Layer(this.vi[var1], this.vi[var1 + 1], this.sr);
      }

   }

   private void computePublicKey() {
      ComputeInField var1 = new ComputeInField();
      int var2 = this.vi[this.vi.length - 1] - this.vi[0];
      int var3 = this.vi[this.vi.length - 1];
      short[][][] var4 = new short[var2][var3][var3];
      this.pub_singular = new short[var2][var3];
      this.pub_scalar = new short[var2];
      boolean var5 = false;
      boolean var6 = false;
      int var7 = 0;
      short[] var8 = new short[var3];
      boolean var9 = false;

      int var17;
      for(int var10 = 0; var10 < this.layers.length; ++var10) {
         short[][][] var11 = this.layers[var10].getCoeffAlpha();
         short[][][] var12 = this.layers[var10].getCoeffBeta();
         short[][] var13 = this.layers[var10].getCoeffGamma();
         short[] var14 = this.layers[var10].getCoeffEta();
         int var19 = var11[0].length;
         int var20 = var12[0].length;

         for(int var15 = 0; var15 < var19; ++var15) {
            int var16;
            short var21;
            for(var16 = 0; var16 < var19; ++var16) {
               for(var17 = 0; var17 < var20; ++var17) {
                  var8 = var1.multVect(var11[var15][var16][var17], this.A2[var16 + var20]);
                  var4[var7 + var15] = var1.addSquareMatrix(var4[var7 + var15], var1.multVects(var8, this.A2[var17]));
                  var8 = var1.multVect(this.b2[var17], var8);
                  this.pub_singular[var7 + var15] = var1.addVect(var8, this.pub_singular[var7 + var15]);
                  var8 = var1.multVect(var11[var15][var16][var17], this.A2[var17]);
                  var8 = var1.multVect(this.b2[var16 + var20], var8);
                  this.pub_singular[var7 + var15] = var1.addVect(var8, this.pub_singular[var7 + var15]);
                  var21 = GF2Field.multElem(var11[var15][var16][var17], this.b2[var16 + var20]);
                  this.pub_scalar[var7 + var15] = GF2Field.addElem(this.pub_scalar[var7 + var15], GF2Field.multElem(var21, this.b2[var17]));
               }
            }

            for(var16 = 0; var16 < var20; ++var16) {
               for(var17 = 0; var17 < var20; ++var17) {
                  var8 = var1.multVect(var12[var15][var16][var17], this.A2[var16]);
                  var4[var7 + var15] = var1.addSquareMatrix(var4[var7 + var15], var1.multVects(var8, this.A2[var17]));
                  var8 = var1.multVect(this.b2[var17], var8);
                  this.pub_singular[var7 + var15] = var1.addVect(var8, this.pub_singular[var7 + var15]);
                  var8 = var1.multVect(var12[var15][var16][var17], this.A2[var17]);
                  var8 = var1.multVect(this.b2[var16], var8);
                  this.pub_singular[var7 + var15] = var1.addVect(var8, this.pub_singular[var7 + var15]);
                  var21 = GF2Field.multElem(var12[var15][var16][var17], this.b2[var16]);
                  this.pub_scalar[var7 + var15] = GF2Field.addElem(this.pub_scalar[var7 + var15], GF2Field.multElem(var21, this.b2[var17]));
               }
            }

            for(var16 = 0; var16 < var20 + var19; ++var16) {
               var8 = var1.multVect(var13[var15][var16], this.A2[var16]);
               this.pub_singular[var7 + var15] = var1.addVect(var8, this.pub_singular[var7 + var15]);
               this.pub_scalar[var7 + var15] = GF2Field.addElem(this.pub_scalar[var7 + var15], GF2Field.multElem(var13[var15][var16], this.b2[var16]));
            }

            this.pub_scalar[var7 + var15] = GF2Field.addElem(this.pub_scalar[var7 + var15], var14[var15]);
         }

         var7 += var19;
      }

      short[][][] var22 = new short[var2][var3][var3];
      short[][] var23 = new short[var2][var3];
      short[] var24 = new short[var2];

      for(var17 = 0; var17 < var2; ++var17) {
         for(int var18 = 0; var18 < this.A1.length; ++var18) {
            var22[var17] = var1.addSquareMatrix(var22[var17], var1.multMatrix(this.A1[var17][var18], var4[var18]));
            var23[var17] = var1.addVect(var23[var17], var1.multVect(this.A1[var17][var18], this.pub_singular[var18]));
            var24[var17] = GF2Field.addElem(var24[var17], GF2Field.multElem(this.A1[var17][var18], this.pub_scalar[var18]));
         }

         var24[var17] = GF2Field.addElem(var24[var17], this.b1[var17]);
      }

      this.pub_singular = var23;
      this.pub_scalar = var24;
      this.compactPublicKey(var22);
   }

   private void compactPublicKey(short[][][] var1) {
      int var2 = var1.length;
      int var3 = var1[0].length;
      int var4 = var3 * (var3 + 1) / 2;
      this.pub_quadratic = new short[var2][var4];
      boolean var5 = false;

      for(int var6 = 0; var6 < var2; ++var6) {
         int var9 = 0;

         for(int var7 = 0; var7 < var3; ++var7) {
            for(int var8 = var7; var8 < var3; ++var8) {
               if (var8 == var7) {
                  this.pub_quadratic[var6][var9] = var1[var6][var7][var8];
               } else {
                  this.pub_quadratic[var6][var9] = GF2Field.addElem(var1[var6][var7][var8], var1[var6][var8][var7]);
               }

               ++var9;
            }
         }
      }

   }

   public void init(KeyGenerationParameters var1) {
      this.initialize(var1);
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      return this.genKeyPair();
   }
}
