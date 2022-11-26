package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.schema.XSAny;
import org.opensaml.saml.common.SAMLObject;

public interface AuthnContextDecl extends SAMLObject, XSAny {
   String DEFAULT_ELEMENT_LOCAL_NAME = "AuthnContextDecl";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDecl", "saml2");
}
