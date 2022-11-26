package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Issuer extends NameIDType, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Issuer";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml2");
   String TYPE_LOCAL_NAME = "IssuerType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "IssuerType", "saml2");
}
