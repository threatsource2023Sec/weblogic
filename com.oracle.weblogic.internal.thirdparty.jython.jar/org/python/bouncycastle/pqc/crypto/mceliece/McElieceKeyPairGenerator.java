package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.python.bouncycastle.pqc.math.linearalgebra.Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialRingGF2m;

public class McElieceKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   private static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
   private McElieceKeyGenerationParameters mcElieceParams;
   private int m;
   private int n;
   private int t;
   private int fieldPoly;
   private SecureRandom random;
   private boolean initialized = false;

   private void initializeDefault() {
      McElieceKeyGenerationParameters var1 = new McElieceKeyGenerationParameters(new SecureRandom(), new McElieceParameters());
      this.initialize(var1);
   }

   private void initialize(KeyGenerationParameters var1) {
      this.mcElieceParams = (McElieceKeyGenerationParameters)var1;
      this.random = new SecureRandom();
      this.m = this.mcElieceParams.getParameters().getM();
      this.n = this.mcElieceParams.getParameters().getN();
      this.t = this.mcElieceParams.getParameters().getT();
      this.fieldPoly = this.mcElieceParams.getParameters().getFieldPoly();
      this.initialized = true;
   }

   private AsymmetricCipherKeyPair genKeyPair() {
      if (!this.initialized) {
         this.initializeDefault();
      }

      GF2mField var1 = new GF2mField(this.m, this.fieldPoly);
      PolynomialGF2mSmallM var2 = new PolynomialGF2mSmallM(var1, this.t, 'I', this.random);
      PolynomialRingGF2m var3 = new PolynomialRingGF2m(var1, var2);
      PolynomialGF2mSmallM[] var4 = var3.getSquareRootMatrix();
      GF2Matrix var5 = GoppaCode.createCanonicalCheckMatrix(var1, var2);
      GoppaCode.MaMaPe var6 = GoppaCode.computeSystematicForm(var5, this.random);
      GF2Matrix var7 = var6.getSecondMatrix();
      Permutation var8 = var6.getPermutation();
      GF2Matrix var9 = (GF2Matrix)var7.computeTranspose();
      GF2Matrix var10 = var9.extendLeftCompactForm();
      int var11 = var9.getNumRows();
      GF2Matrix[] var12 = GF2Matrix.createRandomRegularMatrixAndItsInverse(var11, this.random);
      Permutation var13 = new Permutation(this.n, this.random);
      GF2Matrix var14 = (GF2Matrix)var12[0].rightMultiply((Matrix)var10);
      var14 = (GF2Matrix)var14.rightMultiply(var13);
      McEliecePublicKeyParameters var15 = new McEliecePublicKeyParameters(this.n, this.t, var14);
      McEliecePrivateKeyParameters var16 = new McEliecePrivateKeyParameters(this.n, var11, var1, var2, var8, var13, var12[1]);
      return new AsymmetricCipherKeyPair(var15, var16);
   }

   public void init(KeyGenerationParameters var1) {
      this.initialize(var1);
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      return this.genKeyPair();
   }
}
