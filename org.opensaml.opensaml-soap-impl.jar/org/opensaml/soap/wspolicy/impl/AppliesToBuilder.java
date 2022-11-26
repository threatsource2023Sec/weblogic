package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.AppliesTo;

public class AppliesToBuilder extends AbstractWSPolicyObjectBuilder {
   public AppliesTo buildObject() {
      return (AppliesTo)this.buildObject(AppliesTo.ELEMENT_NAME);
   }

   public AppliesTo buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AppliesToImpl(namespaceURI, localName, namespacePrefix);
   }
}
