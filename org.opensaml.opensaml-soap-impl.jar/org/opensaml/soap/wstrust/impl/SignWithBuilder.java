package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.SignWith;

public class SignWithBuilder extends AbstractWSTrustObjectBuilder {
   public SignWith buildObject() {
      return (SignWith)this.buildObject(SignWith.ELEMENT_NAME);
   }

   public SignWith buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignWithImpl(namespaceURI, localName, namespacePrefix);
   }
}
