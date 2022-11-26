package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.EncryptWith;

public class EncryptWithBuilder extends AbstractWSTrustObjectBuilder {
   public EncryptWith buildObject() {
      return (EncryptWith)this.buildObject(EncryptWith.ELEMENT_NAME);
   }

   public EncryptWith buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptWithImpl(namespaceURI, localName, namespacePrefix);
   }
}
