package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.soap.wstrust.RequestedTokenCancelled;

public class RequestedTokenCancelledImpl extends AbstractWSTrustObject implements RequestedTokenCancelled {
   public RequestedTokenCancelledImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
