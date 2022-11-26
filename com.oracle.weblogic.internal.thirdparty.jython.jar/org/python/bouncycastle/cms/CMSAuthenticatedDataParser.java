package org.python.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1OctetStringParser;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.cms.AttributeTable;
import org.python.bouncycastle.asn1.cms.AuthenticatedDataParser;
import org.python.bouncycastle.asn1.cms.CMSAttributes;
import org.python.bouncycastle.asn1.cms.ContentInfoParser;
import org.python.bouncycastle.asn1.cms.OriginatorInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Arrays;

public class CMSAuthenticatedDataParser extends CMSContentInfoParser {
   RecipientInformationStore recipientInfoStore;
   AuthenticatedDataParser authData;
   private AlgorithmIdentifier macAlg;
   private byte[] mac;
   private AttributeTable authAttrs;
   private ASN1Set authAttrSet;
   private AttributeTable unauthAttrs;
   private boolean authAttrNotRead;
   private boolean unauthAttrNotRead;
   private OriginatorInformation originatorInfo;

   public CMSAuthenticatedDataParser(byte[] var1) throws CMSException, IOException {
      this((InputStream)(new ByteArrayInputStream(var1)));
   }

   public CMSAuthenticatedDataParser(byte[] var1, DigestCalculatorProvider var2) throws CMSException, IOException {
      this((InputStream)(new ByteArrayInputStream(var1)), var2);
   }

   public CMSAuthenticatedDataParser(InputStream var1) throws CMSException, IOException {
      this((InputStream)var1, (DigestCalculatorProvider)null);
   }

   public CMSAuthenticatedDataParser(InputStream var1, DigestCalculatorProvider var2) throws CMSException, IOException {
      super(var1);
      this.authAttrNotRead = true;
      this.authData = new AuthenticatedDataParser((ASN1SequenceParser)this._contentInfo.getContent(16));
      OriginatorInfo var3 = this.authData.getOriginatorInfo();
      if (var3 != null) {
         this.originatorInfo = new OriginatorInformation(var3);
      }

      ASN1Set var4 = ASN1Set.getInstance(this.authData.getRecipientInfos().toASN1Primitive());
      this.macAlg = this.authData.getMacAlgorithm();
      AlgorithmIdentifier var5 = this.authData.getDigestAlgorithm();
      ContentInfoParser var6;
      CMSProcessableInputStream var7;
      if (var5 != null) {
         if (var2 == null) {
            throw new CMSException("a digest calculator provider is required if authenticated attributes are present");
         }

         var6 = this.authData.getEncapsulatedContentInfo();
         var7 = new CMSProcessableInputStream(((ASN1OctetStringParser)var6.getContent(4)).getOctetStream());

         try {
            CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable var8 = new CMSEnvelopedHelper.CMSDigestAuthenticatedSecureReadable(var2.get(var5), var7);
            this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var4, this.macAlg, var8, new AuthAttributesProvider() {
               public ASN1Set getAuthAttributes() {
                  try {
                     return CMSAuthenticatedDataParser.this.getAuthAttrSet();
                  } catch (IOException var2) {
                     throw new IllegalStateException("can't parse authenticated attributes!");
                  }
               }
            });
         } catch (OperatorCreationException var9) {
            throw new CMSException("unable to create digest calculator: " + var9.getMessage(), var9);
         }
      } else {
         var6 = this.authData.getEncapsulatedContentInfo();
         var7 = new CMSProcessableInputStream(((ASN1OctetStringParser)var6.getContent(4)).getOctetStream());
         CMSEnvelopedHelper.CMSAuthenticatedSecureReadable var10 = new CMSEnvelopedHelper.CMSAuthenticatedSecureReadable(this.macAlg, var7);
         this.recipientInfoStore = CMSEnvelopedHelper.buildRecipientInformationStore(var4, this.macAlg, var10);
      }

   }

   public OriginatorInformation getOriginatorInfo() {
      return this.originatorInfo;
   }

   public AlgorithmIdentifier getMacAlgorithm() {
      return this.macAlg;
   }

   public String getMacAlgOID() {
      return this.macAlg.getAlgorithm().toString();
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

   public byte[] getMac() throws IOException {
      if (this.mac == null) {
         this.getAuthAttrs();
         this.mac = this.authData.getMac().getOctets();
      }

      return Arrays.clone(this.mac);
   }

   private ASN1Set getAuthAttrSet() throws IOException {
      if (this.authAttrs == null && this.authAttrNotRead) {
         ASN1SetParser var1 = this.authData.getAuthAttrs();
         if (var1 != null) {
            this.authAttrSet = (ASN1Set)var1.toASN1Primitive();
         }

         this.authAttrNotRead = false;
      }

      return this.authAttrSet;
   }

   public AttributeTable getAuthAttrs() throws IOException {
      if (this.authAttrs == null && this.authAttrNotRead) {
         ASN1Set var1 = this.getAuthAttrSet();
         if (var1 != null) {
            this.authAttrs = new AttributeTable(var1);
         }
      }

      return this.authAttrs;
   }

   public AttributeTable getUnauthAttrs() throws IOException {
      if (this.unauthAttrs == null && this.unauthAttrNotRead) {
         ASN1SetParser var1 = this.authData.getUnauthAttrs();
         this.unauthAttrNotRead = false;
         if (var1 != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();

            ASN1Encodable var3;
            while((var3 = var1.readObject()) != null) {
               ASN1SequenceParser var4 = (ASN1SequenceParser)var3;
               var2.add(var4.toASN1Primitive());
            }

            this.unauthAttrs = new AttributeTable(new DERSet(var2));
         }
      }

      return this.unauthAttrs;
   }

   private byte[] encodeObj(ASN1Encodable var1) throws IOException {
      return var1 != null ? var1.toASN1Primitive().getEncoded() : null;
   }

   public byte[] getContentDigest() {
      return this.authAttrs != null ? ASN1OctetString.getInstance(this.authAttrs.get(CMSAttributes.messageDigest).getAttrValues().getObjectAt(0)).getOctets() : null;
   }
}
