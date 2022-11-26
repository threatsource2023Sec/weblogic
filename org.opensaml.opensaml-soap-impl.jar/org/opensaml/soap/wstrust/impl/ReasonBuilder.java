package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Reason;

public class ReasonBuilder extends AbstractWSTrustObjectBuilder {
   public Reason buildObject() {
      return (Reason)this.buildObject(Reason.ELEMENT_NAME);
   }

   public Reason buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ReasonImpl(namespaceURI, localName, namespacePrefix);
   }
}
