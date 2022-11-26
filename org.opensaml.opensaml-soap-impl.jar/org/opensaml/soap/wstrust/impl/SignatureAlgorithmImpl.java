package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.SignatureAlgorithm;

public class SignatureAlgorithmImpl extends XSURIImpl implements SignatureAlgorithm {
   public SignatureAlgorithmImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
