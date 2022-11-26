package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ProblemIRI;

public class ProblemIRIBuilder extends AbstractWSAddressingObjectBuilder {
   public ProblemIRI buildObject() {
      return (ProblemIRI)this.buildObject(ProblemIRI.ELEMENT_NAME);
   }

   public ProblemIRI buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ProblemIRIImpl(namespaceURI, localName, namespacePrefix);
   }
}
