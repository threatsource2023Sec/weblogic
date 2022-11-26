package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.Q;

public class QImpl extends CryptoBinaryImpl implements Q {
   protected QImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
