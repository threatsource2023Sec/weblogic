package org.python.bouncycastle.cert.jcajce;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.AuthorityKeyIdentifier;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.GeneralNames;
import org.python.bouncycastle.asn1.x509.SubjectKeyIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.cert.X509ExtensionUtils;
import org.python.bouncycastle.operator.DigestCalculator;

public class JcaX509ExtensionUtils extends X509ExtensionUtils {
   public JcaX509ExtensionUtils() throws NoSuchAlgorithmException {
      super(new SHA1DigestCalculator(MessageDigest.getInstance("SHA1")));
   }

   public JcaX509ExtensionUtils(DigestCalculator var1) {
      super(var1);
   }

   public AuthorityKeyIdentifier createAuthorityKeyIdentifier(X509Certificate var1) throws CertificateEncodingException {
      return super.createAuthorityKeyIdentifier((X509CertificateHolder)(new JcaX509CertificateHolder(var1)));
   }

   public AuthorityKeyIdentifier createAuthorityKeyIdentifier(PublicKey var1) {
      return super.createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(var1.getEncoded()));
   }

   public AuthorityKeyIdentifier createAuthorityKeyIdentifier(PublicKey var1, X500Principal var2, BigInteger var3) {
      return super.createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(var1.getEncoded()), new GeneralNames(new GeneralName(X500Name.getInstance(var2.getEncoded()))), var3);
   }

   public AuthorityKeyIdentifier createAuthorityKeyIdentifier(PublicKey var1, GeneralNames var2, BigInteger var3) {
      return super.createAuthorityKeyIdentifier(SubjectPublicKeyInfo.getInstance(var1.getEncoded()), var2, var3);
   }

   public SubjectKeyIdentifier createSubjectKeyIdentifier(PublicKey var1) {
      return super.createSubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(var1.getEncoded()));
   }

   public SubjectKeyIdentifier createTruncatedSubjectKeyIdentifier(PublicKey var1) {
      return super.createTruncatedSubjectKeyIdentifier(SubjectPublicKeyInfo.getInstance(var1.getEncoded()));
   }

   public static ASN1Primitive parseExtensionValue(byte[] var0) throws IOException {
      return ASN1Primitive.fromByteArray(ASN1OctetString.getInstance(var0).getOctets());
   }

   private static class SHA1DigestCalculator implements DigestCalculator {
      private ByteArrayOutputStream bOut = new ByteArrayOutputStream();
      private MessageDigest digest;

      public SHA1DigestCalculator(MessageDigest var1) {
         this.digest = var1;
      }

      public AlgorithmIdentifier getAlgorithmIdentifier() {
         return new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1);
      }

      public OutputStream getOutputStream() {
         return this.bOut;
      }

      public byte[] getDigest() {
         byte[] var1 = this.digest.digest(this.bOut.toByteArray());
         this.bOut.reset();
         return var1;
      }
   }
}
