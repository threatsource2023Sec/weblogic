package org.opensaml.saml.ext.saml2mdreqinit.impl;

import org.opensaml.saml.ext.saml2mdreqinit.RequestInitiator;
import org.opensaml.saml.saml2.metadata.impl.EndpointImpl;

public class RequestInitiatorImpl extends EndpointImpl implements RequestInitiator {
   protected RequestInitiatorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
