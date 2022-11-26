package org.python.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.python.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.python.bouncycastle.crypto.KeyGenerationParameters;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class McElieceCCA2KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
   public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2";
   private McElieceCCA2KeyGenerationParameters mcElieceCCA2Params;
   private int m;
   private int n;
   private int t;
   private int fieldPoly;
   private SecureRandom random;
   private boolean initialized = false;

   private void initializeDefault() {
      McElieceCCA2KeyGenerationParameters var1 = new McElieceCCA2KeyGenerationParameters(new SecureRandom(), new McElieceCCA2Parameters());
      this.init(var1);
   }

   public void init(KeyGenerationParameters var1) {
      this.mcElieceCCA2Params = (McElieceCCA2KeyGenerationParameters)var1;
      this.random = new SecureRandom();
      this.m = this.mcElieceCCA2Params.getParameters().getM();
      this.n = this.mcElieceCCA2Params.getParameters().getN();
      this.t = this.mcElieceCCA2Params.getParameters().getT();
      this.fieldPoly = this.mcElieceCCA2Params.getParameters().getFieldPoly();
      this.initialized = true;
   }

   public AsymmetricCipherKeyPair generateKeyPair() {
      if (!this.initialized) {
         this.initializeDefault();
      }

      GF2mField var1 = new GF2mField(this.m, this.fieldPoly);
      PolynomialGF2mSmallM var2 = new PolynomialGF2mSmallM(var1, this.t, 'I', this.random);
      GF2Matrix var3 = GoppaCode.createCanonicalCheckMatrix(var1, var2);
      GoppaCode.MaMaPe var4 = GoppaCode.computeSystematicForm(var3, this.random);
      GF2Matrix var5 = var4.getSecondMatrix();
      Permutation var6 = var4.getPermutation();
      GF2Matrix var7 = (GF2Matrix)var5.computeTranspose();
      int var8 = var7.getNumRows();
      McElieceCCA2PublicKeyParameters var9 = new McElieceCCA2PublicKeyParameters(this.n, this.t, var7, this.mcElieceCCA2Params.getParameters().getDigest());
      McElieceCCA2PrivateKeyParameters var10 = new McElieceCCA2PrivateKeyParameters(this.n, var8, var1, var2, var6, this.mcElieceCCA2Params.getParameters().getDigest());
      return new AsymmetricCipherKeyPair(var9, var10);
   }
}
