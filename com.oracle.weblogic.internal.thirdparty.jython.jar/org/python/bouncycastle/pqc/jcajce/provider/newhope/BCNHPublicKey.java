package org.python.bouncycastle.pqc.jcajce.provider.newhope;

import java.io.IOException;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.crypto.newhope.NHPublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.interfaces.NHPublicKey;
import org.python.bouncycastle.util.Arrays;

public class BCNHPublicKey implements NHPublicKey {
   private static final long serialVersionUID = 1L;
   private final NHPublicKeyParameters params;

   public BCNHPublicKey(NHPublicKeyParameters var1) {
      this.params = var1;
   }

   public BCNHPublicKey(SubjectPublicKeyInfo var1) {
      this.params = new NHPublicKeyParameters(var1.getPublicKeyData().getBytes());
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCNHPublicKey) {
         BCNHPublicKey var2 = (BCNHPublicKey)var1;
         return Arrays.areEqual(this.params.getPubData(), var2.params.getPubData());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.params.getPubData());
   }

   public final String getAlgorithm() {
      return "NH";
   }

   public byte[] getEncoded() {
      try {
         AlgorithmIdentifier var1 = new AlgorithmIdentifier(PQCObjectIdentifiers.newHope);
         SubjectPublicKeyInfo var2 = new SubjectPublicKeyInfo(var1, this.params.getPubData());
         return var2.getEncoded();
      } catch (IOException var3) {
         return null;
      }
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getPublicData() {
      return this.params.getPubData();
   }

   CipherParameters getKeyParams() {
      return this.params;
   }
}
