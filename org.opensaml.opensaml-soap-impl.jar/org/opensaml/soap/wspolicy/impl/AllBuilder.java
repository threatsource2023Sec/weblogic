package org.opensaml.soap.wspolicy.impl;

import org.opensaml.soap.wspolicy.All;

public class AllBuilder extends AbstractWSPolicyObjectBuilder {
   public All buildObject() {
      return (All)this.buildObject(All.ELEMENT_NAME);
   }

   public All buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new AllImpl(namespaceURI, localName, namespacePrefix);
   }
}
