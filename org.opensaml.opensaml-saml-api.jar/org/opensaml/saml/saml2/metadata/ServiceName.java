package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;

public interface ServiceName extends LocalizedName {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ServiceName";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceName", "md");
   String TYPE_LOCAL_NAME = "localizedNameType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "localizedNameType", "md");
   String LANG_ATTRIB_NAME = "lang";
}
