package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface ManageNameIDResponse extends StatusResponseType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "ManageNameIDResponse";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponse", "saml2p");
   String TYPE_LOCAL_NAME = "ManageNameIDResponseType";
   QName TYPE_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "ManageNameIDResponseType", "saml2p");
}
