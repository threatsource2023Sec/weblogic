package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.MGF;

public class MGFImpl extends AlgorithmIdentifierTypeImpl implements MGF {
   protected MGFImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
