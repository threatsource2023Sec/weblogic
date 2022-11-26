package org.python.bouncycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.asn1.x509.Time;
import org.python.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.python.bouncycastle.operator.ContentSigner;

public class X509v3CertificateBuilder {
   private V3TBSCertificateGenerator tbsGen;
   private ExtensionsGenerator extGenerator;

   public X509v3CertificateBuilder(X500Name var1, BigInteger var2, Date var3, Date var4, X500Name var5, SubjectPublicKeyInfo var6) {
      this(var1, var2, new Time(var3), new Time(var4), var5, var6);
   }

   public X509v3CertificateBuilder(X500Name var1, BigInteger var2, Date var3, Date var4, Locale var5, X500Name var6, SubjectPublicKeyInfo var7) {
      this(var1, var2, new Time(var3, var5), new Time(var4, var5), var6, var7);
   }

   public X509v3CertificateBuilder(X500Name var1, BigInteger var2, Time var3, Time var4, X500Name var5, SubjectPublicKeyInfo var6) {
      this.tbsGen = new V3TBSCertificateGenerator();
      this.tbsGen.setSerialNumber(new ASN1Integer(var2));
      this.tbsGen.setIssuer(var1);
      this.tbsGen.setStartDate(var3);
      this.tbsGen.setEndDate(var4);
      this.tbsGen.setSubject(var5);
      this.tbsGen.setSubjectPublicKeyInfo(var6);
      this.extGenerator = new ExtensionsGenerator();
   }

   public X509v3CertificateBuilder setSubjectUniqueID(boolean[] var1) {
      this.tbsGen.setSubjectUniqueID(CertUtils.booleanToBitString(var1));
      return this;
   }

   public X509v3CertificateBuilder setIssuerUniqueID(boolean[] var1) {
      this.tbsGen.setIssuerUniqueID(CertUtils.booleanToBitString(var1));
      return this;
   }

   public X509v3CertificateBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws CertIOException {
      CertUtils.addExtension(this.extGenerator, var1, var2, var3);
      return this;
   }

   public X509v3CertificateBuilder addExtension(Extension var1) throws CertIOException {
      this.extGenerator.addExtension(var1);
      return this;
   }

   public X509v3CertificateBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, byte[] var3) throws CertIOException {
      this.extGenerator.addExtension(var1, var2, var3);
      return this;
   }

   public X509v3CertificateBuilder copyAndAddExtension(ASN1ObjectIdentifier var1, boolean var2, X509CertificateHolder var3) {
      Certificate var4 = var3.toASN1Structure();
      Extension var5 = var4.getTBSCertificate().getExtensions().getExtension(var1);
      if (var5 == null) {
         throw new NullPointerException("extension " + var1 + " not present");
      } else {
         this.extGenerator.addExtension(var1, var2, var5.getExtnValue().getOctets());
         return this;
      }
   }

   public X509CertificateHolder build(ContentSigner var1) {
      this.tbsGen.setSignature(var1.getAlgorithmIdentifier());
      if (!this.extGenerator.isEmpty()) {
         this.tbsGen.setExtensions(this.extGenerator.generate());
      }

      return CertUtils.generateFullCert(var1, this.tbsGen.generateTBSCertificate());
   }
}
