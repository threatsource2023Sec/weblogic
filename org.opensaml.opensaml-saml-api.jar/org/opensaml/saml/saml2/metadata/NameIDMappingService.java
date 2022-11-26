package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;

public interface NameIDMappingService extends Endpoint {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NameIDMappingService";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDMappingService", "md");
}
