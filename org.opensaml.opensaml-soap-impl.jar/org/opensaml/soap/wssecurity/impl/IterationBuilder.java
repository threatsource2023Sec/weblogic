package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Iteration;

public class IterationBuilder extends AbstractWSSecurityObjectBuilder {
   public Iteration buildObject() {
      return (Iteration)this.buildObject(Iteration.ELEMENT_NAME);
   }

   public Iteration buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new IterationImpl(namespaceURI, localName, namespacePrefix);
   }
}
