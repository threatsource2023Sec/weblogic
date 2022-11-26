package org.python.bouncycastle.asn1.mozilla;

import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;

public class PublicKeyAndChallenge extends ASN1Object {
   private ASN1Sequence pkacSeq;
   private SubjectPublicKeyInfo spki;
   private DERIA5String challenge;

   public static PublicKeyAndChallenge getInstance(Object var0) {
      if (var0 instanceof PublicKeyAndChallenge) {
         return (PublicKeyAndChallenge)var0;
      } else {
         return var0 != null ? new PublicKeyAndChallenge(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private PublicKeyAndChallenge(ASN1Sequence var1) {
      this.pkacSeq = var1;
      this.spki = SubjectPublicKeyInfo.getInstance(var1.getObjectAt(0));
      this.challenge = DERIA5String.getInstance(var1.getObjectAt(1));
   }

   public ASN1Primitive toASN1Primitive() {
      return this.pkacSeq;
   }

   public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
      return this.spki;
   }

   public DERIA5String getChallenge() {
      return this.challenge;
   }
}
