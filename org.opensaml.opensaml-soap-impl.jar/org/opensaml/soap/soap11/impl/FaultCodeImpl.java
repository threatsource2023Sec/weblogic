package org.opensaml.soap.soap11.impl;

import org.opensaml.core.xml.schema.impl.XSQNameImpl;
import org.opensaml.soap.soap11.FaultCode;

public class FaultCodeImpl extends XSQNameImpl implements FaultCode {
   protected FaultCodeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
