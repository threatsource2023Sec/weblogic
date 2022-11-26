package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.CanonicalizationAlgorithm;

public class CanonicalizationAlgorithmImpl extends XSURIImpl implements CanonicalizationAlgorithm {
   public CanonicalizationAlgorithmImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
