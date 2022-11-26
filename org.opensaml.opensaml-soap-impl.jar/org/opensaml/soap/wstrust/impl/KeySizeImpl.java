package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSIntegerImpl;
import org.opensaml.soap.wstrust.KeySize;

public class KeySizeImpl extends XSIntegerImpl implements KeySize {
   public KeySizeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
