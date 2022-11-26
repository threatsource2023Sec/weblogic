package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Expires;

public class ExpiresBuilder extends AbstractWSSecurityObjectBuilder {
   public Expires buildObject() {
      return (Expires)this.buildObject(Expires.ELEMENT_NAME);
   }

   public Expires buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ExpiresImpl(namespaceURI, localName, namespacePrefix);
   }
}
