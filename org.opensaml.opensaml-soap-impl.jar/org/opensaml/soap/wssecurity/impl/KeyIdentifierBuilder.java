package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.KeyIdentifier;

public class KeyIdentifierBuilder extends AbstractWSSecurityObjectBuilder {
   public KeyIdentifier buildObject() {
      return (KeyIdentifier)this.buildObject(KeyIdentifier.ELEMENT_NAME);
   }

   public KeyIdentifier buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeyIdentifierImpl(namespaceURI, localName, namespacePrefix);
   }
}
