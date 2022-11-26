package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnContextClassRef extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContextClassRef";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml2");

   String getAuthnContextClassRef();

   void setAuthnContextClassRef(String var1);
}
