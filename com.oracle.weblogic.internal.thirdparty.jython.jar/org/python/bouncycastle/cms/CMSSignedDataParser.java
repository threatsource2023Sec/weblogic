package org.python.bouncycastle.cms;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Generator;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OctetStringParser;
import org.python.bouncycastle.asn1.ASN1SequenceParser;
import org.python.bouncycastle.asn1.ASN1Set;
import org.python.bouncycastle.asn1.ASN1SetParser;
import org.python.bouncycastle.asn1.ASN1StreamParser;
import org.python.bouncycastle.asn1.BERSequenceGenerator;
import org.python.bouncycastle.asn1.BERSetParser;
import org.python.bouncycastle.asn1.BERTaggedObject;
import org.python.bouncycastle.asn1.DERSet;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.python.bouncycastle.asn1.cms.ContentInfoParser;
import org.python.bouncycastle.asn1.cms.SignedDataParser;
import org.python.bouncycastle.asn1.cms.SignerInfo;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.operator.DigestCalculator;
import org.python.bouncycastle.operator.DigestCalculatorProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.io.Streams;

public class CMSSignedDataParser extends CMSContentInfoParser {
   private static final CMSSignedHelper HELPER;
   private SignedDataParser _signedData;
   private ASN1ObjectIdentifier _signedContentType;
   private CMSTypedStream _signedContent;
   private Map digests;
   private Set digestAlgorithms;
   private SignerInformationStore _signerInfoStore;
   private ASN1Set _certSet;
   private ASN1Set _crlSet;
   private boolean _isCertCrlParsed;

   public CMSSignedDataParser(DigestCalculatorProvider var1, byte[] var2) throws CMSException {
      this(var1, (InputStream)(new ByteArrayInputStream(var2)));
   }

   public CMSSignedDataParser(DigestCalculatorProvider var1, CMSTypedStream var2, byte[] var3) throws CMSException {
      this(var1, var2, (InputStream)(new ByteArrayInputStream(var3)));
   }

   public CMSSignedDataParser(DigestCalculatorProvider var1, InputStream var2) throws CMSException {
      this(var1, (CMSTypedStream)null, (InputStream)var2);
   }

   public CMSSignedDataParser(DigestCalculatorProvider var1, CMSTypedStream var2, InputStream var3) throws CMSException {
      super(var3);

      try {
         this._signedContent = var2;
         this._signedData = SignedDataParser.getInstance(this._contentInfo.getContent(16));
         this.digests = new HashMap();
         ASN1SetParser var4 = this._signedData.getDigestAlgorithms();
         HashSet var5 = new HashSet();

         ASN1Encodable var6;
         while((var6 = var4.readObject()) != null) {
            AlgorithmIdentifier var7 = AlgorithmIdentifier.getInstance(var6);
            var5.add(var7);

            try {
               DigestCalculator var8 = var1.get(var7);
               if (var8 != null) {
                  this.digests.put(var7.getAlgorithm(), var8);
               }
            } catch (OperatorCreationException var11) {
            }
         }

         this.digestAlgorithms = Collections.unmodifiableSet(var5);
         ContentInfoParser var13 = this._signedData.getEncapContentInfo();
         ASN1Encodable var14 = var13.getContent(4);
         if (var14 instanceof ASN1OctetStringParser) {
            ASN1OctetStringParser var9 = (ASN1OctetStringParser)var14;
            CMSTypedStream var10 = new CMSTypedStream(var13.getContentType(), var9.getOctetStream());
            if (this._signedContent == null) {
               this._signedContent = var10;
            } else {
               var10.drain();
            }
         } else if (var14 != null) {
            PKCS7TypedStream var15 = new PKCS7TypedStream(var13.getContentType(), var14);
            if (this._signedContent == null) {
               this._signedContent = var15;
            } else {
               var15.drain();
            }
         }

         if (var2 == null) {
            this._signedContentType = var13.getContentType();
         } else {
            this._signedContentType = this._signedContent.getContentType();
         }

      } catch (IOException var12) {
         throw new CMSException("io exception: " + var12.getMessage(), var12);
      }
   }

   public int getVersion() {
      return this._signedData.getVersion().getValue().intValue();
   }

