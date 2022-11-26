package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.SecurityTokenReference;

public class SecurityTokenReferenceBuilder extends AbstractWSSecurityObjectBuilder {
   public SecurityTokenReference buildObject() {
      return (SecurityTokenReference)this.buildObject(SecurityTokenReference.ELEMENT_NAME);
   }

   public SecurityTokenReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SecurityTokenReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
