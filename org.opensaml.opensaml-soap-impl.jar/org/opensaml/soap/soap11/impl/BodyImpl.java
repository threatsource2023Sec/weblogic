package org.opensaml.soap.soap11.impl;

import org.opensaml.soap.common.AbstractExtensibleSOAPObject;
import org.opensaml.soap.soap11.Body;

public class BodyImpl extends AbstractExtensibleSOAPObject implements Body {
   protected BodyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
