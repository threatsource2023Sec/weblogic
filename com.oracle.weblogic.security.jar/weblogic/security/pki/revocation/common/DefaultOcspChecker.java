package weblogic.security.pki.revocation.common;

import com.rsa.certj.CertJ;
import com.rsa.certj.DatabaseService;
import com.rsa.certj.InvalidParameterException;
import com.rsa.certj.InvalidUseException;
import com.rsa.certj.NoServiceException;
import com.rsa.certj.Provider;
import com.rsa.certj.ProviderManagementException;
import com.rsa.certj.cert.CertificateException;
import com.rsa.certj.cert.NameException;
import com.rsa.certj.cert.X509V3Extensions;
import com.rsa.certj.provider.db.MemoryDB;
import com.rsa.certj.provider.path.X509V1CertPath;
import com.rsa.certj.provider.revocation.ocsp.OCSP;
import com.rsa.certj.provider.revocation.ocsp.OCSPEvidence;
import com.rsa.certj.provider.revocation.ocsp.OCSPRequestControl;
import com.rsa.certj.provider.revocation.ocsp.OCSPResponder;
import com.rsa.certj.provider.revocation.ocsp.OCSPRevocationInfo;
import com.rsa.certj.spi.db.DatabaseException;
import com.rsa.certj.spi.path.CertPathCtx;
import com.rsa.certj.spi.revocation.CertRevocationInfo;
import com.rsa.certj.spi.revocation.CertStatusException;
import com.rsa.jsafe.JSAFE_PrivateKey;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import javax.security.auth.x500.X500Principal;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants.AttributeUsage;
import weblogic.security.pki.revocation.common.CertRevocCheckMethodList.SelectableMethod;

class DefaultOcspChecker extends OcspChecker {
   private static final String DB_PROVIDER_NAME = "OCSP_DB_PROVIDER";
   private static final String CERT_PATH_PROVIDER_NAME = "OCSP_CERT_PATH_PROVIDER";
   private static final String OCSP_PROVIDER_NAME = "OCSP_PROVIDER";

   DefaultOcspChecker(AbstractCertRevocContext context) {
      super(context);
   }

   CertRevocStatus getRemoteStatus(X509Certificate certToCheckIssuer, X509Certificate certToCheck) {
      Util.checkNotNull("Issuer X509Certificate.", certToCheckIssuer);
      Util.checkNotNull("X509Certificate to be checked.", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      LogListener logger = context.getLogListener();
      X500Principal issuerDn = certToCheck.getIssuerX500Principal();
      com.rsa.certj.cert.X509Certificate rsaCertToCheck = RsaUtil.toRsaCert(certToCheck, logger);
      com.rsa.certj.cert.X509Certificate rsaIssuerCert = RsaUtil.toRsaCert(certToCheckIssuer, logger);
      if (null != rsaCertToCheck && null != rsaIssuerCert) {
         com.rsa.certj.cert.X509Certificate rsaExplicitRespondersCert = this.getResponderTrustedCert(issuerDn);
         JSAFE_PrivateKey tempKey = this.getRequestSigningPrivateKey(issuerDn);
         com.rsa.certj.cert.X509Certificate tempCert = this.getRequestSigningPublicCert(issuerDn);
         if (null != tempKey && null != tempCert) {
            if (context.isLoggable(Level.FINEST)) {
               context.log(Level.FINEST, "OCSP request signing enabled, private key and public certificate configured.");
            }
         } else {
            if (context.isLoggable(Level.FINEST)) {
               context.log(Level.FINEST, "OCSP request signing disabled: Private key={0}, Public cert={1}.", null == tempKey ? "missing" : "gotIt", null == tempCert ? "missing" : "gotIt");
            }

            tempKey = null;
            tempCert = null;
         }

         JSAFE_PrivateKey rsaRequestSigningPrivateKey = tempKey;
         com.rsa.certj.cert.X509Certificate rsaRequestSigningPublicCert = tempCert;
         if (!RsaUtil.isFIPS140UsageOk(logger)) {
            return null;
         } else {
            CertRevocationInfo revocationInfo;
            try {
               revocationInfo = this.checkCertRevocation(issuerDn, rsaCertToCheck, rsaIssuerCert, rsaExplicitRespondersCert, rsaRequestSigningPrivateKey, rsaRequestSigningPublicCert);
            } catch (Exception var13) {
               if (context.isLoggable(Level.FINE)) {
                  context.log(Level.FINE, var13, "Exception while checking revocation status using OCSP.");
               }

               return null;
            }

            return this.evalRevocationInfo(certToCheck, revocationInfo);
         }
      } else {
         if (context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, "Unable to check OCSP revocation status, unable to convert both subject and issuer certificates.");
         }

         return null;
      }
   }

