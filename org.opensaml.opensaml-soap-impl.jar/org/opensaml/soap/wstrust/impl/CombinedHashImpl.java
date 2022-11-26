package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.soap.wstrust.CombinedHash;

public class CombinedHashImpl extends XSBase64BinaryImpl implements CombinedHash {
   public CombinedHashImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
