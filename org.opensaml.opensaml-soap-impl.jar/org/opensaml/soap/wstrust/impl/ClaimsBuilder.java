package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Claims;

public class ClaimsBuilder extends AbstractWSTrustObjectBuilder {
   public Claims buildObject() {
      return (Claims)this.buildObject(Claims.ELEMENT_NAME);
   }

   public Claims buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ClaimsImpl(namespaceURI, localName, namespacePrefix);
   }
}
