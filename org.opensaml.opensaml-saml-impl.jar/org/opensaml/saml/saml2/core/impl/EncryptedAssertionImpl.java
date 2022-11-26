package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.EncryptedAssertion;

public class EncryptedAssertionImpl extends EncryptedElementTypeImpl implements EncryptedAssertion {
   protected EncryptedAssertionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
