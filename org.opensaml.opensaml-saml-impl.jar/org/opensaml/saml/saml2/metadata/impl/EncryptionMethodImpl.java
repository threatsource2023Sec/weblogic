package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.EncryptionMethod;

public class EncryptionMethodImpl extends org.opensaml.xmlsec.encryption.impl.EncryptionMethodImpl implements EncryptionMethod {
   protected EncryptionMethodImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
