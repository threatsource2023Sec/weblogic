package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.KeyExchangeToken;

public class KeyExchangeTokenBuilder extends AbstractWSTrustObjectBuilder {
   public KeyExchangeToken buildObject() {
      return (KeyExchangeToken)this.buildObject(KeyExchangeToken.ELEMENT_NAME);
   }

   public KeyExchangeToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyExchangeTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
