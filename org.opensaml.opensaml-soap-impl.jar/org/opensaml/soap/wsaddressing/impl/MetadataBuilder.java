package org.opensaml.soap.wsaddressing.impl;

import org.opensaml.soap.wsaddressing.Metadata;

public class MetadataBuilder extends AbstractWSAddressingObjectBuilder {
   public Metadata buildObject() {
      return (Metadata)this.buildObject(Metadata.ELEMENT_NAME);
   }

   public Metadata buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new MetadataImpl(namespaceURI, localName, namespacePrefix);
   }
}
