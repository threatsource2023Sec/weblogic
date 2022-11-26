package org.opensaml.saml.ext.saml1md.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.saml1md.SourceID;

public class SourceIDBuilder extends AbstractSAMLObjectBuilder {
   public SourceID buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:profiles:v1metadata", "SourceID", "saml1md");
   }

   public SourceID buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new SourceIDImpl(namespaceURI, localName, namespacePrefix);
   }
}
