package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.EncryptionAlgorithm;

public class EncryptionAlgorithmImpl extends XSURIImpl implements EncryptionAlgorithm {
   public EncryptionAlgorithmImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
