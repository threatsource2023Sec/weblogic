package weblogic.security.pki.revocation.wls;

public class WLSCertRevocConstants {
   public static final ExplicitTrustMethod DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD;

   static {
      DEFAULT_OCSP_RESPONDER_EXPLICIT_TRUST_METHOD = WLSCertRevocConstants.ExplicitTrustMethod.NONE;
   }

   public static enum ExplicitTrustMethod {
      NONE,
      USE_SUBJECT,
      USE_ISSUER_SERIAL_NUMBER;
   }
}
