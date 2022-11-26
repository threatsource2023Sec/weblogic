package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.KeySize;

public class KeySizeBuilder extends AbstractWSTrustObjectBuilder {
   public KeySize buildObject() {
      return (KeySize)this.buildObject(KeySize.ELEMENT_NAME);
   }

   public KeySize buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new KeySizeImpl(namespaceURI, localName, namespacePrefix);
   }
}
