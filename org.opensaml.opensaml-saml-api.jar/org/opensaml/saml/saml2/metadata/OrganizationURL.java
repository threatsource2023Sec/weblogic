package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;

public interface OrganizationURL extends LocalizedURI {
   String DEFAULT_ELEMENT_LOCAL_NAME = "OrganizationURL";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationURL", "md");
   String TYPE_LOCAL_NAME = "localizedURIType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "localizedURIType", "md");
}
