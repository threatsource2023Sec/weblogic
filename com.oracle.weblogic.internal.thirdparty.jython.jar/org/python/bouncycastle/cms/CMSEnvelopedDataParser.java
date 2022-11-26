package org.python.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1OctetStringParser;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.EncryptedContentInfoParser;
import org.python.bouncycastle.asn1.cms.EnvelopedDataParser;
import org.python.bouncycastle.asn1.cms.OriginatorInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;

public class CMSEnvelopedDataParser extends CMSContentInfoParser {
   RecipientInformationStore recipientInfoStore;
   EnvelopedDataParser envelopedData;
   private AlgorithmIdentifier encAlg;
   private AttributeTable unprotectedAttributes;
   private boolean attrNotRead;
   private OriginatorInformation originatorInfo;

   public CMSEnvelopedDataParser(byte[] var1) throws CMSException, IOException {
      this((InputStream)(new ByteArrayInputStream(var1)));
   }

   public CMSEnvelopedDataParser(InputStream var1) throws CMSException, IOException {
      super(var1);
      this.attrNotRead = true;
      this.envelopedData = new EnvelopedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
      OriginatorInfo var2 = this.envelopedData.getOriginatorInfo();
      if (var2 != null) {
         this.originatorInfo = new OriginatorInformation(var2);
      }

      ASN1Set var3 = ASN1Set.getInstance(this.envelopedData.getRecipientInfos().toASN1Primitive());
      EncryptedContentInfoParser var4 = this.envelopedData.getEncryptedContentInfo();
      this.encAlg = var4.getContentEncryptionAlgorithm();
      CMSProcessableInputStream var5 = new CMSProcessableInputStream(((ASN1OctetStringParser)var4.getEncryptedContent(4)).getOctetStream());
      CMSEnvelopedHelper.CMSEnvelopedSecureReadable var6 = new CMSEnvelopedHelper.CMSEnvelopedSecureReadable(this.encAlg, var5);
      this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var3, this.encAlg, var6);
   }

   public String getEncryptionAlgOID() {
      return this.encAlg.getAlgorithm().toString();
   }

   public byte[] getEncryptionAlgParams() {
      try {
         return this.encodeObj(this.encAlg.getParameters());
      } catch (Exception var2) {
         throw new RuntimeException("exception getting encryption parameters " + var2);
      }
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this.encAlg;
   }

   public OriginatorInformation getOriginatorInfo() {
      return this.originatorInfo;
   }

   public RecipientInformationStore getRecipientInfos() {
      return this.recipientInfoStore;
   }

   public AttributeTable getUnprotectedAttributes() throws IOException {
      if (this.unprotectedAttributes == null && this.attrNotRead) {
         ASN1SetParser var1 = this.envelopedData.getUnprotectedAttrs();
         this.attrNotRead = false;
         if (var1 != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();

            ASN1Encodable var3;
            while((var3 = var1.readObject()) != null) {
               ASN1SequenceParser var4 = (ASN1SequenceParser)var3;
               var2.add(var4.toASN1Primitive());
            }

            this.unprotectedAttributes = new AttributeTable(new DERSet(var2));
         }
      }

      return this.unprotectedAttributes;
   }

   private byte[] encodeObj(ASN1Encodable var1) throws IOException {
      return var1 != null ? var1.toASN1Primitive().getEncoded() : null;
   }
}
