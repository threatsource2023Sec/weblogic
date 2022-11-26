package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.schema.impl.XSIntegerImpl;
import org.opensaml.xmlsec.encryption.KeySize;

public class KeySizeImpl extends XSIntegerImpl implements KeySize {
   protected KeySizeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
