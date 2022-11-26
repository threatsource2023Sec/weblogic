package org.python.bouncycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.cms.Attribute;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.AuthenticatedData;
import org.python.bouncycastle.asn1.cms.CMSAlgorithmProtection;
import org.python.bouncycastle.asn1.cms.CMSAttributes;
import org.python.bouncycastle.asn1.cms.ContentInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Encodable;

public class CMSAuthenticatedData implements Encodable {
   RecipientInformationStore recipientInfoStore;
   ContentInfo contentInfo;
   private AlgorithmIdentifier macAlg;
   private ASN1Set authAttrs;
   private ASN1Set unauthAttrs;
   private byte[] mac;
   private OriginatorInformation originatorInfo;

   public CMSAuthenticatedData(byte[] var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSAuthenticatedData(byte[] var1, DigestCalculatorProvider var2) throws CMSException {
      this(CMSUtils.readContentInfo(var1), var2);
   }

   public CMSAuthenticatedData(InputStream var1) throws CMSException {
      this(CMSUtils.readContentInfo(var1));
   }

   public CMSAuthenticatedData(InputStream var1, DigestCalculatorProvider var2) throws CMSException {
      this(CMSUtils.readContentInfo(var1), var2);
   }

   public CMSAuthenticatedData(ContentInfo var1) throws CMSException {
      this((ContentInfo)var1, (DigestCalculatorProvider)null);
   }

   public CMSAuthenticatedData(ContentInfo var1, DigestCalculatorProvider var2) throws CMSException {
      this.contentInfo = var1;
      AuthenticatedData var3 = AuthenticatedData.getInstance(var1.getContent());
      if (var3.getOriginatorInfo() != null) {
         this.originatorInfo = new OriginatorInformation(var3.getOriginatorInfo());
      }

      ASN1Set var4 = var3.getRecipientInfos();
      this.macAlg = var3.getMacAlgorithm();
      this.authAttrs = var3.getAuthAttrs();
      this.mac = var3.getMac().getOctets();
      this.unauthAttrs = var3.getUnauthAttrs();
      ContentInfo var5 = var3.getEncapsulatedContentInfo();
      CMSProcessableByteArray var6 = new CMSProcessableByteArray(ASN1OctetString.getInstance(var5.getContent()).getOctets());
      if (this.authAttrs != null) {
         if (var2 == null) {
            throw new CMSException("a digest calculator provider is required if authenticated attributes are present");
         }

         AttributeTable var7 = new AttributeTable(this.authAttrs);
         ASN1EncodableVector var8 = var7.getAll(CMSAttributes.cmsAlgorithmProtect);
         if (var8.size() > 1) {
            throw new CMSException("Only one instance of a cmsAlgorithmProtect attribute can be present");
         }

         if (var8.size() > 0) {
            Attribute var9 = Attribute.getInstance(var8.get(0));
            if (var9.getAttrValues().size() != 1) {
               throw new CMSException("A cmsAlgorithmProtect attribute MUST contain exactly one value");
            }

            CMSAlgorithmProtection var10 = CMSAlgorithmProtection.getInstance(var9.getAttributeValues()[0]);
            if (!CMSUtils.isEquivalent(var10.getDigestAlgorithm(), var3.getDigestAlgorithm())) {
               throw new CMSException("CMS Algorithm Identifier Protection check failed for digestAlgorithm");
            }

            if (!CMSUtils.isEquivalent(var10.getMacAlgorithm(), this.macAlg)) {
               throw new CMSException("CMS Algorithm Identifier Protection check failed for macAlgorithm");
            }
         }

         try {
            CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable var13 = new CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable(var2.get(var3.getDigestAlgorithm()), var6);
            this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var4, this.macAlg, var13, new AuthAttributesProvider() {
               public ASN1Set getAuthAttributes() {
                  return CMSAuthenticatedData.this.authAttrs;
               }
            });
         } catch (OperatorCreationException var11) {
            throw new CMSException("unable to create digest calculator: " + var11.getMessage(), var11);
         }
      } else {
         CMSEnvelopedHelper.CMSAuthenticatedSecureReadable var12 = new CMSEnvelopedHelper.CMSAuthenticatedSecureReadable(this.macAlg, var6);
         this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var4, this.macAlg, var12);
      }

   }

   public OriginatorInformation getOriginatorInfo() {
      return this.originatorInfo;
   }

   public byte[] getMac() {
      return Arrays.clone(this.mac);
   }

   private byte[] encodeObj(ASN1Encodable var1) throws IOException {
      return var1 != null ? var1.toASN1Primitive().getEncoded() : null;
   }

   public AlgorithmIdentifier getMacAlgorithm() {
      return this.macAlg;
   }

   public String getMacAlgOID() {
      return this.macAlg.getAlgorithm().getId();
   }

   public byte[] getMacAlgParams() {
      try {
         return this.encodeObj(this.macAlg.getParameters());
      } catch (Exception var2) {
         throw new RuntimeException("exception getting encryption parameters " + var2);
      }
   }

   public RecipientInformationStore getRecipientInfos() {
      return this.recipientInfoStore;
   }

   /** @deprecated */
   public ContentInfo getContentInfo() {
      return this.contentInfo;
   }

   public ContentInfo toASN1Structure() {
      return this.contentInfo;
   }

   public AttributeTable getAuthAttrs() {
      return this.authAttrs == null ? null : new AttributeTable(this.authAttrs);
   }

   public AttributeTable getUnauthAttrs() {
      return this.unauthAttrs == null ? null : new AttributeTable(this.unauthAttrs);
   }

   public byte[] getEncoded() throws IOException {
      return this.contentInfo.getEncoded();
   }

   public byte[] getContentDigest() {
      return this.authAttrs != null ? ASN1OctetString.getInstance(this.getAuthAttrs().get(CMSAttributes.messageDigest).getAttrValues().getObjectAt(0)).getOctets() : null;
   }
}
