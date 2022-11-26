package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Statement extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Statement";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "Statement", "saml2");
   String TYPE_LOCAL_NAME = "StatementAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "StatementAbstractType", "saml2");
}