   public Set getDigestAlgorithmIDs() {
      return this.digestAlgorithms;
   }

   public SignerInformationStore getSignerInfos() throws CMSException {
      if (this._signerInfoStore == null) {
         this.populateCertCrlSets();
         ArrayList var1 = new ArrayList();
         HashMap var2 = new HashMap();
         Iterator var3 = this.digests.keySet().iterator();

         while(var3.hasNext()) {
            Object var4 = var3.next();
            var2.put(var4, ((DigestCalculator)this.digests.get(var4)).getDigest());
         }

         try {
            ASN1SetParser var9 = this._signedData.getSignerInfos();

            ASN1Encodable var5;
            while((var5 = var9.readObject()) != null) {
               SignerInfo var6 = SignerInfo.getInstance(var5.toASN1Primitive());
               byte[] var7 = (byte[])((byte[])var2.get(var6.getDigestAlgorithm().getAlgorithm()));
               var1.add(new SignerInformation(var6, this._signedContentType, (CMSProcessable)null, var7));
            }
         } catch (IOException var8) {
            throw new CMSException("io exception: " + var8.getMessage(), var8);
         }

         this._signerInfoStore = new SignerInformationStore(var1);
      }

      return this._signerInfoStore;
   }

   public Store getCertificates() throws CMSException {
      this.populateCertCrlSets();
      return HELPER.getCertificates(this._certSet);
   }

   public Store getCRLs() throws CMSException {
      this.populateCertCrlSets();
      return HELPER.getCRLs(this._crlSet);
   }

   public Store getAttributeCertificates() throws CMSException {
      this.populateCertCrlSets();
      return HELPER.getAttributeCertificates(this._certSet);
   }

   public Store getOtherRevocationInfo(ASN1ObjectIdentifier var1) throws CMSException {
      this.populateCertCrlSets();
      return HELPER.getOtherRevocationInfo(var1, this._crlSet);
   }

   private void populateCertCrlSets() throws CMSException {
      if (!this._isCertCrlParsed) {
         this._isCertCrlParsed = true;

         try {
            this._certSet = getASN1Set(this._signedData.getCertificates());
            this._crlSet = getASN1Set(this._signedData.getCrls());
         } catch (IOException var2) {
            throw new CMSException("problem parsing cert/crl sets", var2);
         }
      }
   }

   public String getSignedContentTypeOID() {
      return this._signedContentType.getId();
   }

   public CMSTypedStream getSignedContent() {
      if (this._signedContent == null) {
         return null;
      } else {
         InputStream var1 = CMSUtils.attachDigestsToInputStream(this.digests.values(), this._signedContent.getContentStream());
         return new CMSTypedStream(this._signedContent.getContentType(), var1);
      }
   }

   public static OutputStream replaceSigners(InputStream var0, SignerInformationStore var1, OutputStream var2) throws CMSException, IOException {
      ASN1StreamParser var3 = new ASN1StreamParser(var0);
      ContentInfoParser var4 = new ContentInfoParser((ASN1SequenceParser)var3.readObject());
      SignedDataParser var5 = SignedDataParser.getInstance(var4.getContent(16));
      BERSequenceGenerator var6 = new BERSequenceGenerator(var2);
      var6.addObject(CMSObjectIdentifiers.signedData);
      BERSequenceGenerator var7 = new BERSequenceGenerator(var6.getRawOutputStream(), 0, true);
      var7.addObject(var5.getVersion());
      var5.getDigestAlgorithms().toASN1Primitive();
      ASN1EncodableVector var8 = new ASN1EncodableVector();
      Iterator var9 = var1.getSigners().iterator();

      while(var9.hasNext()) {
         SignerInformation var10 = (SignerInformation)var9.next();
         var8.add(CMSSignedHelper.INSTANCE.fixAlgID(var10.getDigestAlgorithmID()));
      }

      var7.getRawOutputStream().write((new DERSet(var8)).getEncoded());
      ContentInfoParser var15 = var5.getEncapContentInfo();
      BERSequenceGenerator var14 = new BERSequenceGenerator(var7.getRawOutputStream());
      var14.addObject(var15.getContentType());
      pipeEncapsulatedOctetString(var15, var14.getRawOutputStream());
      var14.close();
      writeSetToGeneratorTagged(var7, var5.getCertificates(), 0);
      writeSetToGeneratorTagged(var7, var5.getCrls(), 1);
      ASN1EncodableVector var11 = new ASN1EncodableVector();
      Iterator var12 = var1.getSigners().iterator();

      while(var12.hasNext()) {
         SignerInformation var13 = (SignerInformation)var12.next();
         var11.add(var13.toASN1Structure());
      }

      var7.getRawOutputStream().write((new DERSet(var11)).getEncoded());
      var7.close();
      var6.close();
      return var2;
   }

