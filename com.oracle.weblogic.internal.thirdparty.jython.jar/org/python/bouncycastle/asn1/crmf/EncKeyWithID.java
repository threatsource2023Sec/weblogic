package org.python.bouncycastle.asn1.crmf;

import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Object;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.GeneralName;

public class EncKeyWithID extends ASN1Object {
   private final PrivateKeyInfo privKeyInfo;
   private final ASN1Encodable identifier;

   public static EncKeyWithID getInstance(Object var0) {
      if (var0 instanceof EncKeyWithID) {
         return (EncKeyWithID)var0;
      } else {
         return var0 != null ? new EncKeyWithID(ASN1Sequence.getInstance(var0)) : null;
      }
   }

   private EncKeyWithID(ASN1Sequence var1) {
      this.privKeyInfo = PrivateKeyInfo.getInstance(var1.getObjectAt(0));
      if (var1.size() > 1) {
         if (!(var1.getObjectAt(1) instanceof DERUTF8String)) {
            this.identifier = GeneralName.getInstance(var1.getObjectAt(1));
         } else {
            this.identifier = var1.getObjectAt(1);
         }
      } else {
         this.identifier = null;
      }

   }

   public EncKeyWithID(PrivateKeyInfo var1) {
      this.privKeyInfo = var1;
      this.identifier = null;
   }

   public EncKeyWithID(PrivateKeyInfo var1, DERUTF8String var2) {
      this.privKeyInfo = var1;
      this.identifier = var2;
   }

   public EncKeyWithID(PrivateKeyInfo var1, GeneralName var2) {
      this.privKeyInfo = var1;
      this.identifier = var2;
   }

   public PrivateKeyInfo getPrivateKey() {
      return this.privKeyInfo;
   }

   public boolean hasIdentifier() {
      return this.identifier != null;
   }

   public boolean isIdentifierUTF8String() {
      return this.identifier instanceof DERUTF8String;
   }

   public ASN1Encodable getIdentifier() {
      return this.identifier;
   }

   public ASN1Primitive toASN1Primitive() {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(this.privKeyInfo);
      if (this.identifier != null) {
         var1.add(this.identifier);
      }

      return new DERSequence(var1);
   }
}
