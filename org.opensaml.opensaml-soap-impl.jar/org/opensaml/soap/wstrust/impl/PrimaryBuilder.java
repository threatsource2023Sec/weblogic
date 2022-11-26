package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Primary;

public class PrimaryBuilder extends AbstractWSTrustObjectBuilder {
   public Primary buildObject() {
      return (Primary)this.buildObject(Primary.ELEMENT_NAME);
   }

   public Primary buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PrimaryImpl(namespaceURI, localName, namespacePrefix);
   }
}
