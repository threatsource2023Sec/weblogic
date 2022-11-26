package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.NewEncryptedID;

public class NewEncryptedIDImpl extends EncryptedElementTypeImpl implements NewEncryptedID {
   protected NewEncryptedIDImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
