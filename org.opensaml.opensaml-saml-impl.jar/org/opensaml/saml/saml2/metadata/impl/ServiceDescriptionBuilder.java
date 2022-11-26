package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ServiceDescription;

public class ServiceDescriptionBuilder extends AbstractSAMLObjectBuilder {
   public ServiceDescription buildObject() {
      return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ServiceDescription", "md");
   }

   public ServiceDescription buildObject(String namespaceURI, String localName, String namespacePrefix) {
      return new ServiceDescriptionImpl(namespaceURI, localName, namespacePrefix);
   }
}
