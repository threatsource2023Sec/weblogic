package org.opensaml.xmlsec.signature.impl;

import org.opensaml.xmlsec.signature.ECPointType;

public class ECPointTypeImpl extends CryptoBinaryImpl implements ECPointType {
   protected ECPointTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
