package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.saml2.core.LogoutResponse;

public class LogoutResponseImpl extends StatusResponseTypeImpl implements LogoutResponse {
   protected LogoutResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }
}
