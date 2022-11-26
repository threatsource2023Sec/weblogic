package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.ComputedKey;

public class ComputedKeyBuilder extends AbstractWSTrustObjectBuilder {
   public ComputedKey buildObject() {
      return (ComputedKey)this.buildObject(ComputedKey.ELEMENT_NAME);
   }

   public ComputedKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ComputedKeyImpl(namespaceURI, localName, namespacePrefix);
   }
}
