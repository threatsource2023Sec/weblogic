package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ReferenceParameters;

public class ReferenceParametersBuilder extends AbstractWSAddressingObjectBuilder {
   public ReferenceParameters buildObject() {
      return (ReferenceParameters)this.buildObject(ReferenceParameters.ELEMENT_NAME);
   }

   public ReferenceParameters buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ReferenceParametersImpl(namespaceURI, localName, namespacePrefix);
   }
}
