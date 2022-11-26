package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class PublicImpl extends CryptoBinaryImpl implements Public {
   protected PublicImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
