package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.SignatureConfirmation;

public class SignatureConfirmationBuilder extends AbstractWSSecurityObjectBuilder {
   public SignatureConfirmation buildObject() {
      return (SignatureConfirmation)this.buildObject(SignatureConfirmation.ELEMENT_NAME);
   }

   public SignatureConfirmation buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SignatureConfirmationImpl(namespaceURI, localName, namespacePrefix);
   }
}
