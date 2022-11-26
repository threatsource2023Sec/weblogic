package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface Query extends SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Query";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "Query", "saml1p");
   String TYPE_LOCAL_NAME = "QueryAbstractType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "QueryAbstractType", "saml1p");
}
