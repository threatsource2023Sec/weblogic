package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.pqc.asn1.McEliecePrivateKey;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePrivateKeyParameters;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;
import org.python.bouncycastle.util.Strings;

public class BCMcEliecePrivateKey implements CipherParameters, PrivateKey {
   private static final long serialVersionUID = 1L;
   private McEliecePrivateKeyParameters params;

   public BCMcEliecePrivateKey(McEliecePrivateKeyParameters var1) {
      this.params = var1;
   }

   public String getAlgorithm() {
      return "McEliece";
   }

   public int getN() {
      return this.params.getN();
   }

   public int getK() {
      return this.params.getK();
   }

   public GF2mField getField() {
      return this.params.getField();
   }

   public PolynomialGF2mSmallM getGoppaPoly() {
      return this.params.getGoppaPoly();
   }

   public GF2Matrix getSInv() {
      return this.params.getSInv();
   }

   public Permutation getP1() {
      return this.params.getP1();
   }

   public Permutation getP2() {
      return this.params.getP2();
   }

   public GF2Matrix getH() {
      return this.params.getH();
   }

   public PolynomialGF2mSmallM[] getQInv() {
      return this.params.getQInv();
   }

   public String toString() {
      String var1 = " length of the code          : " + this.getN() + Strings.lineSeparator();
      var1 = var1 + " dimension of the code       : " + this.getK() + Strings.lineSeparator();
      var1 = var1 + " irreducible Goppa polynomial: " + this.getGoppaPoly() + Strings.lineSeparator();
      var1 = var1 + " permutation P1              : " + this.getP1() + Strings.lineSeparator();
      var1 = var1 + " permutation P2              : " + this.getP2() + Strings.lineSeparator();
      var1 = var1 + " (k x k)-matrix S^-1         : " + this.getSInv();
      return var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof BCMcEliecePrivateKey)) {
         return false;
      } else {
         BCMcEliecePrivateKey var2 = (BCMcEliecePrivateKey)var1;
         return this.getN() == var2.getN() && this.getK() == var2.getK() && this.getField().equals(var2.getField()) && this.getGoppaPoly().equals(var2.getGoppaPoly()) && this.getSInv().equals(var2.getSInv()) && this.getP1().equals(var2.getP1()) && this.getP2().equals(var2.getP2());
      }
   }

   public int hashCode() {
      int var1 = this.params.getK();
      var1 = var1 * 37 + this.params.getN();
      var1 = var1 * 37 + this.params.getField().hashCode();
      var1 = var1 * 37 + this.params.getGoppaPoly().hashCode();
      var1 = var1 * 37 + this.params.getP1().hashCode();
      var1 = var1 * 37 + this.params.getP2().hashCode();
      return var1 * 37 + this.params.getSInv().hashCode();
   }

   public byte[] getEncoded() {
      McEliecePrivateKey var1 = new McEliecePrivateKey(this.params.getN(), this.params.getK(), this.params.getField(), this.params.getGoppaPoly(), this.params.getP1(), this.params.getP2(), this.params.getSInv());

      PrivateKeyInfo var3;
      try {
         AlgorithmIdentifier var2 = new AlgorithmIdentifier(PQCObjectIdentifiers.mcEliece);
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

   AsymmetricKeyParameter getKeyParams() {
      return this.params;
   }
}
