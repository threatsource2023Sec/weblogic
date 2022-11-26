package org.python.bouncycastle.asn1.cms;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class OriginatorPublicKey extends ASN1Object {
   private AlgorithmIdentifier algorithm;
   private DERBitString publicKey;

   public OriginatorPublicKey(AlgorithmIdentifier var1, byte[] var2) {
      this.algorithm = var1;
      this.publicKey = new DERBitString(var2);
   }

   /** @deprecated */
   public OriginatorPublicKey(ASN1Sequence var1) {
      this.algorithm = AlgorithmIdentifier.getInstance(var1.getObjectAt(0));
      this.publicKey = (DERBitString)var1.getObjectAt(1);
   }

   public static OriginatorPublicKey getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static OriginatorPublicKey getInstance(Object var0) {
      if (var0 instanceof OriginatorPublicKey) {
         return (OriginatorPublicKey)var0;
      } else {
         return var0 != null ? new OriginatorPublicKey(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public AlgorithmIdentifier getAlgorithm() {
      return this.algorithm;
   }

   public DERBitString getPublicKey() {
      return this.publicKey;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.algorithm);
      var1.add(this.publicKey);
      return new DERSequence(var1);
   }
}
