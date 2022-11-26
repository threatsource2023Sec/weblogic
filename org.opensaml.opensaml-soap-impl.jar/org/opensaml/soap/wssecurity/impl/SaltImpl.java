package org.opensaml.soap.wssecurity.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.soap.wssecurity.Salt;

public class SaltImpl extends XSBase64BinaryImpl implements Salt {
   protected SaltImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
