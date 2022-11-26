package org.opensaml.saml.saml1.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface StatusDetail extends SAMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "StatusDetail";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetail", "saml1p");
   String TYPE_LOCAL_NAME = "StatusDetailType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:1.0:protocol", "StatusDetailType", "saml1p");
}
