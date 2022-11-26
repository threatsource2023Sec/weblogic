package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.cms.EncryptedContentInfo;
import org.python.bouncycastle.asn1.cms.EnvelopedData;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.util.Encodable;

public class CMSEnvelopedData implements Encodable {
   RecipientInformationStore recipientInfoStore;
   ContentInfo contentInfo;
   private AlgorithmIdentifier encAlg;
   private ASN1Set unprotectedAttributes;
   private OriginatorInformation originatorInfo;

   public CMSEnvelopedData(byte[] var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSEnvelopedData(InputStream var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSEnvelopedData(ContentInfo var1) throws CMSException {
      this.contentInfo = var1;

      try {
         EnvelopedData var2 = EnvelopedData.getInstance(var1.getContent());
         if (var2.getOriginatorInfo() != null) {
            this.originatorInfo = new OriginatorInformation(var2.getOriginatorInfo());
         }

         ASN1Set var3 = var2.getRecipientInfos();
         EncryptedContentInfo var4 = var2.getEncryptedContentInfo();
         this.encAlg = var4.getContentEncryptionAlgorithm();
         CMSProcessableByteArray var5 = new CMSProcessableByteArray(var4.getEncryptedContent().getOctets());
         CMSEnvelopedHelper.CMSEnvelopedSecureReadable var6 = new CMSEnvelopedHelper.CMSEnvelopedSecureReadable(this.encAlg, var5);
         this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var3, this.encAlg, var6);
         this.unprotectedAttributes = var2.getUnprotectedAttrs();
      } catch (ClassCastException var7) {
         throw new CMSException("Malformed content.", var7);
      } catch (IllegalArgumentException var8) {
         throw new CMSException("Malformed content.", var8);
      }
   }

   private byte[] encodeObj(ASN1Encodable var1) throws IOException {
      return var1 != null ? var1.toASN1Primitive().getEncoded() : null;
   }

   public OriginatorInformation getOriginatorInfo() {
      return this.originatorInfo;
   }

   public AlgorithmIdentifier getContentEncryptionAlgorithm() {
      return this.encAlg;
   }

   public String getEncryptionAlgOID() {
      return this.encAlg.getAlgorithm().getId();
   }

   public byte[] getEncryptionAlgParams() {
      try {
         return this.encodeObj(this.encAlg.getParameters());
      } catch (Exception var2) {
         throw new RuntimeException("exception getting encryption parameters " + var2);
      }
   }

   public RecipientInformationStore getRecipientInfos() {
      return this.recipientInfoStore;
   }

   public ContentInfo toASN1Structure() {
      return this.contentInfo;
   }

   public AttributeTable getUnprotectedAttributes() {
      return this.unprotectedAttributes == null ? null : new AttributeTable(this.unprotectedAttributes);
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }
}
