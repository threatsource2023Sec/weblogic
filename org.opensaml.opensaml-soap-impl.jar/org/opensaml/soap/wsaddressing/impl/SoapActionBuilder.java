package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.SoapAction;

public class SoapActionBuilder extends AbstractWSAddressingObjectBuilder {
   public SoapAction buildObject() {
      return (SoapAction)this.buildObject(SoapAction.ELEMENT_NAME);
   }

   public SoapAction buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SoapActionImpl(namespaceURI, localName, namespacePrefix);
   }
}
