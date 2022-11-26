package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.AllowPostdating;

public class AllowPostdatingBuilder extends AbstractWSTrustObjectBuilder {
   public AllowPostdating buildObject() {
      return (AllowPostdating)this.buildObject(AllowPostdating.ELEMENT_NAME);
   }

   public AllowPostdating buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AllowPostdatingImpl(namespaceURI, localName, namespacePrefix);
   }
}
