package weblogic.security.spi;

import javax.security.auth.callback.CallbackHandler;
import weblogic.security.service.ContextHandler;

public interface IdentityAsserterV2 {
   String X509_TYPE = "X.509";
   String AU_TYPE = "AuthenticatedUser";
   String CSI_PRINCIPAL_TYPE = "CSI.PrincipalName";
   String CSI_ANONYMOUS_TYPE = "CSI.ITTAnonymous";
   String CSI_X509_CERTCHAIN_TYPE = "CSI.X509CertChain";
   String CSI_DISTINGUISHED_NAME_TYPE = "CSI.DistinguishedName";
   String WSSE_PASSWORD_DIGEST_TYPE = "wsse:PasswordDigest";
   String SAML_ASSERTION_TYPE = "SAML.Assertion";
   String SAML_ASSERTION_B64_TYPE = "SAML.Assertion64";
   String SAML_ASSERTION_DOM_TYPE = "SAML.Assertion.DOM";
   String SAML2_ASSERTION_TYPE = "SAML2.Assertion";
   String SAML2_ASSERTION_DOM_TYPE = "SAML2.Assertion.DOM";
   String WWW_AUTHENTICATE_NEGOTIATE = "WWW-Authenticate.Negotiate";
   String AUTHORIZATION_NEGOTIATE = "Authorization.Negotiate";
   String KERBEROS_V5_AP_REQ = "Kerberosv5_AP_REQ";
   String GSS_KERBEROS_V5_AP_REQ = "GSS_Kerberosv5_AP_REQ";
   String KERBEROS_V5_AP_REQ_1510 = "Kerberosv5_AP_REQ1510";
   String GSS_KERBEROS_V5_AP_REQ_1510 = "GSS_Kerberosv5_AP_REQ1510";
   String KERBEROS_V5_AP_REQ_4120 = "Kerberosv5_AP_REQ4120";
   String GSS_KERBEROS_V5_AP_REQ_4120 = "GSS_Kerberosv5_AP_REQ4120";

   CallbackHandler assertIdentity(String var1, Object var2, ContextHandler var3) throws IdentityAssertionException;
}
