package weblogic.security.spi;

import javax.security.auth.callback.CallbackHandler;

public interface IdentityAsserter {
   String X509_TYPE = "X.509";
   String AU_TYPE = "AuthenticatedUser";
   String WEBLOGIC_JWT_TOKEN_TYPE = "weblogic-jwt-token";
   String CSI_PRINCIPAL_TYPE = "CSI.PrincipalName";
   String CSI_ANONYMOUS_TYPE = "CSI.ITTAnonymous";
   String CSI_X509_CERTCHAIN_TYPE = "CSI.X509CertChain";
   String CSI_DISTINGUISHED_NAME_TYPE = "CSI.DistinguishedName";
   String WSSE_PASSWORD_DIGEST_TYPE = "wsse:PasswordDigest";
   String WWW_AUTHENTICATE_NEGOTIATE = "WWW-Authenticate.Negotiate";
   String AUTHORIZATION_NEGOTIATE = "Authorization.Negotiate";
   String OIDC_TYPE = "Idcs_user_assertion";

   CallbackHandler assertIdentity(String var1, Object var2) throws IdentityAssertionException;
}
