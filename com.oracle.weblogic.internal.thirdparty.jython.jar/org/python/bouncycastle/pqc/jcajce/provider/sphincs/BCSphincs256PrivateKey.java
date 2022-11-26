package org.python.bouncycastle.pqc.jcajce.provider.sphincs;

import java.io.IOException;
import java.security.PrivateKey;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.crypto.CipherParameters;
import org.python.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.python.bouncycastle.pqc.asn1.SPHINCS256KeyParams;
import org.python.bouncycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.python.bouncycastle.pqc.jcajce.interfaces.SPHINCSKey;
import org.python.bouncycastle.util.Arrays;

public class BCSphincs256PrivateKey implements PrivateKey, SPHINCSKey {
   private static final long serialVersionUID = 1L;
   private final ASN1ObjectIdentifier treeDigest;
   private final SPHINCSPrivateKeyParameters params;

   public BCSphincs256PrivateKey(ASN1ObjectIdentifier var1, SPHINCSPrivateKeyParameters var2) {
      this.treeDigest = var1;
      this.params = var2;
   }

   public BCSphincs256PrivateKey(PrivateKeyInfo var1) throws IOException {
      this.treeDigest = SPHINCS256KeyParams.getInstance(var1.getPrivateKeyAlgorithm().getParameters()).getTreeDigest().getAlgorithm();
      this.params = new SPHINCSPrivateKeyParameters(ASN1OctetString.getInstance(var1.parsePrivateKey()).getOctets());
   }

   public boolean equals(Object var1) {
      if (var1 != null && var1 instanceof BCSphincs256PrivateKey) {
         BCSphincs256PrivateKey var2 = (BCSphincs256PrivateKey)var1;
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
         PrivateKeyInfo var2 = new PrivateKeyInfo(var1, new DEROctetString(this.params.getKeyData()));
         return var2.getEncoded();
      } catch (IOException var3) {
         return null;
      }
   }

   public String getFormat() {
      return "PKCS#8";
   }

   public byte[] getKeyData() {
      return this.params.getKeyData();
   }

   CipherParameters getKeyParams() {
      return this.params;
   }
}
