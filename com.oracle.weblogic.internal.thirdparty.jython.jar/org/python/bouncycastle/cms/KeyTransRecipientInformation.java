package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import org.python.bouncycastle.asn1.cms.RecipientIdentifier;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KeyTransRecipientInformation extends RecipientInformation {
   private KeyTransRecipientInfo info;

   KeyTransRecipientInformation(KeyTransRecipientInfo var1, AlgorithmIdentifier var2, CMSSecureReadable var3, AuthAttributesProvider var4) {
      super(var1.getKeyEncryptionAlgorithm(), var2, var3, var4);
      this.info = var1;
      RecipientIdentifier var5 = var1.getRecipientIdentifier();
      if (var5.isTagged()) {
         ASN1OctetString var6 = ASN1OctetString.getInstance(var5.getId());
         this.rid = new KeyTransRecipientId(var6.getOctets());
      } else {
         IssuerAndSerialNumber var7 = IssuerAndSerialNumber.getInstance(var5.getId());
         this.rid = new KeyTransRecipientId(var7.getName(), var7.getSerialNumber().getValue());
      }

   }

   protected RecipientOperator getRecipientOperator(Recipient var1) throws CMSException {
      return ((KeyTransRecipient)var1).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
   }
}
