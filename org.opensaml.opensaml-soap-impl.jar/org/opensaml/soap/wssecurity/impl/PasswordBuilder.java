package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.Password;

public class PasswordBuilder extends AbstractWSSecurityObjectBuilder {
   public Password buildObject() {
      return (Password)this.buildObject(Password.ELEMENT_NAME);
   }

   public Password buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new PasswordImpl(namespaceURI, localName, namespacePrefix);
   }
}
