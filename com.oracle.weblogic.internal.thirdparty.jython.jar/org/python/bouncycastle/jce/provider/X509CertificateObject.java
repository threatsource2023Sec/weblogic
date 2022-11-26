package org.python.bouncycastle.jce.provider;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.python.bouncycastle.asn1.ASN1Encodable;
import org.python.bouncycastle.asn1.ASN1InputStream;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.ASN1OutputStream;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1String;
import org.python.bouncycastle.asn1.DERBitString;
import org.python.bouncycastle.asn1.DERIA5String;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.python.bouncycastle.asn1.misc.NetscapeCertType;
import org.python.bouncycastle.asn1.misc.NetscapeRevocationURL;
import org.python.bouncycastle.asn1.misc.VerisignCzagExtension;
import org.python.bouncycastle.asn1.util.ASN1Dump;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x500.style.RFC4519Style;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.BasicConstraints;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.asn1.x509.Extension;
import org.python.bouncycastle.asn1.x509.Extensions;
import org.python.bouncycastle.asn1.x509.GeneralName;
import org.python.bouncycastle.asn1.x509.KeyUsage;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.python.bouncycastle.jce.X509Principal;
import org.python.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.python.bouncycastle.util.Arrays;
import org.python.bouncycastle.util.Integers;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.encoders.Hex;

/** @deprecated */
public class X509CertificateObject extends X509Certificate implements PKCS12BagAttributeCarrier {
   private Certificate c;
   private BasicConstraints basicConstraints;
   private boolean[] keyUsage;
   private boolean hashValueSet;
   private int hashValue;
   private PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();

   public X509CertificateObject(Certificate var1) throws CertificateParsingException {
      this.c = var1;

      byte[] var2;
      try {
         var2 = this.getExtensionBytes("2.5.29.19");
         if (var2 != null) {
            this.basicConstraints = BasicConstraints.getInstance(ASN1Primitive.fromByteArray(var2));
         }
      } catch (Exception var6) {
         throw new CertificateParsingException("cannot construct BasicConstraints: " + var6);
      }

      try {
         var2 = this.getExtensionBytes("2.5.29.15");
         if (var2 != null) {
            DERBitString var3 = DERBitString.getInstance(ASN1Primitive.fromByteArray(var2));
            var2 = var3.getBytes();
            int var4 = var2.length * 8 - var3.getPadBits();
            this.keyUsage = new boolean[var4 < 9 ? 9 : var4];

            for(int var5 = 0; var5 != var4; ++var5) {
               this.keyUsage[var5] = (var2[var5 / 8] & 128 >>> var5 % 8) != 0;
            }
         } else {
            this.keyUsage = null;
         }

      } catch (Exception var7) {
         throw new CertificateParsingException("cannot construct KeyUsage: " + var7);
      }
   }

