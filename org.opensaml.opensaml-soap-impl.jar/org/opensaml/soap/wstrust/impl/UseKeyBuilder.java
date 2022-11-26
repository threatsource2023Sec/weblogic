package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.UseKey;

public class UseKeyBuilder extends AbstractWSTrustObjectBuilder {
   public UseKey buildObject() {
      return (UseKey)this.buildObject(UseKey.ELEMENT_NAME);
   }

   public UseKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new UseKeyImpl(namespaceURI, localName, namespacePrefix);
   }
}
