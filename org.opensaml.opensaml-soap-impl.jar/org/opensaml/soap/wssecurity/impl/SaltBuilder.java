package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Salt;

public class SaltBuilder extends AbstractWSSecurityObjectBuilder {
   public Salt buildObject() {
      return (Salt)this.buildObject(Salt.ELEMENT_NAME);
   }

   public Salt buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SaltImpl(namespaceURI, localName, namespacePrefix);
   }
}
