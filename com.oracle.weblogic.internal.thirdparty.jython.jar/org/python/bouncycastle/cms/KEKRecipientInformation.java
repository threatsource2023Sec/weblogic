package org.python.bouncycastle.cms;

import java.io.IOException;
import org.python.bouncycastle.asn1.cms.KEKIdentifier;
import org.python.bouncycastle.asn1.cms.KEKRecipientInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class KEKRecipientInformation extends RecipientInformation {
   private KEKRecipientInfo info;

   KEKRecipientInformation(KEKRecipientInfo var1, AlgorithmIdentifier var2, CMSSecureReadable var3, AuthAttributesProvider var4) {
      super(var1.getKeyEncryptionAlgorithm(), var2, var3, var4);
      this.info = var1;
      KEKIdentifier var5 = var1.getKekid();
      this.rid = new KEKRecipientId(var5.getKeyIdentifier().getOctets());
   }

   protected RecipientOperator getRecipientOperator(Recipient var1) throws CMSException, IOException {
      return ((KEKRecipient)var1).getRecipientOperator(this.keyEncAlg, this.messageAlgorithm, this.info.getEncryptedKey().getOctets());
   }
}
