package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.P;

public class PImpl extends CryptoBinaryImpl implements P {
   protected PImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
