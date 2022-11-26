package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.EncType;

public class EncTypeBuilder extends AbstractSAMLObjectBuilder {
   public EncType buildObject() {
      return this.buildObject("urn:ietf:params:xml:ns:samlec", "EncType", "samlec");
   }

   public EncType buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new EncTypeImpl(namespaceURI, localName, namespacePrefix);
   }
}
