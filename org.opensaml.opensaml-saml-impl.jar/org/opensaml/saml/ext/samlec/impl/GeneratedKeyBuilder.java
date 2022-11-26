package org.opensaml.saml.ext.samlec.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.ext.samlec.GeneratedKey;

public class GeneratedKeyBuilder extends AbstractSAMLObjectBuilder {
   public GeneratedKey buildObject() {
      return this.buildObject("urn:ietf:params:xml:ns:samlec", "GeneratedKey", "samlec");
   }

   public GeneratedKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new GeneratedKeyImpl(namespaceURI, localName, namespacePrefix);
   }
}
