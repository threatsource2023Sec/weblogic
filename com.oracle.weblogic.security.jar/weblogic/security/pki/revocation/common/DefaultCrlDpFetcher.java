package weblogic.security.pki.revocation.common;

import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.extensions.CRLDistributionPoints;
import com.rsa.certj.cert.extensions.GeneralName;
import com.rsa.certj.cert.extensions.GeneralNames;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants.AttributeUsage;

class DefaultCrlDpFetcher extends CrlDpFetcher {
   boolean updateCrls(X509Certificate cert, CrlCacheAccessor crlCacheAccessor, URI alternateUri, AbstractCertRevocConstants.AttributeUsage alternateUriUsage, long readTimeout, long connectionTimeout, LogListener logger) throws Exception {
      Util.checkNotNull("X509Certificate with DPs", cert);
      Util.checkNotNull("CrlCacheAccessor", crlCacheAccessor);
      X500Principal certIssuer = this.getIssuerX500Principal(cert, logger);
      if (null == certIssuer) {
         return false;
      } else {
         com.rsa.certj.cert.X509Certificate rsaCert = this.toRsaCert(cert, logger);
         if (null == rsaCert) {
            return false;
         } else {
            X500Name rsaCertIssuer = this.getRsaIssuerX500Name(cert, logger, rsaCert);
            if (null == rsaCertIssuer) {
               return false;
            } else {
               URI uri = this.getUri(alternateUri, alternateUriUsage, logger, certIssuer, rsaCert, rsaCertIssuer);
               if (null != uri) {
                  if (null != logger && logger.isLoggable(Level.FINEST)) {
                     logger.log(Level.FINEST, "Attempting to download CRL from URI \"{0}\".", uri);
                  }

                  int connectionTimeoutInt = connectionTimeout > 2147483647L ? Integer.MAX_VALUE : (int)connectionTimeout;
                  int readTimeoutInt = readTimeout > 2147483647L ? Integer.MAX_VALUE : (int)readTimeout;
                  return this.loadCrl(crlCacheAccessor, logger, certIssuer, connectionTimeoutInt, readTimeoutInt, uri);
               } else {
                  if (null != logger && logger.isLoggable(Level.FINER)) {
                     logger.log(Level.FINER, "Unable to determine CRL DP URI for certificate with subject \"{0}\".", cert.getSubjectX500Principal());
                  }

                  return false;
               }
            }
         }
      }
   }

   private URI getUri(URI alternateUri, AbstractCertRevocConstants.AttributeUsage alternateUriUsage, LogListener logger, X500Principal certIssuer, com.rsa.certj.cert.X509Certificate rsaCert, X500Name rsaCertIssuer) throws NameException {
      URI uri = null;
      if (AttributeUsage.OVERRIDE == alternateUriUsage) {
         if (!isAlternateUriNull(alternateUri, logger, certIssuer)) {
            uri = alternateUri;
         }
      } else {
         CRLDistributionPoints distributionPoints = this.getDistributionPoints(logger, rsaCert);
         if (null != distributionPoints) {
            uri = this.findUriInDp(logger, certIssuer, rsaCertIssuer, distributionPoints);
            if (null == uri) {
               uri = this.findFailoverUri(alternateUri, alternateUriUsage, logger, certIssuer);
            }
         }
      }

      return uri;
   }

   private CRLDistributionPoints getDistributionPoints(LogListener logger, com.rsa.certj.cert.X509Certificate rsaCert) {
      CRLDistributionPoints distributionPoints = (CRLDistributionPoints)RsaUtil.getExtension((com.rsa.certj.cert.X509Certificate)rsaCert, 31);
      if (null == distributionPoints && null != logger && logger.isLoggable(Level.FINER)) {
         logger.log(Level.FINER, "No Distribution points found in cert with subject \"{0}\".", rsaCert.getSubjectName());
      }

      return distributionPoints;
   }

   private URI findFailoverUri(URI alternateUri, AbstractCertRevocConstants.AttributeUsage alternateUriUsage, LogListener logger, X500Principal certIssuer) {
      URI uri = null;
      if (null != logger && logger.isLoggable(Level.FINER)) {
         logger.log(Level.FINER, "Unable to find any usable CRL DP URI, checking FAILOVER CRL DP URI.");
      }

      if (AttributeUsage.FAILOVER == alternateUriUsage && null != alternateUri) {
         if (null != logger && logger.isLoggable(Level.FINER)) {
            logger.log(Level.FINER, "Trying FAILOVER CRL DP URI \"{0}\".", alternateUri);
         }

         uri = alternateUri;
      } else if (null != logger && logger.isLoggable(Level.FINER)) {
         logger.log(Level.FINER, "NO FAILOVER CRL DP URI for issuer \"{0}\".", certIssuer);
      }

      return uri;
   }

