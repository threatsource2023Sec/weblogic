package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Security;

public class SecurityBuilder extends AbstractWSSecurityObjectBuilder {
   public Security buildObject() {
      return (Security)this.buildObject(Security.ELEMENT_NAME);
   }

   public Security buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SecurityImpl(namespaceURI, localName, namespacePrefix);
   }
}
