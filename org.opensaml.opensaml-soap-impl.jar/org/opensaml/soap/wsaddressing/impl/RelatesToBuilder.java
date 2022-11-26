package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.RelatesTo;

public class RelatesToBuilder extends AbstractWSAddressingObjectBuilder {
   public RelatesTo buildObject() {
      return (RelatesTo)this.buildObject(RelatesTo.ELEMENT_NAME);
   }

   public RelatesTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RelatesToImpl(namespaceURI, localName, namespacePrefix);
   }
}
