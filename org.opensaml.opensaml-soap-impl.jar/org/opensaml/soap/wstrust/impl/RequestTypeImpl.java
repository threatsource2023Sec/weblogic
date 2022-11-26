package org.opensaml.soap.wstrust.impl;

import org.opensaml.core.xml.schema.impl.XSURIImpl;
import org.opensaml.soap.wstrust.RequestType;

public class RequestTypeImpl extends XSURIImpl implements RequestType {
   public RequestTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
