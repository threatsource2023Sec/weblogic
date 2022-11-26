package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.ValidateTarget;

public class ValidateTargetBuilder extends AbstractWSTrustObjectBuilder {
   public ValidateTarget buildObject() {
      return (ValidateTarget)this.buildObject(ValidateTarget.ELEMENT_NAME);
   }

   public ValidateTarget buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ValidateTargetImpl(namespaceURI, localName, namespacePrefix);
   }
}
