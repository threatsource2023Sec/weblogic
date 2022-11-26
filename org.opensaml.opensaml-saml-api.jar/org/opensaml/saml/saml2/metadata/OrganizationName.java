package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;

public interface OrganizationName extends LocalizedName {
   String DEFAULT_ELEMENT_LOCAL_NAME = "OrganizationName";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "OrganizationName", "md");
   String TYPE_LOCAL_NAME = "localizedNameType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "localizedNameType", "md");
}
