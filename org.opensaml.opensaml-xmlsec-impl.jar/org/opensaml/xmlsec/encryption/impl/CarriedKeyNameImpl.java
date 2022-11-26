package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.xmlsec.encryption.CarriedKeyName;

public class CarriedKeyNameImpl extends XSStringImpl implements CarriedKeyName {
   protected CarriedKeyNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
