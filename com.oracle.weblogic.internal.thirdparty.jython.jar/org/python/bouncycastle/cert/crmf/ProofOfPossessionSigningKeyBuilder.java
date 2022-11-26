package org.python.bouncycastle.cert.crmf;

import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.crmf.CertRequest;
import org.python.bouncycastle.asn1.crmf.PKMACValue;
import org.python.bouncycastle.asn1.crmf.POPOSigningKey;
import org.python.bouncycastle.asn1.crmf.POPOSigningKeyInput;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.operator.ContentSigner;

public class ProofOfPossessionSigningKeyBuilder {
   private CertRequest certRequest;
   private SubjectPublicKeyInfo pubKeyInfo;
   private GeneralName name;
   private PKMACValue publicKeyMAC;

   public ProofOfPossessionSigningKeyBuilder(CertRequest var1) {
      this.certRequest = var1;
   }

   public ProofOfPossessionSigningKeyBuilder(SubjectPublicKeyInfo var1) {
      this.pubKeyInfo = var1;
   }

   public ProofOfPossessionSigningKeyBuilder setSender(GeneralName var1) {
      this.name = var1;
      return this;
   }

   public ProofOfPossessionSigningKeyBuilder setPublicKeyMac(PKMACValueGenerator var1, char[] var2) throws CRMFException {
      this.publicKeyMAC = var1.generate(var2, this.pubKeyInfo);
      return this;
   }

   public POPOSigningKey build(ContentSigner var1) {
      if (this.name != null && this.publicKeyMAC != null) {
         throw new IllegalStateException("name and publicKeyMAC cannot both be set.");
      } else {
         POPOSigningKeyInput var2;
         if (this.certRequest != null) {
            var2 = null;
            CRMFUtil.derEncodeToStream(this.certRequest, var1.getOutputStream());
         } else if (this.name != null) {
            var2 = new POPOSigningKeyInput(this.name, this.pubKeyInfo);
            CRMFUtil.derEncodeToStream(var2, var1.getOutputStream());
         } else {
            var2 = new POPOSigningKeyInput(this.publicKeyMAC, this.pubKeyInfo);
            CRMFUtil.derEncodeToStream(var2, var1.getOutputStream());
         }

         return new POPOSigningKey(var2, var1.getAlgorithmIdentifier(), new DERBitString(var1.getSignature()));
      }
   }
}
