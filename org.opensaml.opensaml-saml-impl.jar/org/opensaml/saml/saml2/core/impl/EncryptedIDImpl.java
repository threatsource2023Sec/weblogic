package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.EncryptedID;

public class EncryptedIDImpl extends EncryptedElementTypeImpl implements EncryptedID {
   protected EncryptedIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
