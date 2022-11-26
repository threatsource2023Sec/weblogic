package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.ComputedKeyAlgorithm;

public class ComputedKeyAlgorithmImpl extends XSURIImpl implements ComputedKeyAlgorithm {
   public ComputedKeyAlgorithmImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
