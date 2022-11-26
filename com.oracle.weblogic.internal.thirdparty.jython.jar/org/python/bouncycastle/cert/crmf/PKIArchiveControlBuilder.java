package org.python.bouncycastle.cert.crmf;

import java.io.IOException;
import org.python.bouncycastle.asn1.cms.EnvelopedData;
import org.python.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.python.bouncycastle.asn1.crmf.EncKeyWithID;
import org.python.bouncycastle.asn1.crmf.EncryptedKey;
import org.python.bouncycastle.asn1.crmf.PKIArchiveOptions;
import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.cms.CMSEnvelopedData;
import org.python.bouncycastle.cms.CMSEnvelopedDataGenerator;
import org.python.bouncycastle.cms.CMSException;
import org.python.bouncycastle.cms.CMSProcessableByteArray;
import org.python.bouncycastle.cms.RecipientInfoGenerator;
import org.python.bouncycastle.operator.OutputEncryptor;

public class PKIArchiveControlBuilder {
   private CMSEnvelopedDataGenerator envGen;
   private CMSProcessableByteArray keyContent;

   public PKIArchiveControlBuilder(PrivateKeyInfo var1, GeneralName var2) {
      EncKeyWithID var3 = new EncKeyWithID(var1, var2);

      try {
         this.keyContent = new CMSProcessableByteArray(CRMFObjectIdentifiers.id_ct_encKeyWithID, var3.getEncoded());
      } catch (IOException var5) {
         throw new IllegalStateException("unable to encode key and general name info");
      }

      this.envGen = new CMSEnvelopedDataGenerator();
   }

   public PKIArchiveControlBuilder addRecipientGenerator(RecipientInfoGenerator var1) {
      this.envGen.addRecipientInfoGenerator(var1);
      return this;
   }

   public PKIArchiveControl build(OutputEncryptor var1) throws CMSException {
      CMSEnvelopedData var2 = this.envGen.generate(this.keyContent, var1);
      EnvelopedData var3 = EnvelopedData.getInstance(var2.toASN1Structure().getContent());
      return new PKIArchiveControl(new PKIArchiveOptions(new EncryptedKey(var3)));
   }
}
