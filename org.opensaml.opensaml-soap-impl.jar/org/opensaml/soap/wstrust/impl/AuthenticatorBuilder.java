package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Authenticator;

public class AuthenticatorBuilder extends AbstractWSTrustObjectBuilder {
   public Authenticator buildObject() {
      return (Authenticator)this.buildObject(Authenticator.ELEMENT_NAME);
   }

   public Authenticator buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AuthenticatorImpl(namespaceURI, localName, namespacePrefix);
   }
}
