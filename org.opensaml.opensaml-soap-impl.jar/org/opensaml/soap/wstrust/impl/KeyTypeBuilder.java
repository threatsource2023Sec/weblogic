package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.KeyType;

public class KeyTypeBuilder extends AbstractWSTrustObjectBuilder {
   public KeyType buildObject() {
      return (KeyType)this.buildObject(KeyType.ELEMENT_NAME);
   }

   public KeyType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
