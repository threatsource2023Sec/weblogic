package org.opensaml.security.x509;

import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.net.InetAddresses;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CRLException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.security.auth.x500.X500Principal;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.x509.extension.X509ExtensionUtil;
import org.cryptacular.util.CertUtil;
import org.cryptacular.util.CodecUtil;
import org.cryptacular.x509.GeneralNameType;
import org.cryptacular.x509.dn.NameReader;
import org.cryptacular.x509.dn.RDNSequence;
import org.cryptacular.x509.dn.StandardAttributeType;
import org.opensaml.security.SecurityException;
import org.opensaml.security.crypto.KeySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X509Support {
   public static final String CN_OID = "2.5.4.3";
   public static final String SKI_OID = "2.5.29.14";
   public static final Integer OTHER_ALT_NAME = new Integer(0);
   public static final Integer RFC822_ALT_NAME = new Integer(1);
   public static final Integer DNS_ALT_NAME = new Integer(2);
   public static final Integer X400ADDRESS_ALT_NAME = new Integer(3);
   public static final Integer DIRECTORY_ALT_NAME = new Integer(4);
   public static final Integer EDI_PARTY_ALT_NAME = new Integer(5);
   public static final Integer URI_ALT_NAME = new Integer(6);
   public static final Integer IP_ADDRESS_ALT_NAME = new Integer(7);
   public static final Integer REGISTERED_ID_ALT_NAME = new Integer(8);

   protected X509Support() {
   }

   @Nullable
   public static X509Certificate determineEntityCertificate(@Nullable Collection certs, @Nullable PrivateKey privateKey) throws SecurityException {
      if (certs != null && privateKey != null) {
         Iterator var2 = certs.iterator();

         while(var2.hasNext()) {
            X509Certificate certificate = (X509Certificate)var2.next();

            try {
               if (KeySupport.matchKeyPair(certificate.getPublicKey(), privateKey)) {
                  return certificate;
               }
            } catch (SecurityException var5) {
            }
         }

         return null;
      } else {
         return null;
      }
   }

   @Nullable
   public static List getCommonNames(@Nullable X500Principal dn) {
      if (dn == null) {
         return null;
      } else {
         Logger log = getLogger();
         log.debug("Extracting CNs from the following DN: {}", dn.toString());
         RDNSequence attrs = NameReader.readX500Principal(dn);
         List values = new ArrayList(attrs.getValues(StandardAttributeType.CommonName));
         Collections.reverse(values);
         return values;
      }
   }

   @Nullable
   public static List getAltNames(@Nullable X509Certificate certificate, @Nullable Integer[] nameTypes) {
      if (certificate != null && nameTypes != null && nameTypes.length != 0) {
         List altNames = new LinkedList();
         GeneralNameType[] types = new GeneralNameType[nameTypes.length];

         for(int i = 0; i < nameTypes.length; ++i) {
            types[i] = GeneralNameType.fromTagNumber(nameTypes[i]);
         }

         GeneralNames names = CertUtil.subjectAltNames(certificate, types);
         if (names != null) {
            GeneralName[] var5 = names.getNames();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               GeneralName name = var5[var7];
               altNames.add(convertAltNameType(name.getTagNo(), name.getName().toASN1Primitive()));
            }
         }

         return altNames;
      } else {
         return null;
      }
   }

   @Nullable
   public static List getSubjectNames(@Nullable X509Certificate certificate, @Nullable Integer[] altNameTypes) {
      List issuerNames = new LinkedList();
      if (certificate != null) {
         List entityCertCNs = getCommonNames(certificate.getSubjectX500Principal());
         if (entityCertCNs != null && !entityCertCNs.isEmpty()) {
            issuerNames.add(entityCertCNs.get(0));
         }

         List entityAltNames = getAltNames(certificate, altNameTypes);
         if (entityAltNames != null) {
            issuerNames.addAll(entityAltNames);
         }
      }

      return issuerNames;
   }

   @Nullable
   public static byte[] getSubjectKeyIdentifier(@Nonnull X509Certificate certificate) {
      byte[] derValue = certificate.getExtensionValue("2.5.29.14");
      if (derValue != null && derValue.length != 0) {
         try {
            ASN1Primitive ski = X509ExtensionUtil.fromExtensionValue(derValue);
            return ((DEROctetString)ski).getOctets();
         } catch (IOException var3) {
            getLogger().error("Unable to extract subject key identifier from certificate: ASN.1 parsing failed: " + var3);
            return null;
         }
      } else {
         return null;
      }
   }

   @Nonnull
   public static byte[] getX509Digest(@Nonnull X509Certificate certificate, @Nonnull String jcaAlgorithm) throws SecurityException {
      try {
         MessageDigest hasher = MessageDigest.getInstance(jcaAlgorithm);
         return hasher.digest(certificate.getEncoded());
      } catch (CertificateEncodingException var3) {
         getLogger().error("Unable to encode certificate for digest operation", var3);
         throw new SecurityException("Unable to encode certificate for digest operation", var3);
      } catch (NoSuchAlgorithmException var4) {
         getLogger().error("Algorithm {} is unsupported", jcaAlgorithm);
         throw new SecurityException("Algorithm " + jcaAlgorithm + " is unsupported", var4);
      }
   }

   @Nullable
   public static Collection decodeCertificates(@Nonnull File certs) throws CertificateException {
      Constraint.isNotNull(certs, "Input file cannot be null");
      if (!certs.exists()) {
         throw new CertificateException("Certificate file " + certs.getAbsolutePath() + " does not exist");
      } else if (!certs.canRead()) {
         throw new CertificateException("Certificate file " + certs.getAbsolutePath() + " is not readable");
      } else {
         try {
            return decodeCertificates(Files.toByteArray(certs));
         } catch (IOException var2) {
            throw new CertificateException("Error reading certificate file " + certs.getAbsolutePath(), var2);
         }
      }
   }

   @Nullable
   public static Collection decodeCertificates(@Nonnull InputStream certs) throws CertificateException {
      Constraint.isNotNull(certs, "Input Stream cannot be null");

      try {
         return decodeCertificates(ByteStreams.toByteArray(certs));
      } catch (IOException var2) {
         throw new CertificateException("Error reading certificate file", var2);
      }
   }

   @Nullable
   public static Collection decodeCertificates(@Nonnull byte[] certs) throws CertificateException {
      return Arrays.asList(CertUtil.decodeCertificateChain(certs));
   }

   @Nullable
   public static X509Certificate decodeCertificate(@Nonnull File cert) throws CertificateException {
      Constraint.isNotNull(cert, "Input file cannot be null");
      if (!cert.exists()) {
         throw new CertificateException("Certificate file " + cert.getAbsolutePath() + " does not exist");
      } else if (!cert.canRead()) {
         throw new CertificateException("Certificate file " + cert.getAbsolutePath() + " is not readable");
      } else {
         try {
            return decodeCertificate(Files.toByteArray(cert));
         } catch (IOException var2) {
            throw new CertificateException("Error reading certificate file " + cert.getAbsolutePath(), var2);
         }
      }
   }

   @Nullable
   public static X509Certificate decodeCertificate(@Nonnull byte[] cert) throws CertificateException {
      try {
         return CertUtil.decodeCertificate(cert);
      } catch (IllegalArgumentException var2) {
         throw new CertificateException(var2);
      }
   }

   @Nullable
   public static X509Certificate decodeCertificate(@Nonnull String base64Cert) throws CertificateException {
      return decodeCertificate(Base64Support.decode(base64Cert));
   }

   @Nullable
   public static Collection decodeCRLs(@Nonnull File crls) throws CRLException {
      Constraint.isNotNull(crls, "Input file cannot be null");
      if (!crls.exists()) {
         throw new CRLException("CRL file " + crls.getAbsolutePath() + " does not exist");
      } else if (!crls.canRead()) {
         throw new CRLException("CRL file " + crls.getAbsolutePath() + " is not readable");
      } else {
         try {
            return decodeCRLs(Files.toByteArray(crls));
         } catch (IOException var2) {
            throw new CRLException("Error reading CRL file " + crls.getAbsolutePath(), var2);
         }
      }
   }

   @Nullable
   public static Collection decodeCRLs(@Nonnull InputStream crls) throws CRLException {
      Constraint.isNotNull(crls, "Input stream cannot be null");

      try {
         return decodeCRLs(ByteStreams.toByteArray(crls));
      } catch (IOException var2) {
         throw new CRLException("Error reading CRL", var2);
      }
   }

   @Nullable
   public static Collection decodeCRLs(@Nonnull byte[] crls) throws CRLException {
      try {
         CertificateFactory cf = CertificateFactory.getInstance("X.509");
         return cf.generateCRLs(new ByteArrayInputStream(crls));
      } catch (GeneralSecurityException var2) {
         throw new CRLException("Unable to decode X.509 certificates");
      }
   }

   @Nullable
   public static X509CRL decodeCRL(@Nonnull String base64CRL) throws CertificateException, CRLException {
      CertificateFactory cf = CertificateFactory.getInstance("X.509");
      ByteArrayInputStream input = new ByteArrayInputStream(Base64Support.decode(base64CRL));
      return (X509CRL)cf.generateCRL(input);
   }

   @Nonnull
   public static String getIdentifiersToken(@Nonnull X509Credential credential, @Nullable X500DNHandler handler) {
      Constraint.isNotNull(credential, "Credential cannot be null");
      Object x500DNHandler;
      if (handler != null) {
         x500DNHandler = handler;
      } else {
         x500DNHandler = new InternalX500DNHandler();
      }

      X500Principal x500Principal = credential.getEntityCertificate().getSubjectX500Principal();
      StringBuilder builder = new StringBuilder();
      builder.append('[');
      builder.append(String.format("subjectName='%s'", ((X500DNHandler)x500DNHandler).getName(x500Principal)));
      if (!Strings.isNullOrEmpty(credential.getEntityId())) {
         builder.append(String.format(" |credential entityID='%s'", StringSupport.trimOrNull(credential.getEntityId())));
      }

      builder.append(']');
      return builder.toString();
   }

   @Nullable
   private static Object convertAltNameType(@Nonnull Integer nameType, @Nonnull ASN1Primitive nameValue) {
      Logger log = getLogger();
      if (!DIRECTORY_ALT_NAME.equals(nameType) && !DNS_ALT_NAME.equals(nameType) && !RFC822_ALT_NAME.equals(nameType) && !URI_ALT_NAME.equals(nameType) && !REGISTERED_ID_ALT_NAME.equals(nameType)) {
         if (IP_ADDRESS_ALT_NAME.equals(nameType)) {
            byte[] nameValueBytes = ((DEROctetString)nameValue).getOctets();

            try {
               return InetAddresses.toAddrString(InetAddress.getByAddress(nameValueBytes));
            } catch (UnknownHostException var5) {
               log.warn("Was unable to convert IP address alt name byte[] to string: " + CodecUtil.hex(nameValueBytes, true), var5);
               return null;
            }
         } else if (!EDI_PARTY_ALT_NAME.equals(nameType) && !X400ADDRESS_ALT_NAME.equals(nameType) && !OTHER_ALT_NAME.equals(nameType)) {
            log.warn("Encountered unknown alt name type '{}', adding as-is", nameType);
            return nameValue;
         } else {
            return nameValue;
         }
      } else {
         return nameValue.toString();
      }
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(X509Support.class);
   }
}
