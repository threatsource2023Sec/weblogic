package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.ComputedKey;

public class ComputedKeyImpl extends XSURIImpl implements ComputedKey {
   public ComputedKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
