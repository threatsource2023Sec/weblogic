package org.python.bouncycastle.cms;

import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.python.bouncycastle.asn1.cms.KeyTransRecipientInfo;
import org.python.bouncycastle.asn1.cms.RecipientIdentifier;
import org.python.bouncycastle.asn1.cms.RecipientInfo;
import org.python.bouncycastle.operator.AsymmetricKeyWrapper;
import org.python.bouncycastle.operator.GenericKey;
import org.python.bouncycastle.operator.OperatorException;

public abstract class KeyTransRecipientInfoGenerator implements RecipientInfoGenerator {
   protected final AsymmetricKeyWrapper wrapper;
   private IssuerAndSerialNumber issuerAndSerial;
   private byte[] subjectKeyIdentifier;

   protected KeyTransRecipientInfoGenerator(IssuerAndSerialNumber var1, AsymmetricKeyWrapper var2) {
      this.issuerAndSerial = var1;
      this.wrapper = var2;
   }

   protected KeyTransRecipientInfoGenerator(byte[] var1, AsymmetricKeyWrapper var2) {
      this.subjectKeyIdentifier = var1;
      this.wrapper = var2;
   }

   public final RecipientInfo generate(GenericKey var1) throws CMSException {
      byte[] var2;
      try {
         var2 = this.wrapper.generateWrappedKey(var1);
      } catch (OperatorException var4) {
         throw new CMSException("exception wrapping content key: " + var4.getMessage(), var4);
      }

      RecipientIdentifier var3;
      if (this.issuerAndSerial != null) {
         var3 = new RecipientIdentifier(this.issuerAndSerial);
      } else {
         var3 = new RecipientIdentifier(new DEROctetString(this.subjectKeyIdentifier));
      }

      return new RecipientInfo(new KeyTransRecipientInfo(var3, this.wrapper.getAlgorithmIdentifier(), new DEROctetString(var2)));
   }
}