   public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
      this.checkValidity(new Date());
   }

   public void checkValidity(Date var1) throws CertificateExpiredException, CertificateNotYetValidException {
      if (var1.getTime() > this.getNotAfter().getTime()) {
         throw new CertificateExpiredException("certificate expired on " + this.c.getEndDate().getTime());
      } else if (var1.getTime() < this.getNotBefore().getTime()) {
         throw new CertificateNotYetValidException("certificate not valid till " + this.c.getStartDate().getTime());
      }
   }

   public int getVersion() {
      return this.c.getVersionNumber();
   }

   public BigInteger getSerialNumber() {
      return this.c.getSerialNumber().getValue();
   }

   public Principal getIssuerDN() {
      try {
         return new X509Principal(X500Name.getInstance(this.c.getIssuer().getEncoded()));
      } catch (IOException var2) {
         return null;
      }
   }

   public X500Principal getIssuerX500Principal() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         ASN1OutputStream var2 = new ASN1OutputStream(var1);
         var2.writeObject(this.c.getIssuer());
         return new X500Principal(var1.toByteArray());
      } catch (IOException var3) {
         throw new IllegalStateException("can't encode issuer DN");
      }
   }

   public Principal getSubjectDN() {
      return new X509Principal(X500Name.getInstance(this.c.getSubject().toASN1Primitive()));
   }

   public X500Principal getSubjectX500Principal() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         ASN1OutputStream var2 = new ASN1OutputStream(var1);
         var2.writeObject(this.c.getSubject());
         return new X500Principal(var1.toByteArray());
      } catch (IOException var3) {
         throw new IllegalStateException("can't encode issuer DN");
      }
   }

   public Date getNotBefore() {
      return this.c.getStartDate().getDate();
   }

   public Date getNotAfter() {
      return this.c.getEndDate().getDate();
   }

   public byte[] getTBSCertificate() throws CertificateEncodingException {
      try {
         return this.c.getTBSCertificate().getEncoded("DER");
      } catch (IOException var2) {
         throw new CertificateEncodingException(var2.toString());
      }
   }

   public byte[] getSignature() {
      return this.c.getSignature().getOctets();
   }

   public String getSigAlgName() {
      Provider var1 = Security.getProvider("BC");
      if (var1 != null) {
         String var2 = var1.getProperty("Alg.Alias.Signature." + this.getSigAlgOID());
         if (var2 != null) {
            return var2;
         }
      }

      Provider[] var5 = Security.getProviders();

      for(int var3 = 0; var3 != var5.length; ++var3) {
         String var4 = var5[var3].getProperty("Alg.Alias.Signature." + this.getSigAlgOID());
         if (var4 != null) {
            return var4;
         }
      }

      return this.getSigAlgOID();
   }

   public String getSigAlgOID() {
      return this.c.getSignatureAlgorithm().getAlgorithm().getId();
   }

   public byte[] getSigAlgParams() {
      if (this.c.getSignatureAlgorithm().getParameters() != null) {
         try {
            return this.c.getSignatureAlgorithm().getParameters().toASN1Primitive().getEncoded("DER");
         } catch (IOException var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean[] getIssuerUniqueID() {
      DERBitString var1 = this.c.getTBSCertificate().getIssuerUniqueId();
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

   public boolean[] getSubjectUniqueID() {
      DERBitString var1 = this.c.getTBSCertificate().getSubjectUniqueId();
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

   public boolean[] getKeyUsage() {
      return this.keyUsage;
   }

   public List getExtendedKeyUsage() throws CertificateParsingException {
      byte[] var1 = this.getExtensionBytes("2.5.29.37");
      if (var1 != null) {
         try {
            ASN1InputStream var2 = new ASN1InputStream(var1);
            ASN1Sequence var3 = (ASN1Sequence)var2.readObject();
            ArrayList var4 = new ArrayList();

            for(int var5 = 0; var5 != var3.size(); ++var5) {
               var4.add(((ASN1ObjectIdentifier)var3.getObjectAt(var5)).getId());
            }

            return Collections.unmodifiableList(var4);
         } catch (Exception var6) {
            throw new CertificateParsingException("error processing extended key usage extension");
         }
      } else {
         return null;
      }
   }

   public int getBasicConstraints() {
      if (this.basicConstraints != null) {
         if (this.basicConstraints.isCA()) {
            return this.basicConstraints.getPathLenConstraint() == null ? Integer.MAX_VALUE : this.basicConstraints.getPathLenConstraint().intValue();
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public Collection getSubjectAlternativeNames() throws CertificateParsingException {
      return getAlternativeNames(this.getExtensionBytes(Extension.subjectAlternativeName.getId()));
   }

   public Collection getIssuerAlternativeNames() throws CertificateParsingException {
      return getAlternativeNames(this.getExtensionBytes(Extension.issuerAlternativeName.getId()));
   }

   public Set getCriticalExtensionOIDs() {
      if (this.getVersion() == 3) {
         HashSet var1 = new HashSet();
         Extensions var2 = this.c.getTBSCertificate().getExtensions();
         if (var2 != null) {
            Enumeration var3 = var2.oids();

            while(var3.hasMoreElements()) {
               ASN1ObjectIdentifier var4 = (ASN1ObjectIdentifier)var3.nextElement();
               Extension var5 = var2.getExtension(var4);
               if (var5.isCritical()) {
                  var1.add(var4.getId());
               }
            }

            return var1;
         }
      }

      return null;
   }

   private byte[] getExtensionBytes(String var1) {
      Extensions var2 = this.c.getTBSCertificate().getExtensions();
      if (var2 != null) {
         Extension var3 = var2.getExtension(new ASN1ObjectIdentifier(var1));
         if (var3 != null) {
            return var3.getExtnValue().getOctets();
         }
      }

      return null;
   }

   public byte[] getExtensionValue(String var1) {
      Extensions var2 = this.c.getTBSCertificate().getExtensions();
      if (var2 != null) {
         Extension var3 = var2.getExtension(new ASN1ObjectIdentifier(var1));
         if (var3 != null) {
            try {
               return var3.getExtnValue().getEncoded();
            } catch (Exception var5) {
               throw new IllegalStateException("error parsing " + var5.toString());
            }
         }
      }

      return null;
   }

   public Set getNonCriticalExtensionOIDs() {
      if (this.getVersion() == 3) {
         HashSet var1 = new HashSet();
         Extensions var2 = this.c.getTBSCertificate().getExtensions();
         if (var2 != null) {
            Enumeration var3 = var2.oids();

            while(var3.hasMoreElements()) {
               ASN1ObjectIdentifier var4 = (ASN1ObjectIdentifier)var3.nextElement();
               Extension var5 = var2.getExtension(var4);
               if (!var5.isCritical()) {
                  var1.add(var4.getId());
               }
            }

            return var1;
         }
      }

      return null;
   }

   public boolean hasUnsupportedCriticalExtension() {
      if (this.getVersion() == 3) {
         Extensions var1 = this.c.getTBSCertificate().getExtensions();
         if (var1 != null) {
            Enumeration var2 = var1.oids();

            while(var2.hasMoreElements()) {
               ASN1ObjectIdentifier var3 = (ASN1ObjectIdentifier)var2.nextElement();
               String var4 = var3.getId();
               if (!var4.equals(RFC3280CertPathUtilities.KEY_USAGE) && !var4.equals(RFC3280CertPathUtilities.CERTIFICATE_POLICIES) && !var4.equals(RFC3280CertPathUtilities.POLICY_MAPPINGS) && !var4.equals(RFC3280CertPathUtilities.INHIBIT_ANY_POLICY) && !var4.equals(RFC3280CertPathUtilities.CRL_DISTRIBUTION_POINTS) && !var4.equals(RFC3280CertPathUtilities.ISSUING_DISTRIBUTION_POINT) && !var4.equals(RFC3280CertPathUtilities.DELTA_CRL_INDICATOR) && !var4.equals(RFC3280CertPathUtilities.POLICY_CONSTRAINTS) && !var4.equals(RFC3280CertPathUtilities.BASIC_CONSTRAINTS) && !var4.equals(RFC3280CertPathUtilities.SUBJECT_ALTERNATIVE_NAME) && !var4.equals(RFC3280CertPathUtilities.NAME_CONSTRAINTS)) {
                  Extension var5 = var1.getExtension(var3);
                  if (var5.isCritical()) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public PublicKey getPublicKey() {
      try {
         return BouncyCastleProvider.getPublicKey(this.c.getSubjectPublicKeyInfo());
      } catch (IOException var2) {
         return null;
      }
   }

   public byte[] getEncoded() throws CertificateEncodingException {
      try {
         return this.c.getEncoded("DER");
      } catch (IOException var2) {
         throw new CertificateEncodingException(var2.toString());
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof java.security.cert.Certificate)) {
         return false;
      } else {
         java.security.cert.Certificate var2 = (java.security.cert.Certificate)var1;

         try {
            byte[] var3 = this.getEncoded();
            byte[] var4 = var2.getEncoded();
            return Arrays.areEqual(var3, var4);
         } catch (CertificateEncodingException var5) {
            return false;
         }
      }
   }

   public synchronized int hashCode() {
      if (!this.hashValueSet) {
         this.hashValue = this.calculateHashCode();
         this.hashValueSet = true;
      }

      return this.hashValue;
   }

   private int calculateHashCode() {
      try {
         int var1 = 0;
         byte[] var2 = this.getEncoded();

         for(int var3 = 1; var3 < var2.length; ++var3) {
            var1 += var2[var3] * var3;
         }

         return var1;
      } catch (CertificateEncodingException var4) {
         return 0;
      }
   }

   public void setBagAttribute(ASN1ObjectIdentifier var1, ASN1Encodable var2) {
      this.attrCarrier.setBagAttribute(var1, var2);
   }

   public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier var1) {
      return this.attrCarrier.getBagAttribute(var1);
   }

   public Enumeration getBagAttributeKeys() {
      return this.attrCarrier.getBagAttributeKeys();
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      String var2 = Strings.lineSeparator();
      var1.append("  [0]         Version: ").append(this.getVersion()).append(var2);
      var1.append("         SerialNumber: ").append(this.getSerialNumber()).append(var2);
      var1.append("             IssuerDN: ").append(this.getIssuerDN()).append(var2);
      var1.append("           Start Date: ").append(this.getNotBefore()).append(var2);
      var1.append("           Final Date: ").append(this.getNotAfter()).append(var2);
      var1.append("            SubjectDN: ").append(this.getSubjectDN()).append(var2);
      var1.append("           Public Key: ").append(this.getPublicKey()).append(var2);
      var1.append("  Signature Algorithm: ").append(this.getSigAlgName()).append(var2);
      byte[] var3 = this.getSignature();
      var1.append("            Signature: ").append(new String(Hex.encode(var3, 0, 20))).append(var2);

      for(int var4 = 20; var4 < var3.length; var4 += 20) {
         if (var4 < var3.length - 20) {
            var1.append("                       ").append(new String(Hex.encode(var3, var4, 20))).append(var2);
         } else {
            var1.append("                       ").append(new String(Hex.encode(var3, var4, var3.length - var4))).append(var2);
         }
      }

      Extensions var12 = this.c.getTBSCertificate().getExtensions();
      if (var12 != null) {
         Enumeration var5 = var12.oids();
         if (var5.hasMoreElements()) {
            var1.append("       Extensions: \n");
         }

         while(var5.hasMoreElements()) {
            ASN1ObjectIdentifier var6 = (ASN1ObjectIdentifier)var5.nextElement();
            Extension var7 = var12.getExtension(var6);
            if (var7.getExtnValue() != null) {
               byte[] var8 = var7.getExtnValue().getOctets();
               ASN1InputStream var9 = new ASN1InputStream(var8);
               var1.append("                       critical(").append(var7.isCritical()).append(") ");

               try {
                  if (var6.equals(Extension.basicConstraints)) {
                     var1.append(BasicConstraints.getInstance(var9.readObject())).append(var2);
                  } else if (var6.equals(Extension.keyUsage)) {
                     var1.append(KeyUsage.getInstance(var9.readObject())).append(var2);
                  } else if (var6.equals(MiscObjectIdentifiers.netscapeCertType)) {
                     var1.append(new NetscapeCertType((DERBitString)var9.readObject())).append(var2);
                  } else if (var6.equals(MiscObjectIdentifiers.netscapeRevocationURL)) {
                     var1.append(new NetscapeRevocationURL((DERIA5String)var9.readObject())).append(var2);
                  } else if (var6.equals(MiscObjectIdentifiers.verisignCzagExtension)) {
                     var1.append(new VerisignCzagExtension((DERIA5String)var9.readObject())).append(var2);
                  } else {
                     var1.append(var6.getId());
                     var1.append(" value = ").append(ASN1Dump.dumpAsString(var9.readObject())).append(var2);
                  }
               } catch (Exception var11) {
                  var1.append(var6.getId());
                  var1.append(" value = ").append("*****").append(var2);
               }
            } else {
               var1.append(var2);
            }
         }
      }

      return var1.toString();
   }

   public final void verify(PublicKey var1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      String var2 = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());

      Signature var3;
      try {
         var3 = Signature.getInstance(var2, "BC");
      } catch (Exception var5) {
         var3 = Signature.getInstance(var2);
      }

      this.checkSignature(var1, var3);
   }

   public final void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
      String var3 = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
      Signature var4;
      if (var2 != null) {
         var4 = Signature.getInstance(var3, var2);
      } else {
         var4 = Signature.getInstance(var3);
      }

      this.checkSignature(var1, var4);
   }

   public final void verify(PublicKey var1, Provider var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
      String var3 = X509SignatureUtil.getSignatureName(this.c.getSignatureAlgorithm());
      Signature var4;
      if (var2 != null) {
         var4 = Signature.getInstance(var3, var2);
      } else {
         var4 = Signature.getInstance(var3);
      }

      this.checkSignature(var1, var4);
   }

   private void checkSignature(PublicKey var1, Signature var2) throws CertificateException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
      if (!this.isAlgIdEqual(this.c.getSignatureAlgorithm(), this.c.getTBSCertificate().getSignature())) {
         throw new CertificateException("signature algorithm in TBS cert not same as outer cert");
      } else {
         ASN1Encodable var3 = this.c.getSignatureAlgorithm().getParameters();
         X509SignatureUtil.setSignatureParameters(var2, var3);
         var2.initVerify(var1);
         var2.update(this.getTBSCertificate());
         if (!var2.verify(this.getSignature())) {
            throw new SignatureException("certificate does not verify with supplied key");
         }
      }
   }

   private boolean isAlgIdEqual(AlgorithmIdentifier var1, AlgorithmIdentifier var2) {
      if (!var1.getAlgorithm().equals(var2.getAlgorithm())) {
         return false;
      } else if (var1.getParameters() == null) {
         return var2.getParameters() == null || var2.getParameters().equals(DERNull.INSTANCE);
      } else if (var2.getParameters() == null) {
         return var1.getParameters() == null || var1.getParameters().equals(DERNull.INSTANCE);
      } else {
         return var1.getParameters().equals(var2.getParameters());
      }
   }

   private static Collection getAlternativeNames(byte[] var0) throws CertificateParsingException {
      if (var0 == null) {
         return null;
      } else {
         try {
            ArrayList var1 = new ArrayList();
            Enumeration var2 = ASN1Sequence.getInstance(var0).getObjects();

            while(var2.hasMoreElements()) {
               GeneralName var3 = GeneralName.getInstance(var2.nextElement());
               ArrayList var4 = new ArrayList();
               var4.add(Integers.valueOf(var3.getTagNo()));
               switch (var3.getTagNo()) {
                  case 0:
                  case 3:
                  case 5:
                     var4.add(var3.getEncoded());
                     break;
                  case 1:
                  case 2:
                  case 6:
                     var4.add(((ASN1String)var3.getName()).getString());
                     break;
                  case 4:
                     var4.add(X500Name.getInstance(RFC4519Style.INSTANCE, var3.getName()).toString());
                     break;
                  case 7:
                     byte[] var5 = DEROctetString.getInstance(var3.getName()).getOctets();

                     String var6;
                     try {
                        var6 = InetAddress.getByAddress(var5).getHostAddress();
                     } catch (UnknownHostException var8) {
                        continue;
                     }

                     var4.add(var6);
                     break;
                  case 8:
                     var4.add(ASN1ObjectIdentifier.getInstance(var3.getName()).getId());
                     break;
                  default:
                     throw new IOException("Bad tag number: " + var3.getTagNo());
               }

               var1.add(Collections.unmodifiableList(var4));
            }

            if (var1.size() == 0) {
               return null;
            } else {
               return Collections.unmodifiableCollection(var1);
            }
         } catch (Exception var9) {
            throw new CertificateParsingException(var9.getMessage());
         }
      }
   }
}