   public static OutputStream replaceCertificatesAndCRLs(InputStream var0, Store var1, Store var2, Store var3, OutputStream var4) throws CMSException, IOException {
      ASN1StreamParser var5 = new ASN1StreamParser(var0);
      ContentInfoParser var6 = new ContentInfoParser((ASN1SequenceParser)var5.readObject());
      SignedDataParser var7 = SignedDataParser.getInstance(var6.getContent(16));
      BERSequenceGenerator var8 = new BERSequenceGenerator(var4);
      var8.addObject(CMSObjectIdentifiers.signedData);
      BERSequenceGenerator var9 = new BERSequenceGenerator(var8.getRawOutputStream(), 0, true);
      var9.addObject(var7.getVersion());
      var9.getRawOutputStream().write(var7.getDigestAlgorithms().toASN1Primitive().getEncoded());
      ContentInfoParser var10 = var7.getEncapContentInfo();
      BERSequenceGenerator var11 = new BERSequenceGenerator(var9.getRawOutputStream());
      var11.addObject(var10.getContentType());
      pipeEncapsulatedOctetString(var10, var11.getRawOutputStream());
      var11.close();
      getASN1Set(var7.getCertificates());
      getASN1Set(var7.getCrls());
      if (var1 != null || var3 != null) {
         ArrayList var12 = new ArrayList();
         if (var1 != null) {
            var12.addAll(CMSUtils.getCertificatesFromStore(var1));
         }

         if (var3 != null) {
            var12.addAll(CMSUtils.getAttributeCertificatesFromStore(var3));
         }

         ASN1Set var13 = CMSUtils.createBerSetFromList(var12);
         if (var13.size() > 0) {
            var9.getRawOutputStream().write((new DERTaggedObject(false, 0, var13)).getEncoded());
         }
      }

      if (var2 != null) {
         ASN1Set var14 = CMSUtils.createBerSetFromList(CMSUtils.getCRLsFromStore(var2));
         if (var14.size() > 0) {
            var9.getRawOutputStream().write((new DERTaggedObject(false, 1, var14)).getEncoded());
         }
      }

      var9.getRawOutputStream().write(var7.getSignerInfos().toASN1Primitive().getEncoded());
      var9.close();
      var8.close();
      return var4;
   }

   private static void writeSetToGeneratorTagged(ASN1Generator var0, ASN1SetParser var1, int var2) throws IOException {
      ASN1Set var3 = getASN1Set(var1);
      if (var3 != null) {
         if (var1 instanceof BERSetParser) {
            var0.getRawOutputStream().write((new BERTaggedObject(false, var2, var3)).getEncoded());
         } else {
            var0.getRawOutputStream().write((new DERTaggedObject(false, var2, var3)).getEncoded());
         }
      }

   }

   private static ASN1Set getASN1Set(ASN1SetParser var0) {
      return var0 == null ? null : ASN1Set.getInstance(var0.toASN1Primitive());
   }

   private static void pipeEncapsulatedOctetString(ContentInfoParser var0, OutputStream var1) throws IOException {
      ASN1OctetStringParser var2 = (ASN1OctetStringParser)var0.getContent(4);
      if (var2 != null) {
         pipeOctetString(var2, var1);
      }

   }

   private static void pipeOctetString(ASN1OctetStringParser var0, OutputStream var1) throws IOException {
      OutputStream var2 = CMSUtils.createBEROctetOutputStream(var1, 0, true, 0);
      Streams.pipeAll(var0.getOctetStream(), var2);
      var2.close();
   }

   static {
      HELPER = CMSSignedHelper.INSTANCE;
   }
}
