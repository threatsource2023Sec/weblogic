package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Reference;

public class ReferenceBuilder extends AbstractWSSecurityObjectBuilder {
   public Reference buildObject() {
      return (Reference)this.buildObject(Reference.ELEMENT_NAME);
   }

   public Reference buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ReferenceImpl(namespaceURI, localName, namespacePrefix);
   }
}
