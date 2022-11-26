package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Username;

public class UsernameBuilder extends AbstractWSSecurityObjectBuilder {
   public Username buildObject() {
      return (Username)this.buildObject(Username.ELEMENT_NAME);
   }

   public Username buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new UsernameImpl(namespaceURI, localName, namespacePrefix);
   }
}
