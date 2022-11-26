package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.EncryptedAttribute;

public class EncryptedAttributeImpl extends EncryptedElementTypeImpl implements EncryptedAttribute {
   protected EncryptedAttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
