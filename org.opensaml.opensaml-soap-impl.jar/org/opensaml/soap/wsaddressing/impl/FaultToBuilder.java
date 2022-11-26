package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.FaultTo;

public class FaultToBuilder extends AbstractWSAddressingObjectBuilder {
   public FaultTo buildObject() {
      return (FaultTo)this.buildObject(FaultTo.ELEMENT_NAME);
   }

   public FaultTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new FaultToImpl(namespaceURI, localName, namespacePrefix);
   }
}
