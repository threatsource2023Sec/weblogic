package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.signature.KeyName;

public class KeyNameImpl extends XSStringImpl implements KeyName {
   protected KeyNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
