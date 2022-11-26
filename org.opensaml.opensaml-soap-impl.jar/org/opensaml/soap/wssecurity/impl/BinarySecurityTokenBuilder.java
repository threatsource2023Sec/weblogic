package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.BinarySecurityToken;

public class BinarySecurityTokenBuilder extends AbstractWSSecurityObjectBuilder {
   public BinarySecurityToken buildObject() {
      return (BinarySecurityToken)this.buildObject(BinarySecurityToken.ELEMENT_NAME);
   }

   public BinarySecurityToken buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new BinarySecurityTokenImpl(namespaceURI, localName, namespacePrefix);
   }
}
