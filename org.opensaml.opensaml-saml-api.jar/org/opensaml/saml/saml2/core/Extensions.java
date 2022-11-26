package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;
import org.opensaml.core.xml.ElementExtensibleXMLObject;
import org.opensaml.saml.common.SAMLObject;

public interface Extensions extends SAMLObject, ElementExtensibleXMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "Extensions";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "Extensions", "saml2p");
   String TYPE_LOCAL_NAME = "ExtensionsType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ExtensionsType", "saml2p");
}
