package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ProblemHeaderQName;

public class ProblemHeaderQNameBuilder extends AbstractWSAddressingObjectBuilder {
   public ProblemHeaderQName buildObject() {
      return (ProblemHeaderQName)this.buildObject(ProblemHeaderQName.ELEMENT_NAME);
   }

   public ProblemHeaderQName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ProblemHeaderQNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
