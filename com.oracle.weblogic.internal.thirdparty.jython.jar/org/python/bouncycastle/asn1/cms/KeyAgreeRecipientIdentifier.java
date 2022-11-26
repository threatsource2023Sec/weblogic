package org.python.bouncycastle.asn1.cms;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERTaggedObject;

public class KeyAgreeRecipientIdentifier extends ASN1Object implements ASN1Choice {
   private IssuerAndSerialNumber issuerSerial;
   private RecipientKeyIdentifier rKeyID;

   public static KeyAgreeRecipientIdentifier getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   public static KeyAgreeRecipientIdentifier getInstance(Object var0) {
      if (var0 != null && !(var0 instanceof KeyAgreeRecipientIdentifier)) {
         if (var0 instanceof ASN1Sequence) {
            return new KeyAgreeRecipientIdentifier(IssuerAndSerialNumber.getInstance(var0));
         } else if (var0 instanceof ASN1TaggedObject && ((ASN1TaggedObject)var0).getTagNo() == 0) {
            return new KeyAgreeRecipientIdentifier(RecipientKeyIdentifier.getInstance((ASN1TaggedObject)var0, false));
         } else {
            throw new IllegalArgumentException("Invalid KeyAgreeRecipientIdentifier: " + var0.getClass().getName());
         }
      } else {
         return (KeyAgreeRecipientIdentifier)var0;
      }
   }

   public KeyAgreeRecipientIdentifier(IssuerAndSerialNumber var1) {
      this.issuerSerial = var1;
      this.rKeyID = null;
   }

   public KeyAgreeRecipientIdentifier(RecipientKeyIdentifier var1) {
      this.issuerSerial = null;
      this.rKeyID = var1;
   }

   public IssuerAndSerialNumber getIssuerAndSerialNumber() {
      return this.issuerSerial;
   }

   public RecipientKeyIdentifier getRKeyID() {
      return this.rKeyID;
   }

   public ASN1Primitive toASN1Primitive() {
      return (ASN1Primitive)(this.issuerSerial != null ? this.issuerSerial.toASN1Primitive() : new DERTaggedObject(false, 0, this.rKeyID));
   }
}
