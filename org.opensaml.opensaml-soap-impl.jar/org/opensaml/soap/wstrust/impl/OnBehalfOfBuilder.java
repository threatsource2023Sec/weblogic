package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.OnBehalfOf;

public class OnBehalfOfBuilder extends AbstractWSTrustObjectBuilder {
   public OnBehalfOf buildObject() {
      return (OnBehalfOf)this.buildObject(OnBehalfOf.ELEMENT_NAME);
   }

   public OnBehalfOf buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new OnBehalfOfImpl(namespaceURI, localName, namespacePrefix);
   }
}
