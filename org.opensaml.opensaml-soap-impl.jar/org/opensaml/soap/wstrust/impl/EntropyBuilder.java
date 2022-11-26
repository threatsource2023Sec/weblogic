package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Entropy;

public class EntropyBuilder extends AbstractWSTrustObjectBuilder {
   public Entropy buildObject() {
      return (Entropy)this.buildObject(Entropy.ELEMENT_NAME);
   }

   public Entropy buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EntropyImpl(namespaceURI, localName, namespacePrefix);
   }
}
