package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.signature.XPath;

public class XPathImpl extends XSStringImpl implements XPath {
   protected XPathImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
