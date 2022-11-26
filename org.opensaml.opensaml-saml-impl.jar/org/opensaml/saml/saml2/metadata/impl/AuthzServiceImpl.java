package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.AuthzService;

public class AuthzServiceImpl extends EndpointImpl implements AuthzService {
   protected AuthzServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
