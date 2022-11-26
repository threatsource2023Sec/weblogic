package org.python.bouncycastle.pqc.jcajce.provider.sphincs;

import java.io.IOException;
import java.security.PublicKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.asn1.SPHINCS256KeyParams;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCSPublicKeyParameters;
import org.python.bouncycastle.pqc.jcajce.interfaces.SPHINCSKey;
import org.python.bouncycastle.util.Arrays;

public class BCSphincs256PublicKey implements PublicKey, SPHINCSKey {
   private static final long serialVersionUID = 1L;
   private final ASN1ObjectIdentifier treeDigest;
   private final SPHINCSPublicKeyParameters params;

   public BCSphincs256PublicKey(ASN1ObjectIdentifier var1, SPHINCSPublicKeyParameters var2) {
      this.treeDigest = var1;
      this.params = var2;
   }

   public BCSphincs256PublicKey(SubjectPublicKeyInfo var1) {
      this.treeDigest = SPHINCS256KeyParams.getInstance(var1.getAlgorithm().getParameters()).getTreeDigest().getAlgorithm();
      this.params = new SPHINCSPublicKeyParameters(var1.getPublicKeyData().getBytes());
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCSphincs256PublicKey) {
         BCSphincs256PublicKey var2 = (BCSphincs256PublicKey)var1;
         return this.treeDigest.equals(var2.treeDigest) && Arrays.areEqual(this.params.getKeyData(), var2.params.getKeyData());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.treeDigest.hashCode() + 37 * Arrays.hashCode(this.params.getKeyData());
   }

   public final String getAlgorithm() {
      return "SPHINCS-256";
   }

   public byte[] getEncoded() {
      try {
         AlgorithmIdentifier var1 = new AlgorithmIdentifier(PQCObjectIdentifiers.sphincs256, new SPHINCS256KeyParams(new AlgorithmIdentifier(this.treeDigest)));
         SubjectPublicKeyInfo var2 = new SubjectPublicKeyInfo(var1, this.params.getKeyData());
         return var2.getEncoded();
      } catch (IOException var3) {
         return null;
      }
   }

   public String getFormat() {
      return "X.509";
   }

   public byte[] getKeyData() {
      return this.params.getKeyData();
   }

   CipherParameters getKeyParams() {
      return this.params;
   }
}
