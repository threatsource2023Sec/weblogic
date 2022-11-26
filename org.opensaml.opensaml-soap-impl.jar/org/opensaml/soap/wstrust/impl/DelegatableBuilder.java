package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Delegatable;

public class DelegatableBuilder extends AbstractWSTrustObjectBuilder {
   public Delegatable buildObject() {
      return (Delegatable)this.buildObject(Delegatable.ELEMENT_NAME);
   }

   public Delegatable buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DelegatableImpl(namespaceURI, localName, namespacePrefix);
   }
}
