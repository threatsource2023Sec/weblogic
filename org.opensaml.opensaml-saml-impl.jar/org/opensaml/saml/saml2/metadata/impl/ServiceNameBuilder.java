package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ServiceName;

public class ServiceNameBuilder extends AbstractSAMLObjectBuilder {
   public ServiceName buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceName", "md");
   }

   public ServiceName buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ServiceNameImpl(namespaceURI, localName, namespacePrefix);
   }
}
