package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PrivateKey;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.python.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.python.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

public class BCMcElieceCCA2PrivateKey implements PrivateKey {
   private static final long serialVersionUID = 1L;
   private McElieceCCA2PrivateKeyParameters params;

   public BCMcElieceCCA2PrivateKey(McElieceCCA2PrivateKeyParameters var1) {
      this.params = var1;
   }

   public String getAlgorithm() {
      return "McEliece-CCA2";
   }

   public int getN() {
      return this.params.getN();
   }

   public int getK() {
      return this.params.getK();
   }

   public int getT() {
      return this.params.getGoppaPoly().getDegree();
   }

   public GF2mField getField() {
      return this.params.getField();
   }

   public PolynomialGF2mSmallM getGoppaPoly() {
      return this.params.getGoppaPoly();
   }

   public Permutation getP() {
      return this.params.getP();
   }

   public GF2Matrix getH() {
      return this.params.getH();
   }

   public PolynomialGF2mSmallM[] getQInv() {
      return this.params.getQInv();
   }

   public String toString() {
      String var1 = "";
      var1 = var1 + " extension degree of the field      : " + this.getN() + "\n";
      var1 = var1 + " dimension of the code              : " + this.getK() + "\n";
      var1 = var1 + " irreducible Goppa polynomial       : " + this.getGoppaPoly() + "\n";
      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCMcElieceCCA2PrivateKey) {
         BCMcElieceCCA2PrivateKey var2 = (BCMcElieceCCA2PrivateKey)var1;
         return this.getN() == var2.getN() && this.getK() == var2.getK() && this.getField().equals(var2.getField()) && this.getGoppaPoly().equals(var2.getGoppaPoly()) && this.getP().equals(var2.getP()) && this.getH().equals(var2.getH());
      } else {
         return false;
      }
   }

   public int hashCode() {
      int var1 = this.params.getK();
      var1 = var1 * 37 + this.params.getN();
      var1 = var1 * 37 + this.params.getField().hashCode();
      var1 = var1 * 37 + this.params.getGoppaPoly().hashCode();
      var1 = var1 * 37 + this.params.getP().hashCode();
      return var1 * 37 + this.params.getH().hashCode();
   }

   public byte[] getEncoded() {
      try {
         McElieceCCA2PrivateKey var1 = new McElieceCCA2PrivateKey(this.getN(), this.getK(), this.getField(), this.getGoppaPoly(), this.getP(), Utils.getDigAlgId(this.params.getDigest()));
         AlgorithmIdentifier var2 = new AlgorithmIdentifier(PQCObjectIdentifiers.mcElieceCca2);
         PrivateKeyInfo var3 = new PrivateKeyInfo(var2, var1);
         return var3.getEncoded();
      } catch (IOException var4) {
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
