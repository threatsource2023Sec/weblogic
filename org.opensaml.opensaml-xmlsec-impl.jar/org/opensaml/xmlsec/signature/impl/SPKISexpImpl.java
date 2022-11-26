package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSBase64BinaryImpl;
import org.opensaml.xmlsec.signature.SPKISexp;

public class SPKISexpImpl extends XSBase64BinaryImpl implements SPKISexp {
   protected SPKISexpImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
