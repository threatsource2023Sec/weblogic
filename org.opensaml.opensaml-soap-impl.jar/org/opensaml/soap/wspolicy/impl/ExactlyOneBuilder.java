package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.ExactlyOne;

public class ExactlyOneBuilder extends AbstractWSPolicyObjectBuilder {
   public ExactlyOne buildObject() {
      return (ExactlyOne)this.buildObject(ExactlyOne.ELEMENT_NAME);
   }

   public ExactlyOne buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ExactlyOneImpl(namespaceURI, localName, namespacePrefix);
   }
}
