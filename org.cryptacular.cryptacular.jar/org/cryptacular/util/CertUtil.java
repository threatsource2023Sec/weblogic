package org.cryptacular.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.GeneralNamesBuilder;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.PolicyInformation;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.x509.ExtensionReader;
import org.cryptacular.x509.GeneralNameType;
import org.cryptacular.x509.KeyUsageBits;
import org.cryptacular.x509.dn.NameReader;
import org.cryptacular.x509.dn.StandardAttributeType;

public final class CertUtil {
   private CertUtil() {
   }

   public static String subjectCN(X509Certificate cert) throws EncodingException {
      return (new NameReader(cert)).readSubject().getValue(StandardAttributeType.CommonName);
   }

   public static GeneralNames subjectAltNames(X509Certificate cert) throws EncodingException {
      return (new ExtensionReader(cert)).readSubjectAlternativeName();
   }

   public static GeneralNames subjectAltNames(X509Certificate cert, GeneralNameType... types) throws EncodingException {
      GeneralNamesBuilder builder = new GeneralNamesBuilder();
      GeneralNames altNames = subjectAltNames(cert);
      if (altNames != null) {
         GeneralName[] var4 = altNames.getNames();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            GeneralName name = var4[var6];
            GeneralNameType[] var8 = types;
            int var9 = types.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               GeneralNameType type = var8[var10];
               if (type.ordinal() == name.getTagNo()) {
                  builder.addName(name);
               }
            }
         }
      }

      GeneralNames names = builder.build();
      return names.getNames().length == 0 ? null : names;
   }

   public static List subjectNames(X509Certificate cert) throws EncodingException {
      List names = new ArrayList();
      String cn = subjectCN(cert);
      if (cn != null) {
         names.add(cn);
      }

      GeneralNames altNames = subjectAltNames(cert);
      if (altNames == null) {
         return names;
      } else {
         GeneralName[] var4 = altNames.getNames();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            GeneralName name = var4[var6];
            names.add(name.getName().toString());
         }

         return names;
      }
   }

   public static List subjectNames(X509Certificate cert, GeneralNameType... types) throws EncodingException {
      List names = new ArrayList();
      String cn = subjectCN(cert);
      if (cn != null) {
         names.add(cn);
      }

      GeneralNames altNames = subjectAltNames(cert, types);
      if (altNames == null) {
         return names;
      } else {
         GeneralName[] var5 = altNames.getNames();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            GeneralName name = var5[var7];
            names.add(name.getName().toString());
         }

         return names;
      }
   }

   public static X509Certificate findEntityCertificate(PrivateKey key, X509Certificate... candidates) throws EncodingException {
      return findEntityCertificate(key, (Collection)Arrays.asList(candidates));
   }

   public static X509Certificate findEntityCertificate(PrivateKey key, Collection candidates) throws EncodingException {
      Iterator var2 = candidates.iterator();

      X509Certificate candidate;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         candidate = (X509Certificate)var2.next();
      } while(!KeyPairUtil.isKeyPair(candidate.getPublicKey(), key));

      return candidate;
   }

   public static X509Certificate readCertificate(String path) throws EncodingException, StreamException {
      return readCertificate(StreamUtil.makeStream(new File(path)));
   }

   public static X509Certificate readCertificate(File file) throws EncodingException, StreamException {
      return readCertificate(StreamUtil.makeStream(file));
   }

   public static X509Certificate readCertificate(InputStream in) throws EncodingException, StreamException {
      try {
         CertificateFactory factory = CertificateFactory.getInstance("X.509");
         return (X509Certificate)factory.generateCertificate(in);
      } catch (CertificateException var2) {
         if (var2.getCause() instanceof IOException) {
            throw new StreamException((IOException)var2.getCause());
         } else {
            throw new EncodingException("Cannot decode certificate", var2);
         }
      }
   }

   public static X509Certificate decodeCertificate(byte[] encoded) throws EncodingException {
      return readCertificate((InputStream)(new ByteArrayInputStream(encoded)));
   }

   public static X509Certificate[] readCertificateChain(String path) throws EncodingException, StreamException {
      return readCertificateChain(StreamUtil.makeStream(new File(path)));
   }

   public static X509Certificate[] readCertificateChain(File file) throws EncodingException, StreamException {
      return readCertificateChain(StreamUtil.makeStream(file));
   }

   public static X509Certificate[] readCertificateChain(InputStream in) throws EncodingException, StreamException {
      try {
         CertificateFactory factory = CertificateFactory.getInstance("X.509");
         Collection certs = factory.generateCertificates(in);
         return (X509Certificate[])certs.toArray(new X509Certificate[certs.size()]);
      } catch (CertificateException var3) {
         if (var3.getCause() instanceof IOException) {
            throw new StreamException((IOException)var3.getCause());
         } else {
            throw new EncodingException("Cannot decode certificate", var3);
         }
      }
   }

   public static X509Certificate[] decodeCertificateChain(byte[] encoded) throws EncodingException {
      return readCertificateChain((InputStream)(new ByteArrayInputStream(encoded)));
   }

   public static boolean allowsUsage(X509Certificate cert, KeyUsageBits... bits) throws EncodingException {
      KeyUsage usage = (new ExtensionReader(cert)).readKeyUsage();
      KeyUsageBits[] var3 = bits;
      int var4 = bits.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         KeyUsageBits bit = var3[var5];
         if (!bit.isSet(usage)) {
            return false;
         }
      }

      return true;
   }

   public static boolean allowsUsage(X509Certificate cert, KeyPurposeId... purposes) throws EncodingException {
      List allowedUses = (new ExtensionReader(cert)).readExtendedKeyUsage();
      KeyPurposeId[] var3 = purposes;
      int var4 = purposes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         KeyPurposeId purpose = var3[var5];
         if (allowedUses == null || !allowedUses.contains(purpose)) {
            return false;
         }
      }

      return true;
   }

   public static boolean hasPolicies(X509Certificate cert, String... policyOidsToCheck) throws EncodingException {
      List policies = (new ExtensionReader(cert)).readCertificatePolicies();
      String[] var4 = policyOidsToCheck;
      int var5 = policyOidsToCheck.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String policyOid = var4[var6];
         boolean hasPolicy = false;
         if (policies != null) {
            Iterator var8 = policies.iterator();

            while(var8.hasNext()) {
               PolicyInformation policy = (PolicyInformation)var8.next();
               if (policy.getPolicyIdentifier().getId().equals(policyOid)) {
                  hasPolicy = true;
                  break;
               }
            }
         }

         if (!hasPolicy) {
            return false;
         }
      }

      return true;
   }

   public static String subjectKeyId(X509Certificate cert) throws EncodingException {
      return CodecUtil.hex((new ExtensionReader(cert)).readSubjectKeyIdentifier().getKeyIdentifier(), true);
   }

   public static String authorityKeyId(X509Certificate cert) throws EncodingException {
      return CodecUtil.hex((new ExtensionReader(cert)).readAuthorityKeyIdentifier().getKeyIdentifier(), true);
   }
}
