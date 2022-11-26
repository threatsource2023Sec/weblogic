package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.To;

public class ToBuilder extends AbstractWSAddressingObjectBuilder {
   public To buildObject() {
      return (To)this.buildObject(To.ELEMENT_NAME);
   }

   public To buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ToImpl(namespaceURI, localName, namespacePrefix);
   }
}
