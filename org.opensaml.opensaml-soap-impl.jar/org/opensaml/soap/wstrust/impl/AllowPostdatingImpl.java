package org.opensaml.soap.wstrust.impl;

import java.util.List;
import org.opensaml.soap.wstrust.AllowPostdating;

public class AllowPostdatingImpl extends AbstractWSTrustObject implements AllowPostdating {
   public AllowPostdatingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public List getOrderedChildren() {
      return null;
   }
}
