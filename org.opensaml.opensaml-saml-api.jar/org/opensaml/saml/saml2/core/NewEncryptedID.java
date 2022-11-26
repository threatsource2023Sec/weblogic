package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface NewEncryptedID extends EncryptedElementType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "NewEncryptedID";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:protocol", "NewEncryptedID", "saml2p");
}
