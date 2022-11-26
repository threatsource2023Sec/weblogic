package org.python.bouncycastle.cert.crmf.jcajce;

import java.io.IOException;
import java.security.Provider;
import java.security.PublicKey;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.crmf.CertReqMsg;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.crmf.CRMFException;
import org.python.bouncycastle.cert.crmf.CertificateRequestMessage;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;

public class JcaCertificateRequestMessage extends CertificateRequestMessage {
   private CRMFHelper helper;

   public JcaCertificateRequestMessage(byte[] var1) {
      this(CertReqMsg.getInstance(var1));
   }

   public JcaCertificateRequestMessage(CertificateRequestMessage var1) {
      this(var1.toASN1Structure());
   }

   public JcaCertificateRequestMessage(CertReqMsg var1) {
      super(var1);
      this.helper = new CRMFHelper(new DefaultJcaJceHelper());
   }

   public JcaCertificateRequestMessage setProvider(String var1) {
      this.helper = new CRMFHelper(new NamedJcaJceHelper(var1));
      return this;
   }

   public JcaCertificateRequestMessage setProvider(Provider var1) {
      this.helper = new CRMFHelper(new ProviderJcaJceHelper(var1));
      return this;
   }

   public X500Principal getSubjectX500Principal() {
      X500Name var1 = this.getCertTemplate().getSubject();
      if (var1 != null) {
         try {
            return new X500Principal(var1.getEncoded("DER"));
         } catch (IOException var3) {
            throw new IllegalStateException("unable to construct DER encoding of name: " + var3.getMessage());
         }
      } else {
         return null;
      }
   }

   public PublicKey getPublicKey() throws CRMFException {
      SubjectPublicKeyInfo var1 = this.getCertTemplate().getPublicKey();
      return var1 != null ? this.helper.toPublicKey(var1) : null;
   }
}
