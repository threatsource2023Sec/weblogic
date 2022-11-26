package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.AuthenticationType;

public class AuthenticationTypeBuilder extends AbstractWSTrustObjectBuilder {
   public AuthenticationType buildObject() {
      return (AuthenticationType)this.buildObject(AuthenticationType.ELEMENT_NAME);
   }

   public AuthenticationType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthenticationTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
