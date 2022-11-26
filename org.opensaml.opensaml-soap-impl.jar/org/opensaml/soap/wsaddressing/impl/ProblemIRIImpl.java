package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.ProblemIRI;

public class ProblemIRIImpl extends AttributedURIImpl implements ProblemIRI {
   public ProblemIRIImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
