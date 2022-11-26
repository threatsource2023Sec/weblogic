package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ProblemAction;

public class ProblemActionBuilder extends AbstractWSAddressingObjectBuilder {
   public ProblemAction buildObject() {
      return (ProblemAction)this.buildObject(ProblemAction.ELEMENT_NAME);
   }

   public ProblemAction buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ProblemActionImpl(namespaceURI, localName, namespacePrefix);
   }
}
