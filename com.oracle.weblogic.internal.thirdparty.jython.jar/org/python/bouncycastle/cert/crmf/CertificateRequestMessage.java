package org.python.bouncycastle.cert.crmf;

import java.io.IOException;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.DERUTF8String;
import org.python.bouncycastle.asn1.crmf.AttributeTypeAndValue;
import org.python.bouncycastle.asn1.crmf.CRMFObjectIdentifiers;
import org.python.bouncycastle.asn1.crmf.CertReqMsg;
import org.python.bouncycastle.asn1.crmf.CertTemplate;
import org.python.bouncycastle.asn1.crmf.Controls;
import org.python.bouncycastle.asn1.crmf.PKIArchiveOptions;
import org.python.bouncycastle.asn1.crmf.PKMACValue;
import org.python.bouncycastle.asn1.crmf.POPOSigningKey;
import org.python.bouncycastle.asn1.crmf.ProofOfPossession;
import org.python.bouncycastle.cert.CertIOException;
import org.python.bouncycastle.operator.ContentVerifier;
import org.python.bouncycastle.operator.ContentVerifierProvider;
import org.python.bouncycastle.operator.OperatorCreationException;
import org.python.bouncycastle.util.Encodable;

public class CertificateRequestMessage implements Encodable {
   public static final int popRaVerified = 0;
   public static final int popSigningKey = 1;
   public static final int popKeyEncipherment = 2;
   public static final int popKeyAgreement = 3;
   private final CertReqMsg certReqMsg;
   private final Controls controls;

   private static CertReqMsg parseBytes(byte[] var0) throws IOException {
      try {
         return CertReqMsg.getInstance(ASN1Primitive.fromByteArray(var0));
      } catch (ClassCastException var2) {
         throw new CertIOException("malformed data: " + var2.getMessage(), var2);
      } catch (IllegalArgumentException var3) {
         throw new CertIOException("malformed data: " + var3.getMessage(), var3);
      }
   }

   public CertificateRequestMessage(byte[] var1) throws IOException {
      this(parseBytes(var1));
   }

   public CertificateRequestMessage(CertReqMsg var1) {
      this.certReqMsg = var1;
      this.controls = var1.getCertReq().getControls();
   }

   public CertReqMsg toASN1Structure() {
      return this.certReqMsg;
   }

   public CertTemplate getCertTemplate() {
      return this.certReqMsg.getCertReq().getCertTemplate();
   }

   public boolean hasControls() {
      return this.controls != null;
   }

   public boolean hasControl(ASN1ObjectIdentifier var1) {
      return this.findControl(var1) != null;
   }

   public Control getControl(ASN1ObjectIdentifier var1) {
      AttributeTypeAndValue var2 = this.findControl(var1);
      if (var2 != null) {
         if (var2.getType().equals(CRMFObjectIdentifiers.id_regCtrl_pkiArchiveOptions)) {
            return new PKIArchiveControl(PKIArchiveOptions.getInstance(var2.getValue()));
         }

         if (var2.getType().equals(CRMFObjectIdentifiers.id_regCtrl_regToken)) {
            return new RegTokenControl(DERUTF8String.getInstance(var2.getValue()));
         }

         if (var2.getType().equals(CRMFObjectIdentifiers.id_regCtrl_authenticator)) {
            return new AuthenticatorControl(DERUTF8String.getInstance(var2.getValue()));
         }
      }

      return null;
   }

   private AttributeTypeAndValue findControl(ASN1ObjectIdentifier var1) {
      if (this.controls == null) {
         return null;
      } else {
         AttributeTypeAndValue[] var2 = this.controls.toAttributeTypeAndValueArray();
         AttributeTypeAndValue var3 = null;

         for(int var4 = 0; var4 != var2.length; ++var4) {
            if (var2[var4].getType().equals(var1)) {
               var3 = var2[var4];
               break;
            }
         }

         return var3;
      }
   }

   public boolean hasProofOfPossession() {
      return this.certReqMsg.getPopo() != null;
   }

   public int getProofOfPossessionType() {
      return this.certReqMsg.getPopo().getType();
   }

   public boolean hasSigningKeyProofOfPossessionWithPKMAC() {
      ProofOfPossession var1 = this.certReqMsg.getPopo();
      if (var1.getType() == 1) {
         POPOSigningKey var2 = POPOSigningKey.getInstance(var1.getObject());
         return var2.getPoposkInput().getPublicKeyMAC() != null;
      } else {
         return false;
      }
   }

   public boolean isValidSigningKeyPOP(ContentVerifierProvider var1) throws CRMFException, IllegalStateException {
      ProofOfPossession var2 = this.certReqMsg.getPopo();
      if (var2.getType() == 1) {
         POPOSigningKey var3 = POPOSigningKey.getInstance(var2.getObject());
         if (var3.getPoposkInput() != null && var3.getPoposkInput().getPublicKeyMAC() != null) {
            throw new IllegalStateException("verification requires password check");
         } else {
            return this.verifySignature(var1, var3);
         }
      } else {
         throw new IllegalStateException("not Signing Key type of proof of possession");
      }
   }

   public boolean isValidSigningKeyPOP(ContentVerifierProvider var1, PKMACBuilder var2, char[] var3) throws CRMFException, IllegalStateException {
      ProofOfPossession var4 = this.certReqMsg.getPopo();
      if (var4.getType() == 1) {
         POPOSigningKey var5 = POPOSigningKey.getInstance(var4.getObject());
         if (var5.getPoposkInput() != null && var5.getPoposkInput().getSender() == null) {
            PKMACValue var6 = var5.getPoposkInput().getPublicKeyMAC();
            PKMACValueVerifier var7 = new PKMACValueVerifier(var2);
            return var7.isValid(var6, var3, this.getCertTemplate().getPublicKey()) ? this.verifySignature(var1, var5) : false;
         } else {
            throw new IllegalStateException("no PKMAC present in proof of possession");
         }
      } else {
         throw new IllegalStateException("not Signing Key type of proof of possession");
      }
   }

   private boolean verifySignature(ContentVerifierProvider var1, POPOSigningKey var2) throws CRMFException {
      ContentVerifier var3;
      try {
         var3 = var1.get(var2.getAlgorithmIdentifier());
      } catch (OperatorCreationException var5) {
         throw new CRMFException("unable to create verifier: " + var5.getMessage(), var5);
      }

      if (var2.getPoposkInput() != null) {
         CRMFUtil.derEncodeToStream(var2.getPoposkInput(), var3.getOutputStream());
      } else {
         CRMFUtil.derEncodeToStream(this.certReqMsg.getCertReq(), var3.getOutputStream());
      }

      return var3.verify(var2.getSignature().getOctets());
   }

   public byte[] getEncoded() throws IOException {
      return this.certReqMsg.getEncoded();
   }
}
