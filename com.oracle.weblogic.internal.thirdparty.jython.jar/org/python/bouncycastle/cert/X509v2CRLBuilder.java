package org.python.bouncycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1GeneralizedTime;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.Extensions;
import org.python.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.python.bouncycastle.asn1.x509.TBSCertList;
import org.python.bouncycastle.asn1.x509.Time;
import org.python.bouncycastle.asn1.x509.V2TBSCertListGenerator;
import org.python.bouncycastle.operator.ContentSigner;

public class X509v2CRLBuilder {
   private V2TBSCertListGenerator tbsGen = new V2TBSCertListGenerator();
   private ExtensionsGenerator extGenerator = new ExtensionsGenerator();

   public X509v2CRLBuilder(X500Name var1, Date var2) {
      this.tbsGen.setIssuer(var1);
      this.tbsGen.setThisUpdate(new Time(var2));
   }

   public X509v2CRLBuilder(X500Name var1, Date var2, Locale var3) {
      this.tbsGen.setIssuer(var1);
      this.tbsGen.setThisUpdate(new Time(var2, var3));
   }

   public X509v2CRLBuilder(X500Name var1, Time var2) {
      this.tbsGen.setIssuer(var1);
      this.tbsGen.setThisUpdate(var2);
   }

   public X509v2CRLBuilder setNextUpdate(Date var1) {
      return this.setNextUpdate(new Time(var1));
   }

   public X509v2CRLBuilder setNextUpdate(Date var1, Locale var2) {
      return this.setNextUpdate(new Time(var1, var2));
   }

   public X509v2CRLBuilder setNextUpdate(Time var1) {
      this.tbsGen.setNextUpdate(var1);
      return this;
   }

   public X509v2CRLBuilder addCRLEntry(BigInteger var1, Date var2, int var3) {
      this.tbsGen.addCRLEntry(new ASN1Integer(var1), new Time(var2), var3);
      return this;
   }

   public X509v2CRLBuilder addCRLEntry(BigInteger var1, Date var2, int var3, Date var4) {
      this.tbsGen.addCRLEntry(new ASN1Integer(var1), new Time(var2), var3, new ASN1GeneralizedTime(var4));
      return this;
   }

   public X509v2CRLBuilder addCRLEntry(BigInteger var1, Date var2, Extensions var3) {
      this.tbsGen.addCRLEntry(new ASN1Integer(var1), new Time(var2), var3);
      return this;
   }

   public X509v2CRLBuilder addCRL(X509CRLHolder var1) {
      TBSCertList var2 = var1.toASN1Structure().getTBSCertList();
      if (var2 != null) {
         Enumeration var3 = var2.getRevokedCertificateEnumeration();

         while(var3.hasMoreElements()) {
            this.tbsGen.addCRLEntry(ASN1Sequence.getInstance(((ASN1Encodable)var3.nextElement()).toASN1Primitive()));
         }
      }

      return this;
   }

   public X509v2CRLBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, ASN1Encodable var3) throws CertIOException {
      CertUtils.addExtension(this.extGenerator, var1, var2, var3);
      return this;
   }

   public X509v2CRLBuilder addExtension(ASN1ObjectIdentifier var1, boolean var2, byte[] var3) throws CertIOException {
      this.extGenerator.addExtension(var1, var2, var3);
      return this;
   }

   public X509v2CRLBuilder addExtension(Extension var1) throws CertIOException {
      this.extGenerator.addExtension(var1);
      return this;
   }

   public X509CRLHolder build(ContentSigner var1) {
      this.tbsGen.setSignature(var1.getAlgorithmIdentifier());
      if (!this.extGenerator.isEmpty()) {
         this.tbsGen.setExtensions(this.extGenerator.generate());
      }

      return CertUtils.generateFullCRL(var1, this.tbsGen.generateTBSCertList());
   }
}
