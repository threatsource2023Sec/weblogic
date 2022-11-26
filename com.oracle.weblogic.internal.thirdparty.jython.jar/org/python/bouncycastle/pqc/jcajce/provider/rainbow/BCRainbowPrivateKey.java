package org.python.bouncycastle.pqc.jcajce.provider.rainbow;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Arrays;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.asn1.RainbowPrivateKey;
import org.python.bouncycastle.pqc.crypto.rainbow.Layer;
import org.python.bouncycastle.pqc.crypto.rainbow.RainbowPrivateKeyParameters;
import org.python.bouncycastle.pqc.crypto.rainbow.util.RainbowUtil;
import org.python.bouncycastle.pqc.jcajce.spec.RainbowPrivateKeySpec;

public class BCRainbowPrivateKey implements PrivateKey {
   private static final long serialVersionUID = 1L;
   private short[][] A1inv;
   private short[] b1;
   private short[][] A2inv;
   private short[] b2;
   private Layer[] layers;
   private int[] vi;

   public BCRainbowPrivateKey(short[][] var1, short[] var2, short[][] var3, short[] var4, int[] var5, Layer[] var6) {
      this.A1inv = var1;
      this.b1 = var2;
      this.A2inv = var3;
      this.b2 = var4;
      this.vi = var5;
      this.layers = var6;
   }

   public BCRainbowPrivateKey(RainbowPrivateKeySpec var1) {
      this(var1.getInvA1(), var1.getB1(), var1.getInvA2(), var1.getB2(), var1.getVi(), var1.getLayers());
   }

   public BCRainbowPrivateKey(RainbowPrivateKeyParameters var1) {
      this(var1.getInvA1(), var1.getB1(), var1.getInvA2(), var1.getB2(), var1.getVi(), var1.getLayers());
   }

   public short[][] getInvA1() {
      return this.A1inv;
   }

   public short[] getB1() {
      return this.b1;
   }

   public short[] getB2() {
      return this.b2;
   }

   public short[][] getInvA2() {
      return this.A2inv;
   }

   public Layer[] getLayers() {
      return this.layers;
   }

   public int[] getVi() {
      return this.vi;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCRainbowPrivateKey) {
         BCRainbowPrivateKey var2 = (BCRainbowPrivateKey)var1;
         boolean var3 = true;
         var3 = var3 && RainbowUtil.equals(this.A1inv, var2.getInvA1());
         var3 = var3 && RainbowUtil.equals(this.A2inv, var2.getInvA2());
         var3 = var3 && RainbowUtil.equals(this.b1, var2.getB1());
         var3 = var3 && RainbowUtil.equals(this.b2, var2.getB2());
         var3 = var3 && Arrays.equals(this.vi, var2.getVi());
         if (this.layers.length != var2.getLayers().length) {
            return false;
         } else {
            for(int var4 = this.layers.length - 1; var4 >= 0; --var4) {
               var3 &= this.layers[var4].equals(var2.getLayers()[var4]);
            }

            return var3;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.layers.length;
      var1 = var1 * 37 + org.python.bouncycastle.util.Arrays.hashCode(this.A1inv);
      var1 = var1 * 37 + org.python.bouncycastle.util.Arrays.hashCode(this.b1);
      var1 = var1 * 37 + org.python.bouncycastle.util.Arrays.hashCode(this.A2inv);
      var1 = var1 * 37 + org.python.bouncycastle.util.Arrays.hashCode(this.b2);
      var1 = var1 * 37 + org.python.bouncycastle.util.Arrays.hashCode(this.vi);

      for(int var2 = this.layers.length - 1; var2 >= 0; --var2) {
         var1 = var1 * 37 + this.layers[var2].hashCode();
      }

      return var1;
   }

   public final String getAlgorithm() {
      return "Rainbow";
   }

   public byte[] getEncoded() {
      RainbowPrivateKey var1 = new RainbowPrivateKey(this.A1inv, this.b1, this.A2inv, this.b2, this.vi, this.layers);

      PrivateKeyInfo var3;
      try {
         AlgorithmIdentifier var2 = new AlgorithmIdentifier(PQCObjectIdentifiers.rainbow, DERNull.INSTANCE);
         var3 = new PrivateKeyInfo(var2, var1);
      } catch (IOException var5) {
         var5.printStackTrace();
         return null;
      }

      try {
         byte[] var6 = var3.getEncoded();
         return var6;
      } catch (IOException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   public String getFormat() {
      return "PKCS#8";
   }
}
