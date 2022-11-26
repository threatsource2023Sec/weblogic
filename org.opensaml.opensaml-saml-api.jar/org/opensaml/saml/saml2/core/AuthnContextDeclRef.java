package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnContextDeclRef extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContextDeclRef";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDeclRef", "saml2");

   String getAuthnContextDeclRef();

   void setAuthnContextDeclRef(String var1);
}
