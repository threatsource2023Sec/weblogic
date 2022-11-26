package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Lifetime;

public class LifetimeBuilder extends AbstractWSTrustObjectBuilder {
   public Lifetime buildObject() {
      return (Lifetime)this.buildObject(Lifetime.ELEMENT_NAME);
   }

   public Lifetime buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new LifetimeImpl(namespaceURI, localName, namespacePrefix);
   }
}
