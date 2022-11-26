package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.ServiceDescription;

public class ServiceDescriptionImpl extends LocalizedNameImpl implements ServiceDescription {
   protected ServiceDescriptionImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
