package org.opensaml.security.httpclient;

public final class HttpClientSecurityConstants {
   public static final String CONTEXT_KEY_TRUST_ENGINE = "opensaml.TrustEngine";
   public static final String CONTEXT_KEY_CRITERIA_SET = "opensaml.CriteriaSet";
   public static final String CONTEXT_KEY_SERVER_TLS_CREDENTIAL_TRUSTED = "opensaml.ServerTLSCredentialTrusted";
   public static final String CONTEXT_KEY_CLIENT_TLS_CREDENTIAL = "opensaml.ClientTLSCredential";
   public static final String CONTEXT_KEY_TLS_PROTOCOLS = "javasupport.TLSProtocols";
   public static final String CONTEXT_KEY_TLS_CIPHER_SUITES = "javasupport.TLSCipherSuites";
   public static final String CONTEXT_KEY_HOSTNAME_VERIFIER = "javasupport.HostnameVerifier";

   private HttpClientSecurityConstants() {
   }
}
