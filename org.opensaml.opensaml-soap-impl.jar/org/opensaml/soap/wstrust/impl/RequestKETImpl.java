package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.soap.wstrust.RequestKET;

public class RequestKETImpl extends AbstractWSTrustObject implements RequestKET {
   public RequestKETImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
