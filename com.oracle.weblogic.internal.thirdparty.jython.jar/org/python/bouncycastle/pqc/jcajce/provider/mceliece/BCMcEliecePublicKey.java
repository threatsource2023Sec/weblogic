package org.python.bouncycastle.pqc.jcajce.provider.mceliece;

import java.io.IOException;
import java.security.PublicKey;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.pqc.asn1.McEliecePublicKey;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.mceliece.McEliecePublicKeyParameters;
import org.python.bouncycastle.pqc.math.linearalgebra.GF2Matrix;

public class BCMcEliecePublicKey implements PublicKey {
   private static final long serialVersionUID = 1L;
   private McEliecePublicKeyParameters params;

   public BCMcEliecePublicKey(McEliecePublicKeyParameters var1) {
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

   public int getT() {
      return this.params.getT();
   }

   public GF2Matrix getG() {
      return this.params.getG();
   }

   public String toString() {
      String var1 = "McEliecePublicKey:\n";
      var1 = var1 + " length of the code         : " + this.params.getN() + "\n";
      var1 = var1 + " error correction capability: " + this.params.getT() + "\n";
      var1 = var1 + " generator matrix           : " + this.params.getG();
      return var1;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof BCMcEliecePublicKey)) {
         return false;
      } else {
         BCMcEliecePublicKey var2 = (BCMcEliecePublicKey)var1;
         return this.params.getN() == var2.getN() && this.params.getT() == var2.getT() && this.params.getG().equals(var2.getG());
      }
   }

   public int hashCode() {
      return 37 * (this.params.getN() + 37 * this.params.getT()) + this.params.getG().hashCode();
   }

   public byte[] getEncoded() {
      McEliecePublicKey var1 = new McEliecePublicKey(this.params.getN(), this.params.getT(), this.params.getG());
      AlgorithmIdentifier var2 = new AlgorithmIdentifier(PQCObjectIdentifiers.mcEliece);

      try {
         SubjectPublicKeyInfo var3 = new SubjectPublicKeyInfo(var2, var1);
         return var3.getEncoded();
      } catch (IOException var4) {
         return null;
      }
   }

   public String getFormat() {
      return "X.509";
   }

   AsymmetricKeyParameter getKeyParams() {
      return this.params;
   }
}
