package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.soap.wstrust.KeyWrapAlgorithm;

public class KeyWrapAlgorithmImpl extends XSStringImpl implements KeyWrapAlgorithm {
   public KeyWrapAlgorithmImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
