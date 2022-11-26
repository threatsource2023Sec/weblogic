package org.python.bouncycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.x509.AttributeCertificate;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.Extensions;
import org.python.bouncycastle.util.Arrays;

/** @deprecated */
public class X509V2AttributeCertificate implements X509AttributeCertificate {
   private AttributeCertificate cert;
   private Date notBefore;
   private Date notAfter;

   private static AttributeCertificate getObject(InputStream var0) throws IOException {
      try {
         return AttributeCertificate.getInstance((new ASN1InputStream(var0)).readObject());
      } catch (IOException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new IOException("exception decoding certificate structure: " + var3.toString());
      }
   }

   public X509V2AttributeCertificate(InputStream var1) throws IOException {
      this(getObject(var1));
   }

   public X509V2AttributeCertificate(byte[] var1) throws IOException {
      this((InputStream)(new ByteArrayInputStream(var1)));
   }

   X509V2AttributeCertificate(AttributeCertificate var1) throws IOException {
      this.cert = var1;

      try {
         this.notAfter = var1.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime().getDate();
         this.notBefore = var1.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime().getDate();
      } catch (ParseException var3) {
         throw new IOException("invalid data structure in certificate!");
      }
   }

   public int getVersion() {
      return this.cert.getAcinfo().getVersion().getValue().intValue() + 1;
   }

   public BigInteger getSerialNumber() {
      return this.cert.getAcinfo().getSerialNumber().getValue();
   }

   public AttributeCertificateHolder getHolder() {
      return new AttributeCertificateHolder((ASN1Sequence)this.cert.getAcinfo().getHolder().toASN1Primitive());
   }

   public AttributeCertificateIssuer getIssuer() {
      return new AttributeCertificateIssuer(this.cert.getAcinfo().getIssuer());
   }

   public Date getNotBefore() {
      return this.notBefore;
   }

   public Date getNotAfter() {
      return this.notAfter;
   }

   public boolean[] getIssuerUniqueID() {
      DERBitString var1 = this.cert.getAcinfo().getIssuerUniqueID();
      if (var1 != null) {
         byte[] var2 = var1.getBytes();
         boolean[] var3 = new boolean[var2.length * 8 - var1.getPadBits()];

         for(int var4 = 0; var4 != var3.length; ++var4) {
            var3[var4] = (var2[var4 / 8] & 128 >>> var4 % 8) != 0;
         }

         return var3;
      } else {
         return null;
      }
   }

   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
      this.checkValidity(new Date());
   }

   public void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException {
      if (var1.after(this.getNotAfter())) {
         throw new CertificateExpiredException("certificate expired on " + this.getNotAfter());
      } else if (var1.before(this.getNotBefore())) {
         throw new CertificateNotYetValidException("certificate not valid till " + this.getNotBefore());
      }
   }

   public byte[] getSignature() {
      return this.cert.getSignatureValue().getOctets();
   }

   public final void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      Signature var3 = null;
      if (!this.cert.getSignatureAlgorithm().equals(this.cert.getAcinfo().getSignature())) {
         throw new CertificateException("Signature algorithm in certificate info not same as outer certificate");
      } else {
         var3 = Signature.getInstance(this.cert.getSignatureAlgorithm().getAlgorithm().getId(), var2);
         var3.initVerify(var1);

         try {
            var3.update(this.cert.getAcinfo().getEncoded());
         } catch (IOException var5) {
            throw new SignatureException("Exception encoding certificate info object");
         }

         if (!var3.verify(this.getSignature())) {
            throw new InvalidKeyException("Public key presented not for certificate signature");
         }
      }
   }

   public byte[] getEncoded() throws IOException {
      return this.cert.getEncoded();
   }

   public byte[] getExtensionValue(String var1) {
      Extensions var2 = this.cert.getAcinfo().getExtensions();
      if (var2 != null) {
         Extension var3 = var2.getExtension(new ASN1ObjectIdentifier(var1));
         if (var3 != null) {
            try {
               return var3.getExtnValue().getEncoded("DER");
            } catch (Exception var5) {
               throw new RuntimeException("error encoding " + var5.toString());
            }
         }
      }

      return null;
   }

   private Set getExtensionOIDs(boolean var1) {
      Extensions var2 = this.cert.getAcinfo().getExtensions();
      if (var2 != null) {
         HashSet var3 = new HashSet();
         Enumeration var4 = var2.oids();

         while(var4.hasMoreElements()) {
            ASN1ObjectIdentifier var5 = (ASN1ObjectIdentifier)var4.nextElement();
            Extension var6 = var2.getExtension(var5);
            if (var6.isCritical() == var1) {
               var3.add(var5.getId());
            }
         }

         return var3;
      } else {
         return null;
      }
   }

   public Set getNonCriticalExtensionOIDs() {
      return this.getExtensionOIDs(false);
   }

   public Set getCriticalExtensionOIDs() {
      return this.getExtensionOIDs(true);
   }

   public boolean hasUnsupportedCriticalExtension() {
      Set var1 = this.getCriticalExtensionOIDs();
      return var1 != null && !var1.isEmpty();
   }

   public X509Attribute[] getAttributes() {
      ASN1Sequence var1 = this.cert.getAcinfo().getAttributes();
      X509Attribute[] var2 = new X509Attribute[var1.size()];

      for(int var3 = 0; var3 != var1.size(); ++var3) {
         var2[var3] = new X509Attribute(var1.getObjectAt(var3));
      }

      return var2;
   }

   public X509Attribute[] getAttributes(String var1) {
      ASN1Sequence var2 = this.cert.getAcinfo().getAttributes();
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 != var2.size(); ++var4) {
         X509Attribute var5 = new X509Attribute(var2.getObjectAt(var4));
         if (var5.getOID().equals(var1)) {
            var3.add(var5);
         }
      }

      if (var3.size() == 0) {
         return null;
      } else {
         return (X509Attribute[])((X509Attribute[])var3.toArray(new X509Attribute[var3.size()]));
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof X509AttributeCertificate)) {
         return false;
      } else {
         X509AttributeCertificate var2 = (X509AttributeCertificate)var1;

         try {
            byte[] var3 = this.getEncoded();
            byte[] var4 = var2.getEncoded();
            return Arrays.areEqual(var3, var4);
         } catch (IOException var5) {
            return false;
         }
      }
   }

   public int hashCode() {
      try {
         return Arrays.hashCode(this.getEncoded());
      } catch (IOException var2) {
         return 0;
      }
   }
}
