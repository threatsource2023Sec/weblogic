package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.To;

public class ToImpl extends AttributedURIImpl implements To {
   public ToImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
