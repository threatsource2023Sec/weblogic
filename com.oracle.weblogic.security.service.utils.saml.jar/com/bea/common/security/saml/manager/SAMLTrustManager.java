package com.bea.common.security.saml.manager;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.legacy.ExtendedSecurityServices;
import com.bea.common.security.saml.registry.SAMLCertRegLDAPDelegate;
import com.bea.common.security.saml.registry.SAMLIdentityAsserterLDAPDelegate;
import java.math.BigInteger;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import weblogic.management.security.ProviderMBean;
import weblogic.management.utils.InvalidParameterException;
import weblogic.management.utils.NotFoundException;
import weblogic.security.providers.utils.CertRegLDAPDelegate;
import weblogic.security.spi.ProviderInitializationException;
import weblogic.security.spi.SecurityServices;

public class SAMLTrustManager {
   private static LoggerSpi LOGGER = null;
   private static final int V1_MANAGER = 0;
   private static final int V2_MANAGER = 1;
   private static SAMLTrustManager manager = null;
   private static SecurityServices securityServices = null;
   private CertRegLDAPDelegate ldapDelegate = null;

   private static final void logDebug(String msg) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug("SAMLTrustManager: " + msg);
      }

   }

   private SAMLTrustManager(int which, ProviderMBean mbean, SecurityServices services) {
      LOGGER = ((ExtendedSecurityServices)services).getLogger("SecuritySAMLLib");
      if (which == 0) {
         this.ldapDelegate = new SAMLIdentityAsserterLDAPDelegate(mbean, services);
      } else {
         this.ldapDelegate = new SAMLCertRegLDAPDelegate(mbean, services);
      }

   }

   private static synchronized SAMLTrustManager getManager(int which, ProviderMBean mbean, SecurityServices services) {
      if (manager != null && securityServices != null && securityServices != services) {
         manager = null;
      }

      if (manager == null) {
         try {
            securityServices = services;
            SAMLTrustManager newManager = new SAMLTrustManager(which, mbean, services);
            manager = newManager;
         } catch (Exception var4) {
            manager = null;
            throw new ProviderInitializationException("SAMLTrustManager: Unable to instantiate trust manager for realm " + mbean.getRealm().getName() + ": " + var4.toString());
         }
      }

      return manager;
   }

   public static SAMLTrustManager getManager(ProviderMBean mbean, SecurityServices services) {
      return getManager(1, mbean, services);
   }

   public static SAMLTrustManager getV1Manager(ProviderMBean mbean, SecurityServices services) {
      return getManager(0, mbean, services);
   }

   public static synchronized SAMLTrustManager getManager() {
      return manager;
   }

   public X509Certificate getCertificate(String alias) {
      if (alias == null) {
         return null;
      } else {
         logDebug("Looking for certificate alias '" + alias + "'");
         X509Certificate cert = null;

         try {
            cert = this.ldapDelegate.getCertificateFromAlias(alias);
            cert.checkValidity();
         } catch (NotFoundException var4) {
            cert = null;
         } catch (InvalidParameterException var5) {
            cert = null;
         } catch (CertificateExpiredException var6) {
            logDebug("Certificate has expired: " + var6.toString());
            return null;
         } catch (CertificateNotYetValidException var7) {
            logDebug("Certificate is not yet valid: " + var7.toString());
            return null;
         }

         logDebug("Certificate was " + (cert != null ? "found" : "not found"));
         return cert;
      }
   }

   public boolean isCertificateTrusted(X509Certificate cert) {
      if (cert == null) {
         logDebug("Certificate parameter was null!");
         return false;
      } else {
         logDebug("Verifying trust for cert: " + cert.getSubjectDN().getName());
         String issuerDN = cert.getIssuerDN().getName();
         BigInteger serialNumber = cert.getSerialNumber();
         logDebug("Looking for cert with issuerDN: " + issuerDN + " and serialNumber: " + serialNumber);
         X509Certificate x509 = this.ldapDelegate.getCertificateFromIssuerDNAndSerialNumber(issuerDN, serialNumber);
         if (x509 == null) {
            logDebug("Not trusted: X.509 certificate is not registered");
            return false;
         } else {
            try {
               x509.checkValidity();
            } catch (CertificateExpiredException var6) {
               logDebug("Certificate has expired: " + var6.toString());
               return false;
            } catch (CertificateNotYetValidException var7) {
               logDebug("Certificate is not yet valid: " + var7.toString());
               return false;
            }

            if (!x509.equals(cert)) {
               logDebug("Not trusted: X.509 certificate does not match registered certificate");
               return false;
            } else {
               logDebug("Trusted: X.509 certificate found in the registry");
               return true;
            }
         }
      }
   }

   public boolean isCertificateTrustedAlias(X509Certificate cert, String alias) {
      if (cert != null && alias != null) {
         X509Certificate trustedCert = this.getCertificate(alias);
         if (trustedCert == null) {
            return false;
         } else {
            return trustedCert.equals(cert);
         }
      } else {
         return false;
      }
   }
}
