package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.schema.impl.XSIntegerImpl;
import org.opensaml.soap.wssecurity.Iteration;

public class IterationImpl extends XSIntegerImpl implements Iteration {
   public IterationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
