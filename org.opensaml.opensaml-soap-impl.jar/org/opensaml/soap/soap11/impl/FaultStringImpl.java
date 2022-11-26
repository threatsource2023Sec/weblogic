package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.soap.soap11.FaultString;

public class FaultStringImpl extends XSStringImpl implements FaultString {
   protected FaultStringImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
