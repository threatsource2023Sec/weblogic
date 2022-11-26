package org.python.bouncycastle.asn1.bc;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.util.Arrays;

public class EncryptedSecretKeyData extends ASN1Object {
   private final AlgorithmIdentifier keyEncryptionAlgorithm;
   private final ASN1OctetString encryptedKeyData;

   public EncryptedSecretKeyData(AlgorithmIdentifier var1, byte[] var2) {
      this.keyEncryptionAlgorithm = var1;
      this.encryptedKeyData = new DEROctetString(Arrays.clone(var2));
   }

   private EncryptedSecretKeyData(ASN1Sequence var1) {
      this.keyEncryptionAlgorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.encryptedKeyData = ASN1OctetString.getInstance(var1.getObjectAt(1));
   }

   public static EncryptedSecretKeyData getInstance(Object var0) {
      if (var0 instanceof EncryptedSecretKeyData) {
         return (EncryptedSecretKeyData)var0;
      } else {
         return var0 != null ? new EncryptedSecretKeyData(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public AlgorithmIdentifier getKeyEncryptionAlgorithm() {
      return this.keyEncryptionAlgorithm;
   }

   public byte[] getEncryptedKeyData() {
      return Arrays.clone(this.encryptedKeyData.getOctets());
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.keyEncryptionAlgorithm);
      var1.add(this.encryptedKeyData);
      return new DERSequence(var1);
   }
}
