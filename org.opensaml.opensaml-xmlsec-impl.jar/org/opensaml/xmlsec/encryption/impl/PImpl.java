package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class PImpl extends CryptoBinaryImpl implements P {
   protected PImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
