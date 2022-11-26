package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.KeyReference;

public class KeyReferenceImpl extends ReferenceTypeImpl implements KeyReference {
   protected KeyReferenceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
