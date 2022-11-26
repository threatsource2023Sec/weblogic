package org.opensaml.saml.saml2.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnContext extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContext";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContext", "saml2");
   String TYPE_LOCAL_NAME = "AuthnContextType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextType", "saml2");
   String IP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocol";
   String IP_PASSWORD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:InternetProtocolPassword";
   String KERBEROS_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos";
   String MOFU_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorUnregistered";
   String MTFU_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorUnregistered";
   String MOFC_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileOneFactorContract";
   String MTFC_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:MobileTwoFactorContract";
   String PASSWORD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Password";
   String PPT_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport";
   String PREVIOUS_SESSION_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession";
   String X509_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:X509";
   String PGP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PGP";
   String SPKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SPKI";
   String XML_DSIG_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:XMLDSig";
   String SMARTCARD_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Smartcard";
   String SMARTCARD_PKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SmartcardPKI";
   String SOFTWARE_PKI_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SoftwarePKI";
   String TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:Telephony";
   String NOMAD_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:NomadTelephony";
   String PERSONAL_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:PersonalTelephony";
   String AUTHENTICATED_TELEPHONY_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:AuthenticatedTelephony";
   String SRP_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:SecureRemotePassword";
   String TLS_CLIENT_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:TLSClient";
   String TIME_SYNC_TOKEN_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:TimeSyncToken";
   String UNSPECIFIED_AUTHN_CTX = "urn:oasis:names:tc:SAML:2.0:ac:classes:unspecified";

   AuthnContextClassRef getAuthnContextClassRef();

   void setAuthnContextClassRef(AuthnContextClassRef var1);

   AuthnContextDecl getAuthContextDecl();

   void setAuthnContextDecl(AuthnContextDecl var1);

   AuthnContextDeclRef getAuthnContextDeclRef();

   void setAuthnContextDeclRef(AuthnContextDeclRef var1);

   List getAuthenticatingAuthorities();
}
