package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.ExactlyOne;

public class ExactlyOneImpl extends OperatorContentTypeImpl implements ExactlyOne {
   protected ExactlyOneImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
