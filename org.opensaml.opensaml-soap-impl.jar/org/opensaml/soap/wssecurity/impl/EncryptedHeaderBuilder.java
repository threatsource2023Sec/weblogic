package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.EncryptedHeader;

public class EncryptedHeaderBuilder extends AbstractWSSecurityObjectBuilder {
   public EncryptedHeader buildObject() {
      return (EncryptedHeader)this.buildObject(EncryptedHeader.ELEMENT_NAME);
   }

   public EncryptedHeader buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncryptedHeaderImpl(namespaceURI, localName, namespacePrefix);
   }
}
