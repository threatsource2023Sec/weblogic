package org.opensaml.saml.saml2.metadata;

import javax.xml.namespace.QName;
import org.opensaml.saml.common.SAMLObject;

public interface EncryptionMethod extends org.opensaml.xmlsec.encryption.EncryptionMethod, SAMLObject {
   String DEFAULT_ELEMENT_LOCAL_NAME = "EncryptionMethod";
   QName DEFAULT_ELEMENT_NAME = new QName("urn:oasis:names:tc:SAML:2.0:metadata", "EncryptionMethod", "md");
}
