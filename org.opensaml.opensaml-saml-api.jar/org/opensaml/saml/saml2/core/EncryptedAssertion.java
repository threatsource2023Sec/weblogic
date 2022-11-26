package org.opensaml.saml.saml2.core;

import javax.xml.namespace.QName;

public interface EncryptedAssertion extends EncryptedElementType, Evidentiary {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptedAssertion";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion", "saml2");
}
