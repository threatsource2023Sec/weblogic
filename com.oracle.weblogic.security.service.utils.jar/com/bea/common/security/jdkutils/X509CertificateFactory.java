package com.bea.common.security.jdkutils;

import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateFactorySpi;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class X509CertificateFactory extends CertificateFactorySpi {
   private static final String MY_JDK_SECURITY_PROVIDER_NAME = "CSSX509CertificateFactoryProvider";
   private static CertificateFactory standardFactory;
   private static String defaultEncoding;

   public static void register() {
      if (Security.getProvider("CSSX509CertificateFactoryProvider") == null) {
         if (Boolean.valueOf(System.getProperty("weblogic.security.RegisterX509CertificateFactory", "true"))) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  int position = Security.insertProviderAt(new MyJDKSecurityProvider(), 1);
                  if (position != 1) {
                     throw new SecurityException("Could not register X509CertificateFactory as a default factory");
                  } else {
                     return null;
                  }
               }
            });
         }
      }
   }

   private synchronized CertificateFactory getStandardFactory() {
      if (standardFactory == null) {
         defaultEncoding = null;
         Provider[] providers = Security.getProviders();

         for(int i = 0; standardFactory == null && providers != null && i < providers.length; ++i) {
            Provider provider = providers[i];
            if (!"CSSX509CertificateFactoryProvider".equals(provider.getName())) {
               try {
                  standardFactory = CertificateFactory.getInstance("X.509", provider);
               } catch (CertificateException var5) {
               }
            }
         }

         if (standardFactory == null) {
            throw new AssertionError("The CSS X.509 CertificateFactory could not find another X.509 CertificateFactory to delegate to");
         }

         defaultEncoding = (String)standardFactory.getCertPathEncodings().next();
      }

      return standardFactory;
   }

   public CertPath engineGenerateCertPath(InputStream is) throws CertificateException {
      return this.engineGenerateCertPath(is, defaultEncoding);
   }

   public CertPath engineGenerateCertPath(InputStream is, String encoding) throws CertificateException {
      CertPath certPath = this.getStandardFactory().generateCertPath(is, encoding);
      if ("PKCS7".equals(encoding)) {
         certPath = orderCertPath(certPath);
      }

      return certPath;
   }

   public CertPath engineGenerateCertPath(List list) throws CertificateException {
      return this.getStandardFactory().generateCertPath(list);
   }

   public Certificate engineGenerateCertificate(InputStream is) throws CertificateException {
      return this.getStandardFactory().generateCertificate(is);
   }

   public Iterator engineGetCertPathEncodings() {
      return this.getStandardFactory().getCertPathEncodings();
   }

   public Collection engineGenerateCertificates(InputStream is) throws CertificateException {
      return this.getStandardFactory().generateCertificates(is);
   }

   public CRL engineGenerateCRL(InputStream is) throws CRLException {
      return this.getStandardFactory().generateCRL(is);
   }

   public Collection engineGenerateCRLs(InputStream is) throws CRLException {
      return this.getStandardFactory().generateCRLs(is);
   }

   private static int indexOfIssuer(X509Certificate[] certs, X509Certificate issued) {
      for(int i = 0; i < certs.length; ++i) {
         if (certs[i] != null && isIssuedBy(issued, certs[i])) {
            return i;
         }
      }

      return -1;
   }

   private static int indexOfIssued(X509Certificate[] certs, X509Certificate issuer) {
      for(int i = 0; i < certs.length; ++i) {
         if (certs[i] != null && isIssuedBy(certs[i], issuer)) {
            return i;
         }
      }

      return -1;
   }

   private static boolean isIssuedBy(X509Certificate issued, X509Certificate issuer) {
      if (!issued.getIssuerX500Principal().equals(issuer.getSubjectX500Principal())) {
         return false;
      } else {
         try {
            issued.verify(issuer.getPublicKey());
            return true;
         } catch (Exception var3) {
            return false;
         }
      }
   }

   private static boolean isOrdered(CertPath certPath) {
      Iterator certs = certPath.getCertificates().iterator();

      try {
         X509Certificate issuer;
         if (certs.hasNext()) {
            for(X509Certificate issued = (X509Certificate)certs.next(); certs.hasNext(); issued = issuer) {
               issuer = (X509Certificate)certs.next();
               if (!isIssuedBy(issued, issuer) || isSelfSigned(issued)) {
                  return false;
               }
            }
         }

         return true;
      } catch (ClassCastException var4) {
         throw new AssertionError("Received a cert path containing a non-X509 certificate");
      }
   }

   private static boolean isSelfSigned(X509Certificate cert) {
      return isIssuedBy(cert, cert);
   }

   private static CertPath orderCertPath(CertPath originalCertPath) throws CertificateException {
      if (isOrdered(originalCertPath)) {
         return originalCertPath;
      } else {
         List certs = originalCertPath.getCertificates();
         X509Certificate[] unorderedCerts = (X509Certificate[])((X509Certificate[])certs.toArray(new X509Certificate[certs.size()]));
         ArrayList orderedCerts = new ArrayList(unorderedCerts.length);
         X509Certificate head = unorderedCerts[0];
         X509Certificate tail = head;
         orderedCerts.add(unorderedCerts[0]);
         unorderedCerts[0] = null;

         int index;
         while(!isSelfSigned(tail)) {
            index = indexOfIssuer(unorderedCerts, tail);
            if (index < 0) {
               break;
            }

            tail = unorderedCerts[index];
            unorderedCerts[index] = null;
            orderedCerts.add(tail);
         }

         for(index = indexOfIssued(unorderedCerts, head); index >= 0; index = indexOfIssued(unorderedCerts, head)) {
            head = unorderedCerts[index];
            unorderedCerts[index] = null;
            orderedCerts.add(0, head);
         }

         return orderedCerts.size() < unorderedCerts.length ? originalCertPath : CertificateFactory.getInstance("X.509").generateCertPath(orderedCerts);
      }
   }

   private static class MyJDKSecurityProvider extends Provider {
      private static final long serialVersionUID = -798622412285514644L;

      private MyJDKSecurityProvider() {
         super("CSSX509CertificateFactoryProvider", 1.0, "CSS JDK CertPath provider");
         this.put("CertificateFactory.X.509", "com.bea.common.security.jdkutils.X509CertificateFactory");
      }

      // $FF: synthetic method
      MyJDKSecurityProvider(Object x0) {
         this();
      }
   }
}
