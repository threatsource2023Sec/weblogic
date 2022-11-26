package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface AuthenticatingAuthority extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthenticatingAuthority";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthenticatingAuthority", "saml2");

   String getURI();

   void setURI(String var1);
}
