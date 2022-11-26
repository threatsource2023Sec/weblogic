package org.python.bouncycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cmp.CMPCertificate;
import org.python.bouncycastle.asn1.cmp.InfoTypeAndValue;
import org.python.bouncycastle.asn1.cmp.PKIBody;
import org.python.bouncycastle.asn1.cmp.PKIFreeText;
import org.python.bouncycastle.asn1.cmp.PKIHeader;
import org.python.bouncycastle.asn1.cmp.PKIHeaderBuilder;
import org.python.bouncycastle.asn1.cmp.PKIMessage;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.ContentSigner;
import org.python.bouncycastle.operator.MacCalculator;

public class ProtectedPKIMessageBuilder {
   private PKIHeaderBuilder hdrBuilder;
   private PKIBody body;
   private List generalInfos;
   private List extraCerts;

   public ProtectedPKIMessageBuilder(GeneralName var1, GeneralName var2) {
      this(2, var1, var2);
   }

   public ProtectedPKIMessageBuilder(int var1, GeneralName var2, GeneralName var3) {
      this.generalInfos = new ArrayList();
      this.extraCerts = new ArrayList();
      this.hdrBuilder = new PKIHeaderBuilder(var1, var2, var3);
   }

   public ProtectedPKIMessageBuilder setTransactionID(byte[] var1) {
      this.hdrBuilder.setTransactionID(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setFreeText(PKIFreeText var1) {
      this.hdrBuilder.setFreeText(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder addGeneralInfo(InfoTypeAndValue var1) {
      this.generalInfos.add(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setMessageTime(Date var1) {
      this.hdrBuilder.setMessageTime(new ASN1GeneralizedTime(var1));
      return this;
   }

   public ProtectedPKIMessageBuilder setRecipKID(byte[] var1) {
      this.hdrBuilder.setRecipKID(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setRecipNonce(byte[] var1) {
      this.hdrBuilder.setRecipNonce(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setSenderKID(byte[] var1) {
      this.hdrBuilder.setSenderKID(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setSenderNonce(byte[] var1) {
      this.hdrBuilder.setSenderNonce(var1);
      return this;
   }

   public ProtectedPKIMessageBuilder setBody(PKIBody var1) {
      this.body = var1;
      return this;
   }

   public ProtectedPKIMessageBuilder addCMPCertificate(X509CertificateHolder var1) {
      this.extraCerts.add(var1);
      return this;
   }

   public ProtectedPKIMessage build(MacCalculator var1) throws CMPException {
      this.finaliseHeader(var1.getAlgorithmIdentifier());
      PKIHeader var2 = this.hdrBuilder.build();

      try {
         DERBitString var3 = new DERBitString(this.calculateMac(var1, var2, this.body));
         return this.finaliseMessage(var2, var3);
      } catch (IOException var4) {
         throw new CMPException("unable to encode MAC input: " + var4.getMessage(), var4);
      }
   }

   public ProtectedPKIMessage build(ContentSigner var1) throws CMPException {
      this.finaliseHeader(var1.getAlgorithmIdentifier());
      PKIHeader var2 = this.hdrBuilder.build();

      try {
         DERBitString var3 = new DERBitString(this.calculateSignature(var1, var2, this.body));
         return this.finaliseMessage(var2, var3);
      } catch (IOException var4) {
         throw new CMPException("unable to encode signature input: " + var4.getMessage(), var4);
      }
   }

   private void finaliseHeader(AlgorithmIdentifier var1) {
      this.hdrBuilder.setProtectionAlg(var1);
      if (!this.generalInfos.isEmpty()) {
         InfoTypeAndValue[] var2 = new InfoTypeAndValue[this.generalInfos.size()];
         this.hdrBuilder.setGeneralInfo((InfoTypeAndValue[])((InfoTypeAndValue[])this.generalInfos.toArray(var2)));
      }

   }

   private ProtectedPKIMessage finaliseMessage(PKIHeader var1, DERBitString var2) {
      if (this.extraCerts.isEmpty()) {
         return new ProtectedPKIMessage(new PKIMessage(var1, this.body, var2));
      } else {
         CMPCertificate[] var3 = new CMPCertificate[this.extraCerts.size()];

         for(int var4 = 0; var4 != var3.length; ++var4) {
            var3[var4] = new CMPCertificate(((X509CertificateHolder)this.extraCerts.get(var4)).toASN1Structure());
         }

         return new ProtectedPKIMessage(new PKIMessage(var1, this.body, var2, var3));
      }
   }

   private byte[] calculateSignature(ContentSigner var1, PKIHeader var2, PKIBody var3) throws IOException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var2);
      var4.add(var3);
      OutputStream var5 = var1.getOutputStream();
      var5.write((new DERSequence(var4)).getEncoded("DER"));
      var5.close();
      return var1.getSignature();
   }

   private byte[] calculateMac(MacCalculator var1, PKIHeader var2, PKIBody var3) throws IOException {
      ASN1EncodableVector var4 = new ASN1EncodableVector();
      var4.add(var2);
      var4.add(var3);
      OutputStream var5 = var1.getOutputStream();
      var5.write((new DERSequence(var4)).getEncoded("DER"));
      var5.close();
      return var1.getMac();
   }
}
