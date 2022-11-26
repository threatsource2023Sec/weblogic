package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.BinaryExchange;

public class BinaryExchangeBuilder extends AbstractWSTrustObjectBuilder {
   public BinaryExchange buildObject() {
      return (BinaryExchange)this.buildObject(BinaryExchange.ELEMENT_NAME);
   }

   public BinaryExchange buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new BinaryExchangeImpl(namespaceURI, localName, namespacePrefix);
   }
}
