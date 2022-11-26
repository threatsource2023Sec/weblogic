package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.saml2.metadata.SingleLogoutService;

public class SingleLogoutServiceImpl extends EndpointImpl implements SingleLogoutService {
   protected SingleLogoutServiceImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
