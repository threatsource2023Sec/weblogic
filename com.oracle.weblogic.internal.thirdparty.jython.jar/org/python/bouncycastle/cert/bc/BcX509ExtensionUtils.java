package org.python.bouncycastle.cert.bc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.python.bouncycastle.cert.X509ExtensionUtils;
import org.python.bouncycastle.crypto.digests.SHA1Digest;
import org.python.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.python.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.python.bouncycastle.operator.DigestCalculator;

public class BcX509ExtensionUtils extends X509ExtensionUtils {
   public BcX509ExtensionUtils() {
      super(new SHA1DigestCalculator());
   }

   public BcX509ExtensionUtils(DigestCalculator var1) {
      super(var1);
   }

   public AuthorityKeyIdentifier createAuthorityKeyIdentifier(AsymmetricKeyParameter var1) throws IOException {
      return super.createAuthorityKeyIdentifier(SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(var1));
   }

   public SubjectKeyIdentifier createSubjectKeyIdentifier(AsymmetricKeyParameter var1) throws IOException {
      return super.createSubjectKeyIdentifier(SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(var1));
   }

   private static class SHA1DigestCalculator implements DigestCalculator {
      private ByteArrayOutputStream bOut;

      private SHA1DigestCalculator() {
         this.bOut = new ByteArrayOutputStream();
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
      }

      public OutputStream getOutputStream() {
         return this.bOut;
      }

      public byte[] getDigest() {
         byte[] var1 = this.bOut.toByteArray();
         this.bOut.reset();
         SHA1Digest var2 = new SHA1Digest();
         var2.update(var1, 0, var1.length);
         byte[] var3 = new byte[var2.getDigestSize()];
         var2.doFinal(var3, 0);
         return var3;
      }

      // $FF: synthetic method
      SHA1DigestCalculator(Object var1) {
         this();
      }
   }
}
