package weblogic.management.configuration;

import weblogic.security.pki.revocation.common.AbstractCertRevocConstants;
import weblogic.security.pki.revocation.common.AbstractCertRevocConstants.AttributeUsage;
import weblogic.security.pki.revocation.wls.WLSCertRevocConstants;
import weblogic.security.pki.revocation.wls.WLSCertRevocConstants.ExplicitTrustMethod;

public interface CertRevocCaMBean extends ConfigurationMBean {
   String OCSP_EXPLICIT_TRUST_METHOD_NONE = ExplicitTrustMethod.NONE.toString();
   String OCSP_EXPLICIT_TRUST_METHOD_USE_SUBJECT = ExplicitTrustMethod.USE_SUBJECT.toString();
   String OCSP_EXPLICIT_TRUST_METHOD_USE_ISSUER_SERIAL_NUMBER = ExplicitTrustMethod.USE_ISSUER_SERIAL_NUMBER.toString();
   String USAGE_FAILOVER = AttributeUsage.FAILOVER.toString();
   String USAGE_OVERRIDE = AttributeUsage.OVERRIDE.toString();
   String DEFAULT_DISTINGUISHED_NAME = null;
   boolean DEFAULT_CHECKING_DISABLED = AbstractCertRevocConstants.DEFAULT_CHECKING_DISABLED;
   String DEFAULT_OCSP_RESPONDER_URL = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_URL_STRING;
   String DEFAULT_OCSP_RESPONDER_URL_USAGE = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_URL_USAGE.toString();
   String DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD = WLSCertRevocConstants.DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD.toString();
   String DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME_STRING;
   String DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME_STRING;
   String DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER = AbstractCertRevocConstants.DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER_STRING;
   String DEFAULT_CRL_DP_URL = AbstractCertRevocConstants.DEFAULT_CRL_DP_URL_STRING;
   String DEFAULT_CRL_DP_URL_USAGE = AbstractCertRevocConstants.DEFAULT_CRL_DP_URL_USAGE.toString();

   String getDistinguishedName();

   void setDistinguishedName(String var1);

   boolean isCheckingDisabled();

   void setCheckingDisabled(boolean var1);

   boolean isFailOnUnknownRevocStatus();

   void setFailOnUnknownRevocStatus(boolean var1);

   String getMethodOrder();

   void setMethodOrder(String var1);

   String getOcspResponderUrl();

   void setOcspResponderUrl(String var1);

   String getOcspResponderUrlUsage();

   void setOcspResponderUrlUsage(String var1);

   String getOcspResponderExplicitTrustMethod();

   void setOcspResponderExplicitTrustMethod(String var1);

   String getOcspResponderCertSubjectName();

   void setOcspResponderCertSubjectName(String var1);

   String getOcspResponderCertIssuerName();

   void setOcspResponderCertIssuerName(String var1);

   String getOcspResponderCertSerialNumber();

   void setOcspResponderCertSerialNumber(String var1);

   boolean isOcspNonceEnabled();

   void setOcspNonceEnabled(boolean var1);

   boolean isOcspResponseCacheEnabled();

   void setOcspResponseCacheEnabled(boolean var1);

   long getOcspResponseTimeout();

   void setOcspResponseTimeout(long var1);

   int getOcspTimeTolerance();

   void setOcspTimeTolerance(int var1);

   boolean isCrlDpEnabled();

   void setCrlDpEnabled(boolean var1);

   long getCrlDpDownloadTimeout();

   void setCrlDpDownloadTimeout(long var1);

   boolean isCrlDpBackgroundDownloadEnabled();

   void setCrlDpBackgroundDownloadEnabled(boolean var1);

   String getCrlDpUrl();

   void setCrlDpUrl(String var1);

   String getCrlDpUrlUsage();

   void setCrlDpUrlUsage(String var1);
}
