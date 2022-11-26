package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Forwardable;

public class ForwardableBuilder extends AbstractWSTrustObjectBuilder {
   public Forwardable buildObject() {
      return (Forwardable)this.buildObject(Forwardable.ELEMENT_NAME);
   }

   public Forwardable buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ForwardableImpl(namespaceURI, localName, namespacePrefix);
   }
}
