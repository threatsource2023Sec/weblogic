package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.DelegateTo;

public class DelegateToBuilder extends AbstractWSTrustObjectBuilder {
   public DelegateTo buildObject() {
      return (DelegateTo)this.buildObject(DelegateTo.ELEMENT_NAME);
   }

   public DelegateTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new DelegateToImpl(namespaceURI, localName, namespacePrefix);
   }
}
