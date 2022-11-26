package org.opensaml.saml.saml1.core;

import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.saml.common.SAMLObject;

public interface AuthenticationStatement extends SAMLObject, SubjectStatement {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthenticationStatement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatement", "saml1");
   String TYPE_LOCAL_NAME = "AuthenticationStatementType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:assertion", "AuthenticationStatementType", "saml1");
   String AUTHENTICATIONMETHOD_ATTRIB_NAME = "AuthenticationMethod";
   String AUTHENTICATIONINSTANT_ATTRIB_NAME = "AuthenticationInstant";
   String KERBEROS_AUTHN_METHOD = "urn:ietf:rfc:1510";
   String HARDWARE_TOKEN_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:HardwareToken";
   String PASSWORD_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:password";
   String X509_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:X509-PKI";
   String PGP_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:PGP";
   String SPKI_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:SPKI";
   String XKMS_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:XKMS";
   String XML_DSIG_AUTHN_METHOD = "urn:ietf:rfc:3075";
   String SRP_AUTHN_METHOD = "urn:ietf:rfc:2945";
   String TLS_CLIENT_AUTHN_METHOD = "urn:ietf:rfc:2246";
   String UNSPECIFIED_AUTHN_METHOD = "urn:oasis:names:tc:SAML:1.0:am:unspecified";

   String getAuthenticationMethod();

   void setAuthenticationMethod(String var1);

   DateTime getAuthenticationInstant();

   void setAuthenticationInstant(DateTime var1);

   SubjectLocality getSubjectLocality();

   void setSubjectLocality(SubjectLocality var1);

   List getAuthorityBindings();
}
