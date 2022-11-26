package org.opensaml.soap.wstrust.impl;

import org.opensaml.soap.wstrust.Code;

public class CodeBuilder extends AbstractWSTrustObjectBuilder {
   public Code buildObject() {
      return (Code)this.buildObject(Code.ELEMENT_NAME);
   }

   public Code buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new CodeImpl(namespaceURI, localName, namespacePrefix);
   }
}
