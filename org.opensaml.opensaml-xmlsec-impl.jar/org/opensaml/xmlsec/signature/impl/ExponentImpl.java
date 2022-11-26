package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.Exponent;

public class ExponentImpl extends CryptoBinaryImpl implements Exponent {
   protected ExponentImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
