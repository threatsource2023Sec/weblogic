package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.PublicKey;

public class PublicKeyImpl extends ECPointTypeImpl implements PublicKey {
   protected PublicKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