   private URI findUriInDp(LogListener logger, X500Principal certIssuer, X500Name rsaCertIssuer, CRLDistributionPoints distributionPoints) throws NameException {
      URI uri = null;
      int distributionPointCount = distributionPoints.getDistributionPointCount();

      for(int i = 0; i < distributionPointCount; ++i) {
         int reasonFlags = distributionPoints.getReasonFlags(i);
         if (-1 == reasonFlags && this.isDpCrlIssuerEqual(rsaCertIssuer, distributionPoints, i)) {
            Object fullNameObj = distributionPoints.getDistributionPointName(i);
            if (fullNameObj instanceof GeneralNames) {
               GeneralNames fullName = (GeneralNames)fullNameObj;
               uri = this.getUri(logger, certIssuer, fullName);
               if (null != uri) {
                  break;
               }
            }
         }
      }

      return uri;
   }

   private static boolean isAlternateUriNull(URI alternateUri, LogListener logger, X500Principal certIssuer) {
      if (null == alternateUri) {
         if (null != logger && logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, "Unable to fetch CRL from DP. CRL DP override URI set to null for cert issuer \"{0}\".", certIssuer);
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean loadCrl(CrlCacheAccessor crlCacheAccessor, LogListener logger, X500Principal certIssuer, int connectionTimeoutInt, int readTimeoutInt, URI uri) {
      boolean crlsUpdated = false;
      InputStream is = null;

      try {
         is = this.getInputStream(connectionTimeoutInt, readTimeoutInt, uri);
         crlsUpdated = crlCacheAccessor.loadCrl(is);
      } catch (Exception var18) {
         if (null != logger && logger.isLoggable(Level.FINE)) {
            logger.log(Level.FINE, var18, "Exception fetching CRL from DP URI \"{0}\" for cert issuer \"{1}\".", uri, certIssuer);
         }
      } finally {
         if (null != is) {
            try {
               is.close();
            } catch (IOException var17) {
            }
         }

      }

      return crlsUpdated;
   }

   private InputStream getInputStream(int connectionTimeoutInt, int readTimeoutInt, URI uri) throws IOException {
      URL url = uri.toURL();
      URLConnection connection = url.openConnection();
      connection.setConnectTimeout(connectionTimeoutInt);
      connection.setReadTimeout(readTimeoutInt);
      InputStream is = connection.getInputStream();
      return is;
   }

   private URI getUri(LogListener logger, X500Principal certIssuer, GeneralNames fullName) throws NameException {
      URI uri = null;
      int nameCount = fullName.getNameCount();

      for(int j = 0; j < nameCount; ++j) {
         GeneralName name = fullName.getGeneralName(j);
         if (name.getGeneralNameType() == 7) {
            String uriString = (String)name.getGeneralName();

            try {
               uri = new URI(uriString);
               String scheme = uri.getScheme().toLowerCase();
               if (scheme.equals("http") || scheme.equals("ftp")) {
                  break;
               }
            } catch (URISyntaxException var10) {
               if (null != logger && logger.isLoggable(Level.FINE)) {
                  logger.log(Level.FINE, "Unable to parse DP URI \"{0}\" for cert issuer \"{1}\".", uriString, certIssuer);
               }
            }
         }
      }

      return uri;
   }

   private boolean isDpCrlIssuerEqual(X500Name rsaCertIssuer, CRLDistributionPoints distributionPoints, int i) throws NameException {
      GeneralNames rsaDpCrlIssuer = distributionPoints.getCRLIssuer(i);
      if (null == rsaDpCrlIssuer) {
         return true;
      } else {
         int nameCount = rsaDpCrlIssuer.getNameCount();

         for(int j = 0; j < nameCount; ++j) {
            GeneralName name = rsaDpCrlIssuer.getGeneralName(j);
            Object obj = name.getGeneralName();
            if (obj instanceof X500Name && ((X500Name)obj).equals(rsaCertIssuer)) {
               return true;
            }
         }

         return false;
      }
   }

   private X500Name getRsaIssuerX500Name(X509Certificate cert, LogListener logger, com.rsa.certj.cert.X509Certificate rsaCert) {
      X500Name rsaCertIssuer = rsaCert.getIssuerName();
      if (null == rsaCertIssuer && null != logger && logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Unable to update CRLs, missing internal issuer, certificate=\"{0}\".", cert.getSubjectDN());
      }

      return rsaCertIssuer;
   }

   private com.rsa.certj.cert.X509Certificate toRsaCert(X509Certificate cert, LogListener logger) {
      com.rsa.certj.cert.X509Certificate rsaCert = RsaUtil.toRsaCert(cert, logger);
      if (null == rsaCert && null != logger && logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Unable to update CRLs, certificate not convertible, certificate=\"{0}\".", cert.getSubjectDN());
      }

      return rsaCert;
   }

   private X500Principal getIssuerX500Principal(X509Certificate cert, LogListener logger) {
      X500Principal certIssuer = cert.getIssuerX500Principal();
      if (null == certIssuer && null != logger && logger.isLoggable(Level.FINE)) {
         logger.log(Level.FINE, "Unable to update CRLs, missing issuer, certificate=\"{0}\".", cert.getSubjectDN());
      }

      return certIssuer;
   }
}
