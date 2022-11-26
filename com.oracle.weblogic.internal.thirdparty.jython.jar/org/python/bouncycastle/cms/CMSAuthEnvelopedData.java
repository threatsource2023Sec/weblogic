package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.AuthEnvelopedData;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.python.bouncycastle.asn1.cms.OriginatorInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

class CMSAuthEnvelopedData {
   RecipientInformationStore recipientInfoStore;
   ContentInfo contentInfo;
   private OriginatorInfo originator;
   private AlgorithmIdentifier authEncAlg;
   private ASN1Set authAttrs;
   private byte[] mac;
   private ASN1Set unauthAttrs;

   public CMSAuthEnvelopedData(byte[] var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSAuthEnvelopedData(InputStream var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSAuthEnvelopedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;
      AuthEnvelopedData var2 = AuthEnvelopedData.getInstance(var1.getContent());
      this.originator = var2.getOriginatorInfo();
      ASN1Set var3 = var2.getRecipientInfos();
      EncryptedContentInfo var4 = var2.getAuthEncryptedContentInfo();
      this.authEncAlg = var4.getContentEncryptionAlgorithm();
      CMSSecureReadable var5 = new CMSSecureReadable() {
         public InputStream getInputStream() throws IOException, CMSException {
            return null;
         }
      };
      this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var3, this.authEncAlg, var5);
      this.authAttrs = var2.getAuthAttrs();
      this.mac = var2.getMac().getOctets();
      this.unauthAttrs = var2.getUnauthAttrs();
   }
}
