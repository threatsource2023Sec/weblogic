package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class GeneratorImpl extends CryptoBinaryImpl implements Generator {
   protected GeneratorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
