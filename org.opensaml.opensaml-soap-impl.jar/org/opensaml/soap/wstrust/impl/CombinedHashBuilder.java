package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.CombinedHash;

public class CombinedHashBuilder extends AbstractWSTrustObjectBuilder {
   public CombinedHash buildObject() {
      return (CombinedHash)this.buildObject(CombinedHash.ELEMENT_NAME);
   }

   public CombinedHash buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CombinedHashImpl(namespaceURI, localName, namespacePrefix);
   }
}
