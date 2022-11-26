package org.python.bouncycastle.asn1.cryptopro;

import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERSequence;

public class GOST3410PublicKeyAlgParameters extends ASN1Object {
   private ASN1ObjectIdentifier publicKeyParamSet;
   private ASN1ObjectIdentifier digestParamSet;
   private ASN1ObjectIdentifier encryptionParamSet;

   public static GOST3410PublicKeyAlgParameters getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static GOST3410PublicKeyAlgParameters getInstance(Object var0) {
      if (var0 instanceof GOST3410PublicKeyAlgParameters) {
         return (GOST3410PublicKeyAlgParameters)var0;
      } else {
         return var0 != null ? new GOST3410PublicKeyAlgParameters(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier var1, ASN1ObjectIdentifier var2) {
      this.publicKeyParamSet = var1;
      this.digestParamSet = var2;
      this.encryptionParamSet = null;
   }

   public GOST3410PublicKeyAlgParameters(ASN1ObjectIdentifier var1, ASN1ObjectIdentifier var2, ASN1ObjectIdentifier var3) {
      this.publicKeyParamSet = var1;
      this.digestParamSet = var2;
      this.encryptionParamSet = var3;
   }

   /** @deprecated */
   public GOST3410PublicKeyAlgParameters(ASN1Sequence var1) {
      this.publicKeyParamSet = (ASN1ObjectIdentifier)var1.getObjectAt(0);
      this.digestParamSet = (ASN1ObjectIdentifier)var1.getObjectAt(1);
      if (var1.size() > 2) {
         this.encryptionParamSet = (ASN1ObjectIdentifier)var1.getObjectAt(2);
      }

   }

   public ASN1ObjectIdentifier getPublicKeyParamSet() {
      return this.publicKeyParamSet;
   }

   public ASN1ObjectIdentifier getDigestParamSet() {
      return this.digestParamSet;
   }

   public ASN1ObjectIdentifier getEncryptionParamSet() {
      return this.encryptionParamSet;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.publicKeyParamSet);
      var1.add(this.digestParamSet);
      if (this.encryptionParamSet != null) {
         var1.add(this.encryptionParamSet);
      }

      return new DERSequence(var1);
   }
}
