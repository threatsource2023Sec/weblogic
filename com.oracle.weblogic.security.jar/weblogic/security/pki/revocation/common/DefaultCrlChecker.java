package weblogic.security.pki.revocation.common;

import com.rsa.certj.CertJ;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.InvalidUseException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.X500Name;
import com.rsa.certj.cert.X509CRL;
import com.rsa.certj.provider.db.EnhancedFlatFileDB;
import com.rsa.certj.provider.path.X509V1CertPath;
import com.rsa.certj.provider.revocation.CRLCertStatus;
import com.rsa.certj.provider.revocation.CRLEvidence;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import java.io.File;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;
import weblogic.security.Salt;
import weblogic.security.pki.revocation.common.CertRevocCheckMethodList.SelectableMethod;

class DefaultCrlChecker extends CrlChecker {
   private static final String CRL_CACHE_DB_PROVIDER_NAME = "CRL_CACHE_DB_PROVIDER";
   private static final String CRL_CERT_PATH_PROVIDER_NAME = "CRL_CERT_PATH_PROVIDER";
   private static final String CRL_CERT_STATUS_PROVIDER_NAME = "CRL_CERT_STATUS_PROVIDER";

   DefaultCrlChecker(AbstractCertRevocContext context) {
      super(context);
   }

   CertRevocStatus getCrlStatus(X509Certificate certToCheckIssuer, X509Certificate certToCheck) {
      Util.checkNotNull("Issuer X509Certificate.", certToCheckIssuer);
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      LogListener logger = context.getLogListener();
      X500Principal issuerDn = certToCheck.getIssuerX500Principal();
      com.rsa.certj.cert.X509Certificate rsaCertToCheck = RsaUtil.toRsaCert(certToCheck, logger);
      com.rsa.certj.cert.X509Certificate rsaCertToCheckIssuer = RsaUtil.toRsaCert(certToCheckIssuer, logger);
      if (null != rsaCertToCheck && null != rsaCertToCheckIssuer) {
         if (!RsaUtil.isFIPS140UsageOk(logger)) {
            return null;
         } else {
            CertRevocationInfo revocationInfo;
            try {
               revocationInfo = this.checkCertRevocation(issuerDn, rsaCertToCheck, certToCheck, rsaCertToCheckIssuer, certToCheckIssuer);
            } catch (OutOfMemoryError var10) {
               logThrowableDuringCrlCheck(context, var10);
               return null;
            } catch (Exception var11) {
               logThrowableDuringCrlCheck(context, var11);
               return null;
            }

            CertRevocStatus status = this.evalRevocationInfo(certToCheck, revocationInfo);
            return status;
         }
      } else {
         if (context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, "Unable to check revocation status, unable to convert both subject and issuer certificates.");
         }

         return null;
      }
   }

   private static void logThrowableDuringCrlCheck(AbstractCertRevocContext context, Throwable e) {
      if (context.isLoggable(Level.FINE)) {
         context.log(Level.FINE, e, "Exception while checking revocation status using CRLs.");
      }

   }

   private CertRevocationInfo checkCertRevocation(X500Principal issuerDn, com.rsa.certj.cert.X509Certificate rsaCertToCheck, X509Certificate certToCheck, com.rsa.certj.cert.X509Certificate rsaCertToCheckIssuer, X509Certificate certToCheckIssuer) throws Exception {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("rsaCertToCheck", rsaCertToCheck);
      Util.checkNotNull("certToCheck", certToCheck);
      Util.checkNotNull("rsaCertToCheckIssuer", rsaCertToCheckIssuer);
      Util.checkNotNull("certToCheckIssuer", certToCheckIssuer);
      CertJ certj = this.newCertJ();
      DefaultCrlCacheAccessor crlCacheAccessor = this.addCrlCacheProvider(certj);
      this.addCertPathProvider(certj);
      this.addCrlCertStatusProvider(certj);
      CertPathCtx pathCtx = this.initCertPathCtx(rsaCertToCheckIssuer, crlCacheAccessor.getDatabaseService());
      AbstractCertRevocContext context = this.getContext();
      CertRevocationInfo revocationInfo = null;
      boolean triedCrlCacheUpdateFromDp = false;
      boolean tryCrlCache = true;

      while(tryCrlCache) {
         tryCrlCache = false;
         revocationInfo = certj.checkCertRevocation(pathCtx, rsaCertToCheck);
         if (null == revocationInfo) {
            if (context.isLoggable(Level.FINE)) {
               context.log(Level.FINE, "CRL processing implementation returned no revocation information.");
            }
            break;
         }

         if (2 != revocationInfo.getStatus()) {
            break;
         }

         if (!triedCrlCacheUpdateFromDp) {
            triedCrlCacheUpdateFromDp = true;
            if (context.isLoggable(Level.FINEST)) {
               context.log(Level.FINEST, "Attempting CRL fetch from Distribution Point, CRL is not cached.");
            }

            boolean crlDpEnabled = context.isCrlDpEnabled(issuerDn);
            if (!crlDpEnabled) {
               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "CRL fetch from Distribution Point is disabled.");
               }
            } else if (!crlCacheAccessor.isCrlCacheUpdatable()) {
               if (context.isLoggable(Level.FINEST)) {
                  context.log(Level.FINEST, "Not attempting CRL fetch from Distribution Point, CRL cache is not updatable.");
               }
            } else {
               boolean crlCacheUpdated = false;

               try {
                  crlCacheUpdated = this.updateCrlCacheFromDP(certToCheck, crlCacheAccessor);
               } catch (Exception var16) {
                  if (context.isLoggable(Level.FINE)) {
                     context.log(Level.FINE, var16, "Exception while updating CRL cache from Distribution Point.");
                  }
               }

               if (crlCacheUpdated) {
                  tryCrlCache = true;
               }
            }
         }
      }

      return revocationInfo;
   }

   private boolean updateCrlCacheFromDP(X509Certificate certToCheck, CrlCacheAccessor crlCacheAccessor) throws Exception {
      AbstractCertRevocContext context = this.getContext();
      boolean result = CrlCacheUpdater.updateCrlCacheFromDP(certToCheck, crlCacheAccessor, context);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "Attempted to update CRL cache from DP, updatedCache={0}.", result);
      }

      return result;
   }

   private CertJ newCertJ() throws ProviderManagementException, InvalidUseException, InvalidParameterException {
      CertJ certj = new CertJ();
      certj.setDevice(RsaUtil.getCryptoJDeviceList());
      return certj;
   }

   private void addCrlCertStatusProvider(CertJ certj) throws Exception {
      Util.checkNotNull("CertJ", certj);
      Provider certStatusProvider = new CRLCertStatus("CRL_CERT_STATUS_PROVIDER");
      certj.addProvider(certStatusProvider);
   }

   private void addCertPathProvider(CertJ certj) throws Exception {
      Util.checkNotNull("CertJ", certj);
      Provider pathProvider = new X509V1CertPath("CRL_CERT_PATH_PROVIDER");
      certj.addProvider(pathProvider);
   }

   private DefaultCrlCacheAccessor addCrlCacheProvider(CertJ certj) throws Exception {
      Util.checkNotNull("CertJ", certj);
      AbstractCertRevocContext context = this.getContext();
      AbstractCertRevocConstants.CrlCacheType crlCacheType = context.getCrlCacheType();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlCacheType={0}", crlCacheType);
      }

      switch (crlCacheType) {
         case FILE:
            boolean crlUpdatable = this.addCrlCacheFileProvider(certj);
            DatabaseService dbService = (DatabaseService)certj.bindService(1, "CRL_CACHE_DB_PROVIDER");
            return new DefaultCrlCacheAccessor(dbService, crlUpdatable, context.getLogListener());
         default:
            throw new IllegalStateException("Unable to initialize file-based CRL cache, unsupported CrlCacheType \"" + crlCacheType + "\".");
      }
   }

   private boolean addCrlCacheFileProvider(CertJ certj) throws Exception {
      Util.checkNotNull("CertJ", certj);
      AbstractCertRevocContext context = this.getContext();
      File dbDirectory = context.getCrlCacheTypeFileDir();
      CrlCacheUpdater.ensureCrlCacheDir(dbDirectory);
      String dbDirPath = dbDirectory.getAbsolutePath();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "CrlCacheTypeFileDir=\"{0}\"", dbDirPath);
      }

      File dbFile = new File(dbDirPath);
      char[] passphrase = new char[0];
      byte[] salt = Salt.getRandomBytes(4);
      Provider dbProvider = new EnhancedFlatFileDB("CRL_CACHE_DB_PROVIDER", dbFile, passphrase, salt);
      certj.addProvider(dbProvider);
      boolean crlUpdatable = true;
      return true;
   }

   private CertPathCtx initCertPathCtx(com.rsa.certj.cert.X509Certificate rsaIssuerCert, DatabaseService dbService) {
      Util.checkNotNull("issuerCert", rsaIssuerCert);
      Util.checkNotNull("dbService", dbService);
      AbstractCertRevocContext context = this.getContext();
      com.rsa.certj.cert.X509Certificate[] trustedCerts = new com.rsa.certj.cert.X509Certificate[]{rsaIssuerCert};
      int pathOptions = 4;
      byte[][] policies = (byte[][])null;
      Date validationTime = new Date();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "Validation time=\"{0}\"", validationTime);
      }

      CertPathCtx pathCtx = new CertPathCtx(pathOptions, trustedCerts, policies, validationTime, dbService);
      return pathCtx;
   }

   private CertRevocStatus evalRevocationInfo(X509Certificate certToCheck, CertRevocationInfo revocationInfo) {
      Util.checkNotNull("certToCheck", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      if (null == revocationInfo) {
         if (context.isLoggable(Level.FINER)) {
            context.log(Level.FINER, "Revocation status unavailable from CRL (CertRevocationInfo is null).");
         }

         return null;
      } else {
         Boolean revoked = RsaUtil.evalRevocStatusCode(SelectableMethod.CRL, revocationInfo.getStatus(), context.getLogListener());
         if (null == revoked) {
            return null;
         } else {
            int evidenceType = revocationInfo.getType();
            if (evidenceType != 1) {
               if (context.isLoggable(Level.FINE)) {
                  context.log(Level.FINE, "Revocation status unavailable from CRL, unexpected evidence type {0}.", evidenceType);
               }

               return null;
            } else {
               CRLEvidence evidence = (CRLEvidence)revocationInfo.getEvidence();
               if (null == evidence) {
                  if (context.isLoggable(Level.FINE)) {
                     context.log(Level.FINE, "Revocation status unavailable from CRL, no evidence available.");
                  }

                  return null;
               } else {
                  Date thisUpdate = null;
                  Date nextUpdate = null;
                  X509CRL crl = (X509CRL)evidence.getCRL();
                  if (null == crl) {
                     Vector crlList = evidence.getCRLList();
                     if (null == crlList || crlList.isEmpty()) {
                        if (context.isLoggable(Level.FINE)) {
                           context.log(Level.FINE, "Revocation status unavailable from CRL, no CRL evidence available.");
                        }

                        return null;
                     } else {
                        Iterator var11 = crlList.iterator();

                        while(true) {
                           while(var11.hasNext()) {
                              Object elem = var11.next();
                              if (!(elem instanceof X509CRL)) {
                                 if (context.isLoggable(Level.FINE)) {
                                    context.log(Level.FINE, "Found non-X509CRL object in evidence.getCRLList(), foundClass={0}", null == elem ? null : elem.getClass().getName());
                                 }
                              } else {
                                 X509CRL listedCrl = (X509CRL)elem;
                                 if (null == listedCrl.getNextUpdate()) {
                                    thisUpdate = listedCrl.getThisUpdate();
                                    nextUpdate = listedCrl.getNextUpdate();
                                    return new CertRevocStatus(SelectableMethod.CRL, certToCheck.getSubjectX500Principal(), certToCheck.getIssuerX500Principal(), certToCheck.getSerialNumber(), thisUpdate, nextUpdate, revoked, (Boolean)null, (Map)null);
                                 }

                                 if (null == nextUpdate || listedCrl.getNextUpdate().before(nextUpdate)) {
                                    thisUpdate = listedCrl.getThisUpdate();
                                    nextUpdate = listedCrl.getNextUpdate();
                                 }
                              }
                           }

                           return new CertRevocStatus(SelectableMethod.CRL, certToCheck.getSubjectX500Principal(), certToCheck.getIssuerX500Principal(), certToCheck.getSerialNumber(), thisUpdate, nextUpdate, revoked, (Boolean)null, (Map)null);
                        }
                     }
                  } else {
                     thisUpdate = crl.getThisUpdate();
                     nextUpdate = crl.getNextUpdate();
                     return new CertRevocStatus(SelectableMethod.CRL, certToCheck.getSubjectX500Principal(), certToCheck.getIssuerX500Principal(), certToCheck.getSerialNumber(), thisUpdate, nextUpdate, revoked, (Boolean)null, (Map)null);
                  }
               }
            }
         }
      }
   }

   CrlCacheAccessor getCrlCacheAccessor() {
      CrlCacheAccessor crlCacheAccessor = null;

      try {
         crlCacheAccessor = this.addCrlCacheProvider(this.newCertJ());
      } catch (Exception var4) {
         AbstractCertRevocContext context = this.getContext();
         if (context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, var4, "Unable to get CrlCacheAccessor.");
         }
      }

      return crlCacheAccessor;
   }

   private static final class DefaultCrlCacheAccessor implements CrlCacheAccessor {
      private final DatabaseService dbService;
      private final boolean crlCacheUpdatable;
      private final LogListener logger;

      private DefaultCrlCacheAccessor(DatabaseService dbService, boolean crlCacheUpdatable, LogListener logger) {
         Util.checkNotNull("DatabaseService", dbService);
         this.dbService = dbService;
         this.crlCacheUpdatable = crlCacheUpdatable;
         this.logger = logger;
      }

      public boolean loadCrl(InputStream inputStream) throws Exception {
         Util.checkNotNull("InputStream", inputStream);

         byte[] x509CrlBER;
         try {
            x509CrlBER = Util.readAll(inputStream);
         } catch (OutOfMemoryError var11) {
            this.logErrorLoadCrl(var11, "reading");
            throw new RuntimeException(var11);
         } catch (Exception var12) {
            this.logErrorLoadCrl(var12, "reading");
            throw var12;
         }

         int offset = false;
         int special = false;

         X509CRL crl;
         try {
            crl = new X509CRL(x509CrlBER, 0, 0);
         } catch (OutOfMemoryError var9) {
            this.logErrorLoadCrl(var9, "parsing");
            throw new RuntimeException(var9);
         } catch (Exception var10) {
            this.logErrorLoadCrl(var10, "parsing");
            throw var10;
         }

         byte[] x509CrlBER = null;

         try {
            this.dbService.insertCRL(crl);
            return true;
         } catch (OutOfMemoryError var7) {
            this.logErrorLoadCrl(var7, "inserting");
            throw new RuntimeException(var7);
         } catch (Exception var8) {
            this.logErrorLoadCrl(var8, "inserting");
            throw var8;
         }
      }

      public void deleteCrl(X500Principal issuerX500Name, Date thisUpdate) throws Exception {
         Util.checkNotNull("issuerX500Name", issuerX500Name);
         Util.checkNotNull("thisUpdate", thisUpdate);

         X500Name issuer;
         try {
            byte[] encoded = issuerX500Name.getEncoded();
            int offset = false;
            int special = false;
            issuer = new X500Name(encoded, 0, 0);
         } catch (Exception var9) {
            throw new IllegalArgumentException("Illegal issuer distinguished name: " + issuerX500Name, var9);
         }

         try {
            this.dbService.deleteCRL(issuer, thisUpdate);
         } catch (OutOfMemoryError var7) {
            throw new RuntimeException(var7);
         } catch (Exception var8) {
            throw var8;
         }
      }

      public boolean isCrlCacheUpdatable() {
         return this.crlCacheUpdatable;
      }

      private DatabaseService getDatabaseService() {
         return this.dbService;
      }

      private void logErrorLoadCrl(Throwable t, String action) {
         if (null != this.logger && this.logger.isLoggable(Level.FINE)) {
            this.logger.log(Level.FINE, t, "Unable to load CRL, while " + action + ".");
         }

      }

      // $FF: synthetic method
      DefaultCrlCacheAccessor(DatabaseService x0, boolean x1, LogListener x2, Object x3) {
         this(x0, x1, x2);
      }
   }
}
