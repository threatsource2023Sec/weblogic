package org.python.bouncycastle.pkix;

import org.python.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.python.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cms.KeyTransRecipientId;
import org.python.bouncycastle.cms.RecipientId;

public class PKIXIdentity {
   private final PrivateKeyInfo privateKeyInfo;
   private final X509CertificateHolder[] certificateHolders;

   public PKIXIdentity(PrivateKeyInfo var1, X509CertificateHolder[] var2) {
      this.privateKeyInfo = var1;
      this.certificateHolders = new X509CertificateHolder[var2.length];
      System.arraycopy(var2, 0, this.certificateHolders, 0, var2.length);
   }

   public PrivateKeyInfo getPrivateKeyInfo() {
      return this.privateKeyInfo;
   }

   public X509CertificateHolder getCertificate() {
      return this.certificateHolders[0];
   }

   public RecipientId getRecipientId() {
      return new KeyTransRecipientId(this.certificateHolders[0].getIssuer(), this.certificateHolders[0].getSerialNumber(), this.getSubjectKeyIdentifier());
   }

   private byte[] getSubjectKeyIdentifier() {
      SubjectKeyIdentifier var1 = SubjectKeyIdentifier.fromExtensions(this.certificateHolders[0].getExtensions());
      return var1 == null ? null : var1.getKeyIdentifier();
   }
}
