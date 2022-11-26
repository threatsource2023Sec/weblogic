package org.python.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.python.bouncycastle.crypto.DataLengthException;
import org.python.bouncycastle.crypto.DerivationParameters;
import org.python.bouncycastle.crypto.Mac;
import org.python.bouncycastle.crypto.MacDerivationFunction;
import org.python.bouncycastle.crypto.params.KDFFeedbackParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;

public class KDFFeedbackBytesGenerator implements MacDerivationFunction {
   private static final BigInteger INTEGER_MAX = BigInteger.valueOf(2147483647L);
   private static final BigInteger TWO = BigInteger.valueOf(2L);
   private final Mac prf;
   private final int h;
   private byte[] fixedInputData;
   private int maxSizeExcl;
   private byte[] ios;
   private byte[] iv;
   private boolean useCounter;
   private int generatedBytes;
   private byte[] k;

   public KDFFeedbackBytesGenerator(Mac var1) {
      this.prf = var1;
      this.h = var1.getMacSize();
      this.k = new byte[this.h];
   }

   public void init(DerivationParameters var1) {
      if (!(var1 instanceof KDFFeedbackParameters)) {
         throw new IllegalArgumentException("Wrong type of arguments given");
      } else {
         KDFFeedbackParameters var2 = (KDFFeedbackParameters)var1;
         this.prf.init(new KeyParameter(var2.getKI()));
         this.fixedInputData = var2.getFixedInputData();
         int var3 = var2.getR();
         this.ios = new byte[var3 / 8];
         if (var2.useCounter()) {
            BigInteger var4 = TWO.pow(var3).multiply(BigInteger.valueOf((long)this.h));
            this.maxSizeExcl = var4.compareTo(INTEGER_MAX) == 1 ? Integer.MAX_VALUE : var4.intValue();
         } else {
            this.maxSizeExcl = Integer.MAX_VALUE;
         }

         this.iv = var2.getIV();
         this.useCounter = var2.useCounter();
         this.generatedBytes = 0;
      }
   }

   public Mac getMac() {
      return this.prf;
   }

   public int generateBytes(byte[] var1, int var2, int var3) throws DataLengthException, IllegalArgumentException {
      int var4 = this.generatedBytes + var3;
      if (var4 >= 0 && var4 < this.maxSizeExcl) {
         if (this.generatedBytes % this.h == 0) {
            this.generateNext();
         }

         int var6 = this.generatedBytes % this.h;
         int var7 = this.h - this.generatedBytes % this.h;
         int var8 = Math.min(var7, var3);
         System.arraycopy(this.k, var6, var1, var2, var8);
         this.generatedBytes += var8;
         int var5 = var3 - var8;

         for(var2 += var8; var5 > 0; var2 += var8) {
            this.generateNext();
            var8 = Math.min(this.h, var5);
            System.arraycopy(this.k, 0, var1, var2, var8);
            this.generatedBytes += var8;
            var5 -= var8;
         }

         return var3;
      } else {
         throw new DataLengthException("Current KDFCTR may only be used for " + this.maxSizeExcl + " bytes");
      }
   }

   private void generateNext() {
      if (this.generatedBytes == 0) {
         this.prf.update(this.iv, 0, this.iv.length);
      } else {
         this.prf.update(this.k, 0, this.k.length);
      }

      if (this.useCounter) {
         int var1 = this.generatedBytes / this.h + 1;
         switch (this.ios.length) {
            case 4:
               this.ios[0] = (byte)(var1 >>> 24);
            case 3:
               this.ios[this.ios.length - 3] = (byte)(var1 >>> 16);
            case 2:
               this.ios[this.ios.length - 2] = (byte)(var1 >>> 8);
            case 1:
               this.ios[this.ios.length - 1] = (byte)var1;
               this.prf.update(this.ios, 0, this.ios.length);
               break;
            default:
               throw new IllegalStateException("Unsupported size of counter i");
         }
      }

      this.prf.update(this.fixedInputData, 0, this.fixedInputData.length);
      this.prf.doFinal(this.k, 0);
   }
}
