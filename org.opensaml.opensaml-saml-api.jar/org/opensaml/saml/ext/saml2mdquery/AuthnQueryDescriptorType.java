package org.opensaml.saml.ext.saml2mdquery;

import javax.xml.namespace.QName;

public interface AuthnQueryDescriptorType extends QueryDescriptorType {
   String TYPE_LOCAL_NAME = "AuthnQueryDescriptorType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:metadata:ext:query", "AuthnQueryDescriptorType", "query");
}
