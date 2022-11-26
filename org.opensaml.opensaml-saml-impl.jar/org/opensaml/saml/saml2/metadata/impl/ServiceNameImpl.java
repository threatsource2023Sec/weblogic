package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.ServiceName;

public class ServiceNameImpl extends LocalizedNameImpl implements ServiceName {
   protected ServiceNameImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
