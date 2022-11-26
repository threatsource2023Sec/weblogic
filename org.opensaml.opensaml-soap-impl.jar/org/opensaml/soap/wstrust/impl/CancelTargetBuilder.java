package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.CancelTarget;

public class CancelTargetBuilder extends AbstractWSTrustObjectBuilder {
   public CancelTarget buildObject() {
      return (CancelTarget)this.buildObject(CancelTarget.ELEMENT_NAME);
   }

   public CancelTarget buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CancelTargetImpl(namespaceURI, localName, namespacePrefix);
   }
}
