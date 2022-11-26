package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSStringImpl;
import org.opensaml.soap.wstrust.Reason;

public class ReasonImpl extends XSStringImpl implements Reason {
   public ReasonImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
