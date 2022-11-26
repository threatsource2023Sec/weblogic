package org.opensaml.soap.wssecurity.impl;

import org.opensaml.soap.wssecurity.TransformationParameters;

public class TransformationParametersBuilder extends AbstractWSSecurityObjectBuilder {
   public TransformationParameters buildObject() {
      return (TransformationParameters)this.buildObject(TransformationParameters.ELEMENT_NAME);
   }

   public TransformationParameters buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new TransformationParametersImpl(namespaceURI, localName, namespacePrefix);
   }
}
