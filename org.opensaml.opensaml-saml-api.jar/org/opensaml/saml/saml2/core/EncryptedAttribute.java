package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface EncryptedAttribute extends EncryptedElementType {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedAttribute";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAttribute", "saml2");
}
