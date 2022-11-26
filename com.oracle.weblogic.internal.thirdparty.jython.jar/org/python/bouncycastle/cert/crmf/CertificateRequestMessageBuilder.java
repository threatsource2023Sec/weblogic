package org.python.bouncycastle.cert.crmf;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1Null;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.crmf.AttributeTypeAndValue;
import org.python.bouncycastle.asn1.crmf.CertReqMsg;
import org.python.bouncycastle.asn1.crmf.CertRequest;
import org.python.bouncycastle.asn1.crmf.CertTemplate;
import org.python.bouncycastle.asn1.crmf.CertTemplateBuilder;
import org.python.bouncycastle.asn1.crmf.OptionalValidity;
import org.python.bouncycastle.asn1.crmf.POPOPrivKey;
import org.python.bouncycastle.asn1.crmf.ProofOfPossession;
import org.python.bouncycastle.asn1.crmf.SubsequentMessage;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x509.Time;
import org.python.bouncycastle.cert.CertIOException;
import org.python.bouncycastle.operator.ContentSigner;

public class CertificateRequestMessageBuilder {
   private final BigInteger certReqId;
   private ExtensionsGenerator extGenerator;
   private CertTemplateBuilder templateBuilder;
   private List controls;
   private ContentSigner popSigner;
   private PKMACBuilder pkmacBuilder;
   private char[] password;
   private GeneralName sender;
   private POPOPrivKey popoPrivKey;
   private ASN1Null popRaVerified;

   public CertificateRequestMessageBuilder(BigInteger var1) {
      this.certReqId = var1;
      this.extGenerator = new ExtensionsGenerator();
      this.templateBuilder = new CertTemplateBuilder();
      this.controls = new ArrayList();
   }

   public CertificateRequestMessageBuilder setPublicKey(SubjectPublicKeyInfo var1) {
      if (var1 != null) {
         this.templateBuilder.setPublicKey(var1);
      }

      return this;
   }

   public CertificateRequestMessageBuilder setIssuer(X500Name var1) {
      if (var1 != null) {
         this.templateBuilder.setIssuer(var1);
      }

      return this;
   }

   public CertificateRequestMessageBuilder setSubject(X500Name var1) {
      if (var1 != null) {
         this.templateBuilder.setSubject(var1);
      }

      return this;
   }

   public CertificateRequestMessageBuilder setSerialNumber(BigInteger var1) {
      if (var1 != null) {
         this.templateBuilder.setSerialNumber(new ASN1Integer(var1));
      }

      return this;
   }

   public CertificateRequestMessageBuilder setValidity(Date var1, Date var2) {
      this.templateBuilder.setValidity(new OptionalValidity(this.createTime(var1), this.createTime(var2)));
      return this;
   }

   private Time createTime(Date var1) {
      return var1 != null ? new Time(var1) : null;
   }

   public CertificateRequestMessageBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws CertIOException {
      CRMFUtil.addExtension(this.extGenerator, var1, var2, var3);
      return this;
   }

   public CertificateRequestMessageBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, byte[] var3) {
      this.extGenerator.addExtension(var1, var2, var3);
      return this;
   }

   public CertificateRequestMessageBuilder addControl(Control var1) {
      this.controls.add(var1);
      return this;
   }

   public CertificateRequestMessageBuilder setProofOfPossessionSigningKeySigner(ContentSigner var1) {
      if (this.popoPrivKey == null && this.popRaVerified == null) {
         this.popSigner = var1;
         return this;
      } else {
         throw new IllegalStateException("only one proof of possession allowed");
      }
   }

   public CertificateRequestMessageBuilder setProofOfPossessionSubsequentMessage(SubsequentMessage var1) {
      if (this.popSigner == null && this.popRaVerified == null) {
         this.popoPrivKey = new POPOPrivKey(var1);
         return this;
      } else {
         throw new IllegalStateException("only one proof of possession allowed");
      }
   }

   public CertificateRequestMessageBuilder setProofOfPossessionRaVerified() {
      if (this.popSigner == null && this.popoPrivKey == null) {
         this.popRaVerified = DERNull.INSTANCE;
         return this;
      } else {
         throw new IllegalStateException("only one proof of possession allowed");
      }
   }

   public CertificateRequestMessageBuilder setAuthInfoPKMAC(PKMACBuilder var1, char[] var2) {
      this.pkmacBuilder = var1;
      this.password = var2;
      return this;
   }

   public CertificateRequestMessageBuilder setAuthInfoSender(X500Name var1) {
      return this.setAuthInfoSender(new GeneralName(var1));
   }

   public CertificateRequestMessageBuilder setAuthInfoSender(GeneralName var1) {
      this.sender = var1;
      return this;
   }

   public CertificateRequestMessage build() throws CRMFException {
      ASN1EncodableVector var1 = new ASN1EncodableVector();
      var1.add(new ASN1Integer(this.certReqId));
      if (!this.extGenerator.isEmpty()) {
         this.templateBuilder.setExtensions(this.extGenerator.generate());
      }

      var1.add(this.templateBuilder.build());
      if (!this.controls.isEmpty()) {
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         Iterator var3 = this.controls.iterator();

         while(var3.hasNext()) {
            Control var4 = (Control)var3.next();
            var2.add(new AttributeTypeAndValue(var4.getType(), var4.getValue()));
         }

         var1.add(new DERSequence(var2));
      }

      CertRequest var7 = CertRequest.getInstance(new DERSequence(var1));
      var1 = new ASN1EncodableVector();
      var1.add(var7);
      if (this.popSigner != null) {
         CertTemplate var8 = var7.getCertTemplate();
         if (var8.getSubject() != null && var8.getPublicKey() != null) {
            ProofOfPossessionSigningKeyBuilder var10 = new ProofOfPossessionSigningKeyBuilder(var7);
            var1.add(new ProofOfPossession(var10.build(this.popSigner)));
         } else {
            SubjectPublicKeyInfo var9 = var7.getCertTemplate().getPublicKey();
            ProofOfPossessionSigningKeyBuilder var5 = new ProofOfPossessionSigningKeyBuilder(var9);
            if (this.sender != null) {
               var5.setSender(this.sender);
            } else {
               PKMACValueGenerator var6 = new PKMACValueGenerator(this.pkmacBuilder);
               var5.setPublicKeyMac(var6, this.password);
            }

            var1.add(new ProofOfPossession(var5.build(this.popSigner)));
         }
      } else if (this.popoPrivKey != null) {
         var1.add(new ProofOfPossession(2, this.popoPrivKey));
      } else if (this.popRaVerified != null) {
         var1.add(new ProofOfPossession());
      }

      return new CertificateRequestMessage(CertReqMsg.getInstance(new DERSequence(var1)));
   }
}
