package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1Choice;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.cms.EnvelopedData;

public class EncryptedKey extends ASN1Object implements ASN1Choice {
   private EnvelopedData envelopedData;
   private EncryptedValue encryptedValue;

   public static EncryptedKey getInstance(Object var0) {
      if (var0 instanceof EncryptedKey) {
         return (EncryptedKey)var0;
      } else if (var0 instanceof ASN1TaggedObject) {
         return new EncryptedKey(EnvelopedData.getInstance((ASN1TaggedObject)var0, false));
      } else {
         return var0 instanceof EncryptedValue ? new EncryptedKey((EncryptedValue)var0) : new EncryptedKey(EncryptedValue.getInstance(var0));
      }
   }

   public EncryptedKey(EnvelopedData var1) {
      this.envelopedData = var1;
   }

   public EncryptedKey(EncryptedValue var1) {
      this.encryptedValue = var1;
   }

   public boolean isEncryptedValue() {
      return this.encryptedValue != null;
   }

   public ASN1Encodable getValue() {
      return (ASN1Encodable)(this.encryptedValue != null ? this.encryptedValue : this.envelopedData);
   }

   public ASN1Primitive toASN1Primitive() {
      return (ASN1Primitive)(this.encryptedValue != null ? this.encryptedValue.toASN1Primitive() : new DERTaggedObject(false, 0, this.envelopedData));
   }
}
