package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class QImpl extends CryptoBinaryImpl implements Q {
   protected QImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
