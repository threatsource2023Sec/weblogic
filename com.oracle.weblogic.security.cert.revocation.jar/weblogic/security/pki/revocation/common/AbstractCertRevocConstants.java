package weblogic.security.pki.revocation.common;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public class AbstractCertRevocConstants {
   public static final Boolean DEFAULT_CHECKING_ENABLED;
   public static final Boolean DEFAULT_CHECKING_DISABLED;
   public static final Boolean DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS;
   public static final CertRevocCheckMethodList.SelectableMethodList DEFAULT_SELECTABLE_METHOD_LIST;
   public static final CertRevocCheckMethodList DEFAULT_METHOD_ORDER;
   public static final URI DEFAULT_OCSP_RESPONDER_URL;
   public static final String DEFAULT_OCSP_RESPONDER_URL_STRING;
   public static final AttributeUsage DEFAULT_OCSP_RESPONDER_URL_USAGE;
   public static final X509Certificate DEFAULT_OCSP_RESPONDER_TRUSTED_CERT;
   public static final Boolean DEFAULT_OCSP_NONCE_ENABLED;
   public static final PrivateKey DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY;
   public static final String DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY_ALIAS;
   public static final X509Certificate DEFAULT_OCSP_REQUEST_SIGNING_CERT;
   public static final Boolean DEFAULT_OCSP_RESPONSE_CACHE_ENABLED;
   public static final Integer DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY;
   public static final Integer DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT;
   public static final Long DEFAULT_OCSP_RESPONSE_TIMEOUT;
   public static final long MIN_OCSP_RESPONSE_TIMEOUT = 1L;
   public static final long MAX_OCSP_RESPONSE_TIMEOUT = 300L;
   public static final Integer DEFAULT_OCSP_TIME_TOLERANCE;
   public static final int MIN_OCSP_TIME_TOLERANCE = 0;
   public static final int MAX_OCSP_TIME_TOLERANCE = 900;
   public static final CrlCacheType DEFAULT_CRL_CACHE_TYPE;
   public static final File DEFAULT_CRL_CACHE_IMPORT_DIR;
   public static final File DEFAULT_CRL_CACHE_TYPE_FILE_DIR;
   public static final String DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME;
   public static final Integer DEFAULT_CRL_CACHE_TYPE_LDAP_PORT;
   public static final Integer DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT;
   public static final int MIN_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = 1;
   public static final int MAX_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = 300;
   public static final Integer DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT;
   public static final Boolean DEFAULT_CRL_DP_ENABLED;
   public static final Long DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT;
   public static final long MIN_CRL_DP_DOWNLOAD_TIMEOUT = 1L;
   public static final long MAX_CRL_DP_DOWNLOAD_TIMEOUT = 300L;
   public static final Boolean DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED;
   public static final URI DEFAULT_CRL_DP_URL;
   public static final String DEFAULT_CRL_DP_URL_STRING;
   public static final AttributeUsage DEFAULT_CRL_DP_URL_USAGE;
   public static final X500Principal DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME_STRING;
   public static final X500Principal DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME_STRING;
   public static final BigInteger DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER;
   public static final String DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER_STRING;

   static {
      DEFAULT_CHECKING_ENABLED = Boolean.FALSE;
      DEFAULT_CHECKING_DISABLED = Boolean.FALSE;
      DEFAULT_FAIL_ON_UNKNOWN_REVOC_STATUS = Boolean.FALSE;
      DEFAULT_SELECTABLE_METHOD_LIST = CertRevocCheckMethodList.SelectableMethodList.OCSP_THEN_CRL;
      DEFAULT_METHOD_ORDER = new CertRevocCheckMethodList(DEFAULT_SELECTABLE_METHOD_LIST);
      DEFAULT_OCSP_RESPONDER_URL = null;
      DEFAULT_OCSP_RESPONDER_URL_STRING = null;
      DEFAULT_OCSP_RESPONDER_URL_USAGE = AbstractCertRevocConstants.AttributeUsage.FAILOVER;
      DEFAULT_OCSP_RESPONDER_TRUSTED_CERT = null;
      DEFAULT_OCSP_NONCE_ENABLED = Boolean.FALSE;
      DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY = null;
      DEFAULT_OCSP_REQUEST_SIGNING_PRIVATE_KEY_ALIAS = null;
      DEFAULT_OCSP_REQUEST_SIGNING_CERT = null;
      DEFAULT_OCSP_RESPONSE_CACHE_ENABLED = Boolean.TRUE;
      DEFAULT_OCSP_RESPONSE_CACHE_CAPACITY = 1024;
      DEFAULT_OCSP_RESPONSE_CACHE_REFRESH_PERIOD_PERCENT = 100;
      DEFAULT_OCSP_RESPONSE_TIMEOUT = 10L;
      DEFAULT_OCSP_TIME_TOLERANCE = 0;
      DEFAULT_CRL_CACHE_TYPE = AbstractCertRevocConstants.CrlCacheType.FILE;
      DEFAULT_CRL_CACHE_IMPORT_DIR = null;
      DEFAULT_CRL_CACHE_TYPE_FILE_DIR = null;
      DEFAULT_CRL_CACHE_TYPE_LDAP_HOST_NAME = null;
      DEFAULT_CRL_CACHE_TYPE_LDAP_PORT = -1;
      DEFAULT_CRL_CACHE_TYPE_LDAP_SEARCH_TIMEOUT = 10;
      DEFAULT_CRL_CACHE_REFRESH_PERIOD_PERCENT = 100;
      DEFAULT_CRL_DP_ENABLED = Boolean.TRUE;
      DEFAULT_CRL_DP_DOWNLOAD_TIMEOUT = 10L;
      DEFAULT_CRL_DP_BACKGROUND_DOWNLOAD_ENABLED = Boolean.FALSE;
      DEFAULT_CRL_DP_URL = null;
      DEFAULT_CRL_DP_URL_STRING = null;
      DEFAULT_CRL_DP_URL_USAGE = AbstractCertRevocConstants.AttributeUsage.FAILOVER;
      DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME = null;
      DEFAULT_OCSP_RESPONDER_CERT_SUBJECT_NAME_STRING = null;
      DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME = null;
      DEFAULT_OCSP_RESPONDER_CERT_ISSUER_NAME_STRING = null;
      DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER = null;
      DEFAULT_OCSP_RESPONDER_CERT_SERIAL_NUMBER_STRING = null;
   }

   public static enum CrlCacheType {
      FILE,
      LDAP;
   }

   public static enum AttributeUsage {
      FAILOVER,
      OVERRIDE;
   }
}
