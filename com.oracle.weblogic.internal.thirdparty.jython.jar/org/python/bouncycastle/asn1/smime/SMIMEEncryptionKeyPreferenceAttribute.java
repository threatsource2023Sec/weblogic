package org.python.bouncycastle.asn1.smime;

import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.cms.Attribute;
import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.asn1.cms.RecipientKeyIdentifier;

public class SMIMEEncryptionKeyPreferenceAttribute extends Attribute {
   public SMIMEEncryptionKeyPreferenceAttribute(IssuerAndSerialNumber var1) {
      super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 0, var1)));
   }

   public SMIMEEncryptionKeyPreferenceAttribute(RecipientKeyIdentifier var1) {
      super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 1, var1)));
   }

   public SMIMEEncryptionKeyPreferenceAttribute(ASN1OctetString var1) {
      super(SMIMEAttributes.encrypKeyPref, new DERSet(new DERTaggedObject(false, 2, var1)));
   }
}
