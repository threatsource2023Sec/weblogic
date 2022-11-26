package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;

public interface ServiceDescription extends LocalizedName {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ServiceDescription";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceDescription", "md");
   String TYPE_LOCAL_NAME = "localizedNameType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "localizedNameType", "md");
}
