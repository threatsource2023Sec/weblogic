package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.UsernameToken;

public class UsernameTokenBuilder extends AbstractWSSecurityObjectBuilder {
   public UsernameToken buildObject() {
      return (UsernameToken)this.buildObject(UsernameToken.ELEMENT_NAME);
   }

   public UsernameToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new UsernameTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
