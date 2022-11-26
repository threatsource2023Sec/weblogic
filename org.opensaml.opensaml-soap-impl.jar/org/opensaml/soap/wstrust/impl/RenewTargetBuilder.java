package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.RenewTarget;

public class RenewTargetBuilder extends AbstractWSTrustObjectBuilder {
   public RenewTarget buildObject() {
      return (RenewTarget)this.buildObject(RenewTarget.ELEMENT_NAME);
   }

   public RenewTarget buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new RenewTargetImpl(namespaceURI, localName, namespacePrefix);
   }
}
