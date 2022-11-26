package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.Modulus;

public class ModulusImpl extends CryptoBinaryImpl implements Modulus {
   protected ModulusImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
