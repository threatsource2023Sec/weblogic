package weblogic.security.pk;

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
import java.util.Vector;
import weblogic.logging.Loggable;
import weblogic.security.SecurityInitializationException;
import weblogic.security.SecurityLogger;
import weblogic.security.utils.X509Utils;

public class X509CertificateFactory extends CertificateFactorySpi {
   private static final String MY_JDK_SECURITY_PROVIDER_NAME = "WLSX509CertificateFactoryProvider";
   private static final String FACTORY_ALGORITHM = "X.509";
   private static CertificateFactory standardFactory;

   public static void register() {
      if (Security.getProvider("WLSX509CertificateFactoryProvider") == null) {
         if (Boolean.parseBoolean(System.getProperty("weblogic.security.RegisterX509CertificateFactory", "true"))) {
            AccessController.doPrivileged(new PrivilegedAction() {
               public Object run() {
                  int position = Security.insertProviderAt(new MyJDKSecurityProvider(), 1);
                  if (position != 1) {
                     Loggable loggable = SecurityLogger.logCouldNotRegisterWLSX509CertificateFactoryAsDefaultFactoryLoggable();
                     loggable.log();
                     throw new SecurityInitializationException(loggable.getMessageText());
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
         Provider[] providers = Security.getProviders();

         for(int i = 0; standardFactory == null && providers != null && i < providers.length; ++i) {
            Provider provider = providers[i];
            if (!"WLSX509CertificateFactoryProvider".equals(provider.getName())) {
               try {
                  standardFactory = CertificateFactory.getInstance("X.509", provider);
               } catch (CertificateException var5) {
               }
            }
         }

         if (standardFactory == null) {
            throw new AssertionError("The WLS X.509 CertificateFactory could not find another X.509 CertificateFactory to delegate to");
         }
      }

      return standardFactory;
   }

   public CertPath engineGenerateCertPath(InputStream is) throws CertificateException {
      String defaultEncoding = (String)((String)this.engineGetCertPathEncodings().next());
      return this.engineGenerateCertPath(is, defaultEncoding);
   }

   public CertPath engineGenerateCertPath(InputStream is, String encoding) throws CertificateException {
      CertPath certPath = this.getStandardFactory().generateCertPath(is, encoding);
      return "PKCS7".equals(encoding) ? orderCertPath(certPath) : certPath;
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

   private static X509Certificate findIssuer(X509Certificate[] unorderedCerts, X509Certificate issued) {
      for(int i = 0; i < unorderedCerts.length; ++i) {
         X509Certificate issuer = unorderedCerts[i];
         if (issuer != null && X509Utils.isIssuedBy(issued, issuer)) {
            unorderedCerts[i] = null;
            return issuer;
         }
      }

      return null;
   }

   private static X509Certificate findIssued(X509Certificate[] unorderedCerts, X509Certificate issuer) {
      for(int i = 0; i < unorderedCerts.length; ++i) {
         X509Certificate issued = unorderedCerts[i];
         if (issued != null && X509Utils.isIssuedBy(issued, issuer)) {
            unorderedCerts[i] = null;
            return issued;
         }
      }

      return null;
   }

   private static CertPath orderCertPath(CertPath originalCertPath) throws CertificateException {
      if (X509Utils.isOrdered(originalCertPath)) {
         return originalCertPath;
      } else {
         X509Certificate[] unorderedCerts = X509Utils.getCertificates(originalCertPath);
         Vector orderedCerts = new Vector(unorderedCerts.length);
         orderedCerts.add(unorderedCerts[0]);
         unorderedCerts[0] = null;
         X509Certificate head = (X509Certificate)orderedCerts.lastElement();

         while(head != null && !X509Utils.isSelfSigned(head)) {
            head = findIssuer(unorderedCerts, head);
            if (head != null) {
               orderedCerts.add(head);
            }
         }

         head = (X509Certificate)orderedCerts.firstElement();

         while(head != null) {
            head = findIssued(unorderedCerts, head);
            if (head != null) {
               orderedCerts.add(0, head);
            }
         }

         if (orderedCerts.size() < unorderedCerts.length) {
            return originalCertPath;
         } else {
            return CertificateFactory.getInstance("X.509").generateCertPath(new ArrayList(orderedCerts));
         }
      }
   }

   private static class MyJDKSecurityProvider extends Provider {
      private MyJDKSecurityProvider() {
         super("WLSX509CertificateFactoryProvider", 1.0, "WebLogic JDK CertPath provider");
         this.put("CertificateFactory.X.509", "weblogic.security.pk.X509CertificateFactory");
      }

      // $FF: synthetic method
      MyJDKSecurityProvider(Object x0) {
         this();
      }
   }
}
