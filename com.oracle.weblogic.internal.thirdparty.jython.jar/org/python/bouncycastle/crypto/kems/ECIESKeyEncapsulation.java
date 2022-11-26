package org.python.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.DerivationFunction;
import org.python.bouncycastle.crypto.KeyEncapsulation;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.crypto.params.ECKeyParameters;
import org.python.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.python.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.python.bouncycastle.crypto.params.KDFParameters;
import org.python.bouncycastle.crypto.params.KeyParameter;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.ec.ECMultiplier;
import org.python.bouncycastle.math.ec.ECPoint;
import org.python.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.BigIntegers;

public class ECIESKeyEncapsulation implements KeyEncapsulation {
   private static final BigInteger ONE = BigInteger.valueOf(1L);
   private DerivationFunction kdf;
   private SecureRandom rnd;
   private ECKeyParameters key;
   private boolean CofactorMode;
   private boolean OldCofactorMode;
   private boolean SingleHashMode;

   public ECIESKeyEncapsulation(DerivationFunction var1, SecureRandom var2) {
      this.kdf = var1;
      this.rnd = var2;
      this.CofactorMode = false;
      this.OldCofactorMode = false;
      this.SingleHashMode = false;
   }

   public ECIESKeyEncapsulation(DerivationFunction var1, SecureRandom var2, boolean var3, boolean var4, boolean var5) {
      this.kdf = var1;
      this.rnd = var2;
      this.CofactorMode = var3;
      this.OldCofactorMode = var4;
      this.SingleHashMode = var5;
   }

   public void init(CipherParameters var1) throws IllegalArgumentException {
      if (!(var1 instanceof ECKeyParameters)) {
         throw new IllegalArgumentException("EC key required");
      } else {
         this.key = (ECKeyParameters)var1;
      }
   }

   public CipherParameters encrypt(byte[] var1, int var2, int var3) throws IllegalArgumentException {
      if (!(this.key instanceof ECPublicKeyParameters)) {
         throw new IllegalArgumentException("Public key required for encryption");
      } else {
         ECPublicKeyParameters var4 = (ECPublicKeyParameters)this.key;
         ECDomainParameters var5 = var4.getParameters();
         ECCurve var6 = var5.getCurve();
         BigInteger var7 = var5.getN();
         BigInteger var8 = var5.getH();
         BigInteger var9 = BigIntegers.createRandomInRange(ONE, var7, this.rnd);
         BigInteger var10 = this.CofactorMode ? var9.multiply(var8).mod(var7) : var9;
         ECMultiplier var11 = this.createBasePointMultiplier();
         ECPoint[] var12 = new ECPoint[]{var11.multiply(var5.getG(), var9), var4.getQ().multiply(var10)};
         var6.normalizeAll(var12);
         ECPoint var13 = var12[0];
         ECPoint var14 = var12[1];
         byte[] var15 = var13.getEncoded(false);
         System.arraycopy(var15, 0, var1, var2, var15.length);
         byte[] var16 = var14.getAffineXCoord().getEncoded();
         return this.deriveKey(var3, var15, var16);
      }
   }

   public CipherParameters encrypt(byte[] var1, int var2) {
      return this.encrypt(var1, 0, var2);
   }

   public CipherParameters decrypt(byte[] var1, int var2, int var3, int var4) throws IllegalArgumentException {
      if (!(this.key instanceof ECPrivateKeyParameters)) {
         throw new IllegalArgumentException("Private key required for encryption");
      } else {
         ECPrivateKeyParameters var5 = (ECPrivateKeyParameters)this.key;
         ECDomainParameters var6 = var5.getParameters();
         ECCurve var7 = var6.getCurve();
         BigInteger var8 = var6.getN();
         BigInteger var9 = var6.getH();
         byte[] var10 = new byte[var3];
         System.arraycopy(var1, var2, var10, 0, var3);
         ECPoint var11 = var7.decodePoint(var10);
         ECPoint var12 = var11;
         if (this.CofactorMode || this.OldCofactorMode) {
            var12 = var11.multiply(var9);
         }

         BigInteger var13 = var5.getD();
         if (this.CofactorMode) {
            var13 = var13.multiply(var9.modInverse(var8)).mod(var8);
         }

         ECPoint var14 = var12.multiply(var13).normalize();
         byte[] var15 = var14.getAffineXCoord().getEncoded();
         return this.deriveKey(var4, var10, var15);
      }
   }

   public CipherParameters decrypt(byte[] var1, int var2) {
      return this.decrypt(var1, 0, var1.length, var2);
   }

   protected ECMultiplier createBasePointMultiplier() {
      return new FixedPointCombMultiplier();
   }

   protected KeyParameter deriveKey(int var1, byte[] var2, byte[] var3) {
      byte[] var4 = var3;
      if (!this.SingleHashMode) {
         var4 = Arrays.concatenate(var2, var3);
         Arrays.fill((byte[])var3, (byte)0);
      }

      KeyParameter var6;
      try {
         this.kdf.init(new KDFParameters(var4, (byte[])null));
         byte[] var5 = new byte[var1];
         this.kdf.generateBytes(var5, 0, var5.length);
         var6 = new KeyParameter(var5);
      } finally {
         Arrays.fill((byte[])var4, (byte)0);
      }

      return var6;
   }
}
