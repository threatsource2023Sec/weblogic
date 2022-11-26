package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Status;

public class StatusBuilder extends AbstractWSTrustObjectBuilder {
   public Status buildObject() {
      return (Status)this.buildObject(Status.ELEMENT_NAME);
   }

   public Status buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new StatusImpl(namespaceURI, localName, namespacePrefix);
   }
}
