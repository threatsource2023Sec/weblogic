package weblogic.security.spi;

import javax.security.auth.Subject;
import weblogic.security.service.ContextHandler;

public interface CredentialMapperV2 {
   String PASSWORD_TYPE = "weblogic.Password";
   String USER_PASSWORD_TYPE = "weblogic.UserPassword";
   String PKI_KEY_PAIR_TYPE = "weblogic.pki.Keypair";
   String PKI_TRUSTED_CERTIFICATE_TYPE = "weblogic.pki.TrustedCertificate";
   String SPNEGO_TOKEN_TYPE = "com.bea.SPNEGO.token";
   String SAML_ASSERTION_TYPE = "SAML.Assertion";
   String SAML_ASSERTION_B64_TYPE = "SAML.Assertion64";
   String SAML_ASSERTION_DOM_TYPE = "SAML.Assertion.DOM";
   String SAML2_ASSERTION_TYPE = "SAML2.Assertion";
   String SAML2_ASSERTION_DOM_TYPE = "SAML2.Assertion.DOM";
   String KERBEROS_V5_AP_REQ = "Kerberosv5_AP_REQ";
   String GSS_KERBEROS_V5_AP_REQ = "GSS_Kerberosv5_AP_REQ";
   String KERBEROS_V5_AP_REQ_1510 = "Kerberosv5_AP_REQ1510";
   String GSS_KERBEROS_V5_AP_REQ_1510 = "GSS_Kerberosv5_AP_REQ1510";
   String KERBEROS_V5_AP_REQ_4120 = "Kerberosv5_AP_REQ4120";
   String GSS_KERBEROS_V5_AP_REQ_4120 = "GSS_Kerberosv5_AP_REQ4120";
   String WEBLOGIC_OAUTH2_JWT_ACCESS_TOKEN_TYPE = "weblogic.oauth2.jwt.access.token";

   Object getCredential(Subject var1, String var2, Resource var3, ContextHandler var4, String var5);

   Object[] getCredentials(Subject var1, Subject var2, Resource var3, ContextHandler var4, String var5);
}
