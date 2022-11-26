package org.python.bouncycastle.asn1.cmp;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge extends ASN1Object {
   private AlgorithmIdentifier owf;
   private ASN1OctetString witness;
   private ASN1OctetString challenge;

   private Challenge(ASN1Sequence var1) {
      int var2 = 0;
      if (var1.size() == 3) {
         this.owf = AlgorithmIdentifier.getInstance(var1.getObjectAt(var2++));
      }

      this.witness = ASN1OctetString.getInstance(var1.getObjectAt(var2++));
      this.challenge = ASN1OctetString.getInstance(var1.getObjectAt(var2));
   }

   public static Challenge getInstance(Object var0) {
      if (var0 instanceof Challenge) {
         return (Challenge)var0;
      } else {
         return var0 != null ? new Challenge(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public Challenge(byte[] var1, byte[] var2) {
      this((AlgorithmIdentifier)null, var1, var2);
   }

   public Challenge(AlgorithmIdentifier var1, byte[] var2, byte[] var3) {
      this.owf = var1;
      this.witness = new DEROctetString(var2);
      this.challenge = new DEROctetString(var3);
   }

   public AlgorithmIdentifier getOwf() {
      return this.owf;
   }

   public byte[] getWitness() {
      return this.witness.getOctets();
   }

   public byte[] getChallenge() {
      return this.challenge.getOctets();
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      this.addOptional(var1, this.owf);
      var1.add(this.witness);
      var1.add(this.challenge);
      return new DERSequence(var1);
   }

   private void addOptional(ASN1EncodableVector var1, ASN1Encodable var2) {
      if (var2 != null) {
         var1.add(var2);
      }

   }
}
