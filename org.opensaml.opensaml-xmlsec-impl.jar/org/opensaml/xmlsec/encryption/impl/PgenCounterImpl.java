package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class PgenCounterImpl extends CryptoBinaryImpl implements PgenCounter {
   protected PgenCounterImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