   private JSAFE_PrivateKey getRequestSigningPrivateKey(X500Principal issuerDn) {
      Util.checkNotNull("issuerDn", issuerDn);
      AbstractCertRevocContext context = this.getContext();
      JSAFE_PrivateKey jSafeSigningKey = null;
      PrivateKey signingKey = context.getOcspRequestSigningPrivateKey(issuerDn);
      if (null != signingKey) {
         jSafeSigningKey = RsaUtil.toRsaPrivateKey(signingKey, context.getLogListener());
         if (null == jSafeSigningKey && context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, "Unable to convert request signing private key.");
         }
      }

      return jSafeSigningKey;
   }

   private com.rsa.certj.cert.X509Certificate getRequestSigningPublicCert(X500Principal issuerDn) {
      Util.checkNotNull("issuerDn", issuerDn);
      AbstractCertRevocContext context = this.getContext();
      com.rsa.certj.cert.X509Certificate rsaRequestSigningCert = null;
      X509Certificate signersCert = context.getOcspRequestSigningCert(issuerDn);
      if (null != signersCert) {
         rsaRequestSigningCert = RsaUtil.toRsaCert(signersCert, context.getLogListener());
         if (null == rsaRequestSigningCert && context.isLoggable(Level.FINE)) {
            context.log(Level.FINE, "Unable to convert request signing public certificate.");
         }
      }

      return rsaRequestSigningCert;
   }

   private com.rsa.certj.cert.X509Certificate getResponderTrustedCert(X500Principal issuerDn) {
      Util.checkNotNull("issuerDn", issuerDn);
      AbstractCertRevocContext context = this.getContext();
      com.rsa.certj.cert.X509Certificate rsaResponderCert = null;
      X509Certificate respondersCert = context.getOcspResponderTrustedCert(issuerDn);
      if (null == respondersCert) {
         if (context.isLoggable(Level.FINEST)) {
            context.log(Level.FINEST, "No OCSP responder explicitly trusted certificate is available.");
         }
      } else {
         rsaResponderCert = RsaUtil.toRsaCert(respondersCert, context.getLogListener());
         if (null == rsaResponderCert) {
            if (context.isLoggable(Level.FINE)) {
               context.log(Level.FINE, "Unable to convert OCSP responder explicitly trusted certificate.");
            }
         } else if (context.isLoggable(Level.FINEST)) {
            context.log(Level.FINEST, "OCSP using explicitly trust certificate \"{0}\".", rsaResponderCert.getSubjectName());
         }
      }

      return rsaResponderCert;
   }

   private CertJ initCertJ() throws InvalidParameterException, ProviderManagementException, InvalidUseException {
      Provider memoryProvider = new MemoryDB("OCSP_DB_PROVIDER");
      Provider pathProvider = new X509V1CertPath("OCSP_CERT_PATH_PROVIDER");
      Provider[] providers = new Provider[]{memoryProvider, pathProvider};
      CertJ certJ = new CertJ(providers);
      certJ.setDevice(RsaUtil.getCryptoJDeviceList());
      return certJ;
   }

   private DatabaseService initDbService(com.rsa.certj.cert.X509Certificate explicitResponderCert, com.rsa.certj.cert.X509Certificate issuerCert, com.rsa.certj.cert.X509Certificate requestSigningPublicCert, JSAFE_PrivateKey requestSigningPrivateKey, CertJ certJ) throws InvalidParameterException, ProviderManagementException, NoServiceException, DatabaseException {
      Util.checkNotNull("certJ", certJ);
      DatabaseService dbService = (DatabaseService)certJ.bindService(1, "OCSP_DB_PROVIDER");
      if (null != explicitResponderCert) {
         dbService.insertCertificate(explicitResponderCert);
      }

      Util.checkNotNull("issuerCert", issuerCert);
      dbService.insertCertificate(issuerCert);
      if (null != requestSigningPublicCert && null != requestSigningPrivateKey) {
         dbService.insertCertificate(requestSigningPublicCert);
         dbService.insertPrivateKeyByCertificate(requestSigningPublicCert, requestSigningPrivateKey);
      }

      return dbService;
   }

   private CertPathCtx initCertPathCtx(X500Principal issuerDn, com.rsa.certj.cert.X509Certificate rsaIssuerCert, com.rsa.certj.cert.X509Certificate rsaExplicitRespondersCert, DatabaseService dbService) {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("issuerCert", rsaIssuerCert);
      Util.checkNotNull("dbService", dbService);
      com.rsa.certj.cert.X509Certificate[] trustedCerts;
      if (null != rsaExplicitRespondersCert) {
         trustedCerts = new com.rsa.certj.cert.X509Certificate[]{rsaIssuerCert, rsaExplicitRespondersCert};
      } else {
         trustedCerts = new com.rsa.certj.cert.X509Certificate[]{rsaIssuerCert};
      }

      AbstractCertRevocContext context = this.getContext();
      AbstractCertRevocConstants.AttributeUsage responderUrlUsage = context.getOcspResponderUrlUsage(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponderUrlUsage={0}", responderUrlUsage);
      }

      URI responderUrl = context.getOcspResponderUrl(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponderUrl={0}", responderUrl);
      }

      int pathOptions = 4;
      if (AttributeUsage.OVERRIDE == responderUrlUsage) {
         if (null == responderUrl) {
            throw new IllegalStateException("OCSP responder URI override is null, preventing OCSP checking for cert issuer \"" + issuerDn + "\"");
         }

         pathOptions |= 2048;
      }

      byte[][] policies = (byte[][])null;
      Date validationTime = new Date();
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "Validation time=\"{0}\"", validationTime);
      }

      CertPathCtx pathCtx = new CertPathCtx(pathOptions, trustedCerts, policies, validationTime, dbService);
      return pathCtx;
   }

   private String[] initDestList(X500Principal issuerDn) {
      Util.checkNotNull("issuerDn", issuerDn);
      AbstractCertRevocContext context = this.getContext();
      URI responderUrl = context.getOcspResponderUrl(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponderUrl={0}", responderUrl);
      }

      String[] destList = null;
      if (null != responderUrl) {
         destList = new String[]{responderUrl.toASCIIString()};
      }

      return destList;
   }

   private OCSPResponder initOcspResponder(com.rsa.certj.cert.X509Certificate requestSigningPublicCert, com.rsa.certj.cert.X509Certificate explicitResponderCert, X500Principal issuerDn, com.rsa.certj.cert.X509Certificate issuerCert, DatabaseService dbService) throws InvalidParameterException {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("issuerCert", issuerCert);
      Util.checkNotNull("dbService", dbService);
      AbstractCertRevocContext context = this.getContext();
      int ocspResponderProfile = 0;
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "Using OCSP responder profile={0}", Integer.valueOf(ocspResponderProfile));
      }

      int ocspResponderFlags = 8;
      boolean requestNonceEnabled = context.isOcspNonceEnabled(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspNonceEnabled={0}", requestNonceEnabled);
      }

      if (!requestNonceEnabled) {
         ocspResponderFlags |= 1;
      }

      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "ocspResponderFlags={0}", ocspResponderFlags);
      }

      String[] proxyList = null;
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "proxyList is empty, reverting to System Properties.");
      }

      OCSPRequestControl ocspRequestControl = this.initOcspRequestControl(requestSigningPublicCert);
      com.rsa.certj.cert.X509Certificate[] rsaIssuerCerts = new com.rsa.certj.cert.X509Certificate[]{issuerCert};
      int timeTolerance = context.getOcspTimeTolerance(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspTimeTolerance={0}", timeTolerance);
      }

      String[] destList = this.initDestList(issuerDn);
      OCSPResponder ocspResponder = new OCSPResponder(ocspResponderProfile, ocspResponderFlags, destList, (String[])proxyList, ocspRequestControl, explicitResponderCert, rsaIssuerCerts, dbService, timeTolerance);
      return ocspResponder;
   }

   private OCSPRequestControl initOcspRequestControl(com.rsa.certj.cert.X509Certificate requestSigningPublicCert) throws InvalidParameterException {
      AbstractCertRevocContext context = this.getContext();
      String signatureAlg = "SHA1/RSA/PKCS1Block01Pad";
      String digestAlg = "SHA1";
      com.rsa.certj.cert.X509Certificate[] extraCerts = null;
      String extraCertsStr = null;
      X509V3Extensions requestExtensions = null;
      String requestExtensionsStr = null;
      String requestSigningPublicCertStr = null;
      if (requestSigningPublicCert != null && requestSigningPublicCert.getSubjectName() != null) {
         requestSigningPublicCertStr = requestSigningPublicCert.getSubjectName().toStringRFC2253();
      }

      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "Request signing: signingCert={0}, digestAlg={1}, signatureAlg={2}, extraCerts={3}, requestExtensions={4}", requestSigningPublicCertStr, digestAlg, signatureAlg, extraCertsStr, requestExtensionsStr);
      }

      OCSPRequestControl ocspRequestControl = new OCSPRequestControl(requestSigningPublicCert, digestAlg, signatureAlg, (com.rsa.certj.cert.X509Certificate[])extraCerts, (X509V3Extensions)requestExtensions);
      return ocspRequestControl;
   }

   private OCSP initOcspProvider(X500Principal issuerDn, OCSPResponder ocspResponder) throws UnsupportedEncodingException, InvalidParameterException, CertificateException, NameException {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("ocspResponder", ocspResponder);
      AbstractCertRevocContext context = this.getContext();
      long responseTimeout = context.getOcspResponseTimeout(issuerDn);
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OcspResponseTimeout={0}", responseTimeout);
      }

      StringBuilder sb = new StringBuilder();
      sb.append("timeoutSecs=");
      sb.append(responseTimeout);
      sb.append("\n");
      if (context.isLoggable(Level.FINEST)) {
         context.log(Level.FINEST, "OCSP configStream=\"{0}\"", sb.toString());
      }

      InputStream configStream = new ByteArrayInputStream(sb.toString().getBytes("US-ASCII"));
      OCSP ocspProvider = new OCSP("OCSP_PROVIDER", ocspResponder, configStream);
      return ocspProvider;
   }

   private CertRevocationInfo checkCertRevocation(X500Principal issuerDn, com.rsa.certj.cert.X509Certificate rsaCertToCheck, com.rsa.certj.cert.X509Certificate rsaIssuerCert, com.rsa.certj.cert.X509Certificate rsaExplicitRespondersCert, JSAFE_PrivateKey rsaRequestSigningPrivateKey, com.rsa.certj.cert.X509Certificate rsaRequestSigningPublicCert) throws InvalidParameterException, ProviderManagementException, InvalidUseException, NoServiceException, DatabaseException, UnsupportedEncodingException, CertificateException, NameException, CertStatusException {
      Util.checkNotNull("issuerDn", issuerDn);
      Util.checkNotNull("certToCheck", rsaCertToCheck);
      Util.checkNotNull("issuerCert", rsaIssuerCert);
      CertJ certJ = this.initCertJ();
      DatabaseService dbService = this.initDbService(rsaExplicitRespondersCert, rsaIssuerCert, rsaRequestSigningPublicCert, rsaRequestSigningPrivateKey, certJ);
      CertPathCtx pathCtx = this.initCertPathCtx(issuerDn, rsaIssuerCert, rsaExplicitRespondersCert, dbService);
      OCSPResponder ocspResponder = this.initOcspResponder(rsaRequestSigningPublicCert, rsaExplicitRespondersCert, issuerDn, rsaIssuerCert, dbService);
      OCSP ocspProvider = this.initOcspProvider(issuerDn, ocspResponder);
      certJ.registerService(ocspProvider);
      CertRevocationInfo revocationInfo = certJ.checkCertRevocation(pathCtx, rsaCertToCheck);
      return revocationInfo;
   }

   private CertRevocStatus evalRevocationInfo(X509Certificate certToCheck, CertRevocationInfo revocationInfo) {
      Util.checkNotNull("certToCheck", certToCheck);
      AbstractCertRevocContext context = this.getContext();
      if (null == revocationInfo) {
         if (context.isLoggable(Level.FINER)) {
            context.log(Level.FINER, "Revocation status unavailable from OCSP (CertRevocationInfo is null).");
         }

         return null;
      } else {
         Boolean revoked = RsaUtil.evalRevocStatusCode(SelectableMethod.OCSP, revocationInfo.getStatus(), context.getLogListener());
         if (null == revoked) {
            return null;
         } else {
            int evidenceType = revocationInfo.getType();
            if (evidenceType != 2) {
               if (context.isLoggable(Level.FINE)) {
                  context.log(Level.FINE, "Revocation status unavailable from OCSP, unexpected evidence type {0}.", evidenceType);
               }

               return null;
            } else {
               OCSPEvidence evidence = (OCSPEvidence)revocationInfo.getEvidence();
               if (null == evidence) {
                  if (context.isLoggable(Level.FINE)) {
                     context.log(Level.FINE, "Revocation status unavailable from OCSP, no evidence available.");
                  }

                  return null;
               } else {
                  int flags = evidence.getFlags();
                  boolean nonceIgnored = isNonceIgnored(flags);
                  Date revocationTime = null;
                  Integer reasonCode = null;
                  OCSPRevocationInfo revokeInfo = evidence.getRevocationInfo();
                  if (null != revokeInfo) {
                     revocationTime = revokeInfo.getRevocationTime();
                     reasonCode = revokeInfo.getReasonCode();
                  }

                  HashMap addlProps = new HashMap(10);
                  addlProps.put("Flags", Integer.toString(flags, 2));
                  addlProps.put("ProducedAt", CertRevocStatus.format(evidence.getProducedAt()));
                  addlProps.put("RevocationTime", CertRevocStatus.format(revocationTime));
                  addlProps.put("ReasonCode", null == reasonCode ? null : reasonCode.toString());
                  return new CertRevocStatus(SelectableMethod.OCSP, certToCheck.getSubjectX500Principal(), certToCheck.getIssuerX500Principal(), certToCheck.getSerialNumber(), evidence.getThisUpdate(), evidence.getNextUpdate(), revoked, nonceIgnored, addlProps);
               }
            }
         }
      }
   }

   private static boolean isNonceIgnored(int flags) {
      return (flags & 1) != 0;
   }
}
