package org.python.bouncycastle.cert.cmp;

import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.cmp.CMPCertificate;
import org.python.bouncycastle.asn1.cmp.CMPObjectIdentifiers;
import org.python.bouncycastle.asn1.cmp.PBMParameter;
import org.python.bouncycastle.asn1.cmp.PKIBody;
import org.python.bouncycastle.asn1.cmp.PKIHeader;
import org.python.bouncycastle.asn1.cmp.PKIMessage;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.crmf.PKMACBuilder;
import org.python.bouncycastle.operator.ContentVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.MacCalculator;
import org.python.bouncycastle.util.Arrays;

public class ProtectedPKIMessage {
   private PKIMessage pkiMessage;

   public ProtectedPKIMessage(GeneralPKIMessage var1) {
      if (!var1.hasProtection()) {
         throw new IllegalArgumentException("PKIMessage not protected");
      } else {
         this.pkiMessage = var1.toASN1Structure();
      }
   }

   ProtectedPKIMessage(PKIMessage var1) {
      if (var1.getHeader().getProtectionAlg() == null) {
         throw new IllegalArgumentException("PKIMessage not protected");
      } else {
         this.pkiMessage = var1;
      }
   }

   public PKIHeader getHeader() {
      return this.pkiMessage.getHeader();
   }

   public PKIBody getBody() {
      return this.pkiMessage.getBody();
   }

   public PKIMessage toASN1Structure() {
      return this.pkiMessage;
   }

   public boolean hasPasswordBasedMacProtection() {
      return this.pkiMessage.getHeader().getProtectionAlg().getAlgorithm().equals(CMPObjectIdentifiers.passwordBasedMac);
   }

   public X509CertificateHolder[] getCertificates() {
      CMPCertificate[] var1 = this.pkiMessage.getExtraCerts();
      if (var1 == null) {
         return new X509CertificateHolder[0];
      } else {
         X509CertificateHolder[] var2 = new X509CertificateHolder[var1.length];

         for(int var3 = 0; var3 != var1.length; ++var3) {
            var2[var3] = new X509CertificateHolder(var1[var3].getX509v3PKCert());
         }

         return var2;
      }
   }

   public boolean verify(ContentVerifierProvider var1) throws CMPException {
      try {
         ContentVerifier var2 = var1.get(this.pkiMessage.getHeader().getProtectionAlg());
         return this.verifySignature(this.pkiMessage.getProtection().getBytes(), var2);
      } catch (Exception var4) {
         throw new CMPException("unable to verify signature: " + var4.getMessage(), var4);
      }
   }

   public boolean verify(PKMACBuilder var1, char[] var2) throws CMPException {
      if (!CMPObjectIdentifiers.passwordBasedMac.equals(this.pkiMessage.getHeader().getProtectionAlg().getAlgorithm())) {
         throw new CMPException("protection algorithm not mac based");
      } else {
         try {
            var1.setParameters(PBMParameter.getInstance(this.pkiMessage.getHeader().getProtectionAlg().getParameters()));
            MacCalculator var3 = var1.build(var2);
            OutputStream var4 = var3.getOutputStream();
            ASN1EncodableVector var5 = new ASN1EncodableVector();
            var5.add(this.pkiMessage.getHeader());
            var5.add(this.pkiMessage.getBody());
            var4.write((new DERSequence(var5)).getEncoded("DER"));
            var4.close();
            return Arrays.areEqual(var3.getMac(), this.pkiMessage.getProtection().getBytes());
         } catch (Exception var6) {
            throw new CMPException("unable to verify MAC: " + var6.getMessage(), var6);
         }
      }
   }

   private boolean verifySignature(byte[] var1, ContentVerifier var2) throws IOException {
      ASN1EncodableVector var3 = new ASN1EncodableVector();
      var3.add(this.pkiMessage.getHeader());
      var3.add(this.pkiMessage.getBody());
      OutputStream var4 = var2.getOutputStream();
      var4.write((new DERSequence(var3)).getEncoded("DER"));
      var4.close();
      return var2.verify(var1);
   }
}
