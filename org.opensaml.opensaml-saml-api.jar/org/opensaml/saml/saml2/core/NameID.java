package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface NameID extends SAMLObject, NameIDType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "NameID", "saml2");
   String TYPE_LOCAL_NAME = "NameIDType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "NameIDType", "saml2");
}
