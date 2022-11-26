package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.PgenCounter;

public class PgenCounterImpl extends CryptoBinaryImpl implements PgenCounter {
   protected PgenCounterImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
