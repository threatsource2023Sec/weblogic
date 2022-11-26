package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Nonce;

public class NonceImpl extends EncodedStringImpl implements Nonce {
   public NonceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
